package com.github.framework.evo.controller.bizz;

import com.github.framework.evo.base.assist.BaseHelper;
import com.github.framework.evo.base.bizz.BasePlusBizz;
import com.github.framework.evo.common.model.PageList;
import com.github.framework.evo.controller.api.ConfigApi;
import com.github.framework.evo.controller.dao.ConfigPropertyDao;
import com.github.framework.evo.controller.entity.ConfigProperty;
import com.github.framework.evo.controller.model.ConfigInfoDto;
import com.github.framework.evo.controller.model.ConfigItemDto;
import com.github.framework.evo.controller.model.ConfigItemCondition;
import com.github.framework.evo.controller.model.ConfigPropertyDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

	public ConfigInfoDto findPage(ConfigItemCondition condition) {
		List<ConfigProperty> configPropertyList = dao.selectAll(condition);

		Set<String> profileSet = new LinkedHashSet<>();// 设置环境列 default, 环境1, 环境2...
		String[] profiles = condition.getProfiles();
		if (profiles == null || profiles.length == 0) {
			profileSet.add("default");
		} else {
			profileSet.addAll(Arrays.asList(profiles));
		}

		Map<String, ConfigItemDto> configItemDtoMap = new TreeMap<>();// 设置配置属性关联的属性值
		for (ConfigProperty configProperty : configPropertyList) {
			if (profiles == null || profiles.length == 0) {
				profileSet.add(configProperty.getProfile());
			}

			String key = configProperty.getKey();
			String profile = configProperty.getProfile();

			configItemDtoMap.computeIfAbsent(key, s -> {
				ConfigItemDto itemDto = new ConfigItemDto();
				itemDto.setLabel(configProperty.getLabel());
				itemDto.setApplication(configProperty.getApplication());
				itemDto.setKey(key);
				itemDto.setValueMap(new HashMap<>());
				itemDto.setComment(configProperty.getComment());
				return itemDto;
			}).getValueMap().put(profile, configProperty.getValue());
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

	public void updateConfigProperty(String application, String profile, String label, String key, ConfigPropertyDto configPropertyDto) {
		ConfigProperty configProperty = dao.findByApplicationAndProfileAndLabelAndKey(application, profile, label, key);
		configProperty.setValue(configPropertyDto.getValue());
		configProperty.setComment(configPropertyDto.getComment());
		this.update(toDto(configProperty));
	}

	public void refreshConfigProperty(String destination) {
		configApi.busRefresh(destination);
	}
}
