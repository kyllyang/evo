package com.ritoinfo.framework.evo.sp.datadict.dao;

import com.ritoinfo.framework.evo.sp.base.dao.MyBatisDao;
import com.ritoinfo.framework.evo.sp.datadict.condition.DataDictCondition;
import com.ritoinfo.framework.evo.sp.datadict.entity.DataDict;
import org.apache.ibatis.annotations.Mapper;

/**
 * User: Kyll
 * Date: 2018-04-13 09:51
 */
@Mapper
public interface DataDictDao extends MyBatisDao<DataDict, Long, DataDictCondition> {
}
