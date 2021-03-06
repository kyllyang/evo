package com.github.framework.evo.sys.dao;

import com.github.framework.evo.base.dao.BaseXmlDao;
import com.github.framework.evo.sys.condition.MenuCondition;
import com.github.framework.evo.sys.entity.Menu;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * User: Kyll
 * Date: 2018-04-23 21:12
 */
@Mapper
public interface MenuDao extends BaseXmlDao<Menu, Long> {
	Map<String, Object> getWithParent(Long id);

	List<Map<String, Object>> findByIds(Long... ids);

	List<Map<String, Object>> findByUsername(String username);

	int countWithParent(MenuCondition condition);

	List<Map<String, Object>> findPageWithParent(MenuCondition condition);
}
