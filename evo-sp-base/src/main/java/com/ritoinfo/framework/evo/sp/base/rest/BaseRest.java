package com.ritoinfo.framework.evo.sp.base.rest;

import com.ritoinfo.framework.evo.sp.base.bizz.BaseBizz;
import com.ritoinfo.framework.evo.sp.base.condition.BaseCondition;
import com.ritoinfo.framework.evo.sp.base.entity.BaseEntity;
import com.ritoinfo.framework.evo.sp.base.model.ServiceResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.Serializable;

/**
 * User: Kyll
 * Date: 2018-02-09 15:58
 */
public abstract class BaseRest<B extends BaseBizz, E extends BaseEntity, PK extends Serializable, C extends BaseCondition> {
	@Autowired
	private B bizz;

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ServiceResponse get(@PathVariable PK id) {
		bizz.get(id);
		return null;
	}

	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public ServiceResponse find() {
		bizz.find();
		return null;
	}

	@RequestMapping(method = RequestMethod.GET)
	public ServiceResponse find(C condition) {
		bizz.find(condition);
		return null;
	}

	@RequestMapping(value = "/page", method = RequestMethod.GET)
	public ServiceResponse findPage(C condition) {
		bizz.findPage(condition);
		return null;
	}

	@RequestMapping(method = RequestMethod.POST)
	public ServiceResponse create(E entity) {
		bizz.create(entity);
		return null;
	}

	@RequestMapping(method = RequestMethod.PUT)
	public ServiceResponse update(E entity) {
		bizz.update(entity);
		return null;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ServiceResponse delete(@PathVariable PK id) {
		bizz.delete(id);
		return null;
	}
}
