package com.github.framework.evo.sys.dao;

import com.github.framework.evo.base.dao.BaseXmlDao;
import com.github.framework.evo.sys.entity.DataDict;
import org.apache.ibatis.annotations.Mapper;

/**
 * User: Kyll
 * Date: 2018-04-13 09:51
 */
@Mapper
public interface DataDictDao extends BaseXmlDao<DataDict, Long> {
}
