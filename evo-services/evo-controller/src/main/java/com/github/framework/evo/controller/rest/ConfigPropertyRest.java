package com.github.framework.evo.controller.rest;

import com.github.framework.evo.common.validate.group.CheckGroup;
import com.github.framework.evo.common.validate.group.CreateGroup;
import com.github.framework.evo.common.validate.group.PageGroup;
import com.github.framework.evo.common.validate.group.UpdateGroup;
import com.github.framework.evo.controller.bizz.ConfigPropertyBizz;
import com.github.framework.evo.controller.model.configproperty.ConfigInfoDto;
import com.github.framework.evo.controller.model.configproperty.ConfigItemCondition;
import com.github.framework.evo.controller.model.configproperty.ConfigPropertyDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

/**
 * User: Kyll
 * Date: 2019-05-24 13:07
 */
@Slf4j
@RequestMapping("/config-property")
@RestController
public class ConfigPropertyRest {
	@Autowired
	private ConfigPropertyBizz configPropertyBizz;

	@GetMapping("/profiles")
	public String[] getProfiles() {
		return configPropertyBizz.getProfiles();
	}

	@PostMapping("/item/page")
	public ConfigInfoDto findPage(@Validated(PageGroup.class) @RequestBody ConfigItemCondition condition) {
		return configPropertyBizz.findPage(condition);
	}

	@GetMapping("/{id}")
	public ConfigPropertyDto get(@PathVariable("id") Long id) {
		return configPropertyBizz.get(id);
	}

	@PostMapping("/check")
	public boolean check(@Validated(CheckGroup.class) @RequestBody ConfigItemCondition condition) {
		return configPropertyBizz.check(condition);
	}

	@PostMapping
	public Long create(@Validated({CreateGroup.class}) @RequestBody ConfigPropertyDto dto) {
		return configPropertyBizz.create(dto);
	}

	@PutMapping
	public void update(@Validated({UpdateGroup.class}) @RequestBody ConfigPropertyDto dto) {
		configPropertyBizz.update(dto);
	}

	@DeleteMapping("/{id}")
	public void delete(@PathVariable("id") Long id) {
		configPropertyBizz.delete(id);
	}

	@PostMapping("/item/refresh/{destination}")
	public void refreshConfigProperty(@PathVariable("destination") String destination) {
		configPropertyBizz.refreshConfigProperty(destination);
	}
}
