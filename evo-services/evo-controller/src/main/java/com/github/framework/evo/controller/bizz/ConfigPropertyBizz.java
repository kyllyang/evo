package com.github.framework.evo.controller.bizz;

import com.github.framework.evo.base.assist.BaseHelper;
import com.github.framework.evo.base.bizz.BasePlusBizz;
import com.github.framework.evo.common.SR;
import com.github.framework.evo.common.exception.BusinessException;
import com.github.framework.evo.common.model.PageList;
import com.github.framework.evo.common.uitl.BeanUtil;
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
		Set<String> profileSet = new LinkedHashSet<>();
		profileSet.add("default");
		profileSet.addAll(dao.selectProfiles());

		return profileSet.toArray(new String[0]);
	}

	@Transactional
	public ConfigInfoDto findPage(ConfigItemCondition condition) {
		List<ConfigProperty> configPropertyList = dao.selectAll(condition);
		update(toDto(configPropertyList.get(0)));

		Set<String> profileSet = new LinkedHashSet<>();// 设置环境列 default, 环境1, 环境2...
		String[] profiles = condition.getProfiles();
		if (profiles == null || profiles.length == 0) {
			profileSet.add("default");
		} else {
			List<String> profileList = Arrays.asList(profiles);
			if (profileList.contains("default")) {// 调整顺序
				profileSet.add("default");
			}
			profileSet.addAll(profileList);
		}

		Map<String, ConfigItemDto> configItemDtoMap = new TreeMap<>();// 设置配置属性关联的属性值
		for (ConfigProperty configProperty : configPropertyList) {
			if (profiles == null || profiles.length == 0) {
				profileSet.add(configProperty.getProfile());
			}

			String key = configProperty.getKey();
			ConfigItemDto configItemDto = configItemDtoMap.computeIfAbsent(key, s -> {
				ConfigItemDto itemDto = new ConfigItemDto();
				itemDto.setLabel(configProperty.getLabel());
				itemDto.setApplication(configProperty.getApplication());
				itemDto.setKey(key);
				itemDto.setValueMap(new HashMap<>());
				itemDto.setIdMap(new HashMap<>());
				itemDto.setComment(configProperty.getComment());
				return itemDto;
			});

			String profile = configProperty.getProfile();
			configItemDto.getValueMap().put(profile, configProperty.getValue());
			configItemDto.getIdMap().put(profile, String.valueOf(configProperty.getId()));

			if ("default".equals(profile)) {
				configItemDto.setComment(configProperty.getComment());
			}
		}

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
		configInfoDto.setProfiles(profileSet.toArray(new String[0]));
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
}
