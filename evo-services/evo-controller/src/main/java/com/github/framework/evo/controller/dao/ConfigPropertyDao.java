package com.github.framework.evo.controller.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.framework.evo.controller.entity.ConfigProperty;
import com.github.framework.evo.controller.model.ConfigItemCondition;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * User: Kyll
 * Date: 2019-05-24 13:53
 */
public interface ConfigPropertyDao extends BaseMapper<ConfigProperty> {
	List<String> selectProfiles();

	List<ConfigProperty> selectAll(@Param("c") ConfigItemCondition condition);

	List<ConfigProperty> selectByCheck(@Param("c") ConfigItemCondition condition);
}
