package com.github.framework.evo.controller.dao;

import com.github.framework.evo.controller.entity.ConfigProperty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * User: Kyll
 * Date: 2019-05-24 13:53
 */
public interface ConfigPropertyDao extends JpaRepository<ConfigProperty, Long>, JpaSpecificationExecutor<ConfigProperty> {
	ConfigProperty findByApplicationAndProfileAndLabelAndKey(String application, String profile, String label, String key);
}
