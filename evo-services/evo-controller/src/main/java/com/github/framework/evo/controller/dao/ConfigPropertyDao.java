package com.github.framework.evo.controller.dao;

import com.github.framework.evo.controller.entity.ConfigProperty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * User: Kyll
 * Date: 2019-05-24 13:53
 */
public interface ConfigPropertyDao extends JpaRepository<ConfigProperty, Long>, JpaSpecificationExecutor<ConfigProperty> {
	@Query(nativeQuery = true, value = "select t.profile from config_property t group by t.profile order by t.profile asc")
	List<String> findProfiles();

	ConfigProperty findByApplicationAndProfileAndLabelAndKey(String application, String profile, String label, String key);
}
