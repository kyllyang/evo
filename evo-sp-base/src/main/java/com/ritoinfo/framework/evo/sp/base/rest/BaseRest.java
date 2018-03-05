package com.ritoinfo.framework.evo.sp.base.rest;

import com.ritoinfo.framework.evo.sp.base.bizz.BaseBizz;
import com.ritoinfo.framework.evo.sp.base.condition.BaseCondition;
import com.ritoinfo.framework.evo.sp.base.dao.BaseDao;
import com.ritoinfo.framework.evo.sp.base.entity.BaseEntity;
import com.ritoinfo.framework.evo.sp.base.model.ServiceResponse;
import com.ritoinfo.framework.evo.sp.base.validate.group.ListGroup;
import com.ritoinfo.framework.evo.sp.base.validate.group.PageGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import java.io.Serializable;

/**
 * User: Kyll
 * Date: 2018-02-09 15:58
 */
public abstract class BaseRest<B extends BaseBizz<D, E, PK, C>, D extends BaseDao<E, PK, C>, E extends BaseEntity<PK>, PK extends Serializable, C extends BaseCondition<PK>> {
	@Autowired
	private B bizz;

	@GetMapping("/{id}")
	public ServiceResponse get(@PathVariable PK id) {
		return ServiceResponse.ok(bizz.get(id));
	}

	@GetMapping("/all")
	public ServiceResponse find() {
		return ServiceResponse.ok(bizz.find());
	}

	@GetMapping
	public ServiceResponse find(@Validated(ListGroup.class)C condition) {
		return ServiceResponse.ok(bizz.find(condition));
	}

	@GetMapping("/page")
	public ServiceResponse findPage(@Validated(PageGroup.class) C condition) {
		return ServiceResponse.ok(bizz.findPage(condition));
	}

	@PostMapping
	public ServiceResponse create(E entity) {
		bizz.create(entity);
		return ServiceResponse.ok();
	}

	@PutMapping
	public ServiceResponse update(E entity) {
		bizz.update(entity);
		return ServiceResponse.ok();
	}

	@DeleteMapping("/{id}")
	public ServiceResponse delete(@PathVariable PK id) {
		bizz.delete(id);
		return ServiceResponse.ok();
	}
}
