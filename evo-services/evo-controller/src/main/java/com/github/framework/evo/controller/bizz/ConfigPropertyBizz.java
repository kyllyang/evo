package com.github.framework.evo.controller.bizz;

import com.github.framework.evo.base.assist.BaseHelper;
import com.github.framework.evo.base.bizz.BasePlusBizz;
import com.github.framework.evo.common.SR;
import com.github.framework.evo.common.exception.BusinessException;
import com.github.framework.evo.common.model.PageList;
import com.github.framework.evo.common.uitl.ArrayUtil;
import com.github.framework.evo.common.uitl.BeanUtil;
import com.github.framework.evo.common.uitl.StringUtil;
import com.github.framework.evo.controller.api.ConfigApi;
import com.github.framework.evo.controller.dao.ConfigPropertyDao;
import com.github.framework.evo.controller.entity.ConfigProperty;
import com.github.framework.evo.controller.model.configproperty.ConfigInfoDto;
import com.github.framework.evo.controller.model.configproperty.ConfigItemCondition;
import com.github.framework.evo.controller.model.configproperty.ConfigItemDto;
import com.github.framework.evo.controller.model.configproperty.ConfigPropertyDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * User: Kyll
 * Date: 2019-05-24 13:09
 */
@Slf4j
@Service
public class ConfigPropertyBizz extends BasePlusBizz<ConfigPropertyDao, ConfigProperty, Long, ConfigPropertyDto> {
	@Autowired
	private ConfigApi configApi;

	public String[] getProfiles() {
		return getProfiles(getProfileSet());
	}

	public ConfigItemDto getByIds(Long[] ids) {
		List<ConfigProperty> configPropertyList = dao.selectBatchIds(Arrays.asList(ids));

		Set<String> keySet = new HashSet<>();
		for (ConfigProperty configProperty : configPropertyList) {
			keySet.add(configProperty.getKey());
		}

		String key = keySet.iterator().next();
		if (keySet.size() > 1) {
			throw new BusinessException(SR.RC.CONTROLLER_SPRING_CLOUD_CONFIG_PROPERTY_DUPLICATE, key);
		}

		return convertToConfigItemDtoMap(configPropertyList, getProfileSet()).get(key);
	}

	public ConfigInfoDto findPage(ConfigItemCondition condition) {
		Set<String> profileSet = ArrayUtil.isEmpty(condition.getProfiles()) ? getProfileSet() : getProfileSet(condition.getProfiles());
		Map<String, ConfigItemDto> configItemDtoMap = convertToConfigItemDtoMap(dao.selectAll(condition), profileSet);

		int total = configItemDtoMap.size();
		int pageSize = condition.getPageSize();
		int start = (condition.getPageNo() - 1) * pageSize;
		int end = start + pageSize;
		if (end > total) {
			end = total;
		}
		if (start > end) {
			start = end;
		}

		PageList<ConfigItemDto> pageList = new PageList<>();
		BaseHelper.copyPage(pageList, total, condition, new ArrayList<>(configItemDtoMap.values()).subList(start, end));

		ConfigInfoDto configInfoDto = new ConfigInfoDto();
		configInfoDto.setProfiles(getProfiles(profileSet));
		configInfoDto.setConfigItemList(pageList);
		return configInfoDto;
	}

	public boolean check(ConfigItemCondition condition) {
		List<ConfigProperty> list = dao.selectByCheck(condition);
		if (!list.isEmpty() && !list.get(0).getId().equals(condition.getId())) {
			throw new BusinessException(SR.RC.CONTROLLER_SPRING_CLOUD_CONFIG_PROPERTY_EXIST, condition.getProfile(), condition.getKey(), condition.getValue());
		}
		return true;
	}

	@Override
	public Long create(ConfigPropertyDto dto) {
		dto.setLabel("master");
		dto.setApplication("evo");
		return super.create(dto);
	}

	@Transactional
	@Override
	public void update(ConfigPropertyDto dto) {
		dto.setLabel("master");
		dto.setApplication("evo");
		super.update(dto);

		updateDefaultComment(dto);
	}

	public void refreshConfigProperty(String destination) {
		configApi.busRefresh(destination);
	}

	private void updateDefaultComment(ConfigPropertyDto dto) {
		Map<String, Object> conditionMap = new HashMap<>();
		conditionMap.put("label", "master");
		conditionMap.put("application", "evo");
		conditionMap.put("profile", "default");
		conditionMap.put("key_", dto.getKey());
		List<ConfigProperty> list = dao.selectByMap(conditionMap);

		int size = list.size();
		if (size == 1) {
			ConfigProperty cp = list.get(0);
			cp.setComment(dto.getComment());

			BeanUtil.copy(dto, cp);
			super.update(dto);
		} else if (list.size() > 1) {
			throw new BusinessException(SR.RC.CONTROLLER_SPRING_CLOUD_CONFIG_PROPERTY_DUPLICATE, "default", dto.getKey());
		}
	}

	private String[] getProfiles(Set<String> profileSet) {
		return profileSet.toArray(new String[0]);
	}

	private Set<String> getProfileSet() {
		Set<String> profileSet = new LinkedHashSet<>();
		profileSet.add("default");
		profileSet.addAll(dao.selectProfiles());
		return profileSet;
	}

	private Set<String> getProfileSet(String[] profiles) {
		Set<String> profileSet = new LinkedHashSet<>();

		List<String> profileList = Arrays.asList(profiles);
		if (profileList.contains("default")) {// 调整顺序
			profileSet.add("default");
		}
		profileSet.addAll(profileList);

		return profileSet;
	}

	private Map<String, String> createProfileMap(Set<String> profileSet) {
		Map<String, String> map = new LinkedHashMap<>();
		for (String profile : profileSet) {
			map.put(profile, null);
		}
		return map;
	}

	private Map<String, ConfigItemDto> convertToConfigItemDtoMap(List<ConfigProperty> configPropertyList, Set<String> profileSet) {
		Map<String, ConfigItemDto> configItemDtoMap = new TreeMap<>();// 设置配置属性关联的属性值

		for (ConfigProperty configProperty : configPropertyList) {
			String key = configProperty.getKey();

			ConfigItemDto configItemDto = configItemDtoMap.computeIfAbsent(key, s -> {
				ConfigItemDto itemDto = new ConfigItemDto();
				itemDto.setLabel(configProperty.getLabel());
				itemDto.setApplication(configProperty.getApplication());
				itemDto.setKey(key);
				itemDto.setValueMap(createProfileMap(profileSet));
				itemDto.setIdMap(createProfileMap(profileSet));
				itemDto.setComment(configProperty.getComment());
				return itemDto;
			});

			String profile = configProperty.getProfile();
			configItemDto.getValueMap().put(profile, configProperty.getValue());
			configItemDto.getIdMap().put(profile, String.valueOf(configProperty.getId()));

			if (StringUtil.isBlank(configItemDto.getComment())) {
				configItemDto.setComment(configProperty.getComment());
			}
		}

		return configItemDtoMap;
	}
}
