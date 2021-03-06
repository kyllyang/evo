package com.github.framework.evo.base.rest;

import com.github.framework.evo.base.bizz.BaseBizz;
import com.github.framework.evo.common.model.PageDto;
import com.github.framework.evo.common.model.PageList;
import com.github.framework.evo.common.validate.group.CreateGroup;
import com.github.framework.evo.common.validate.group.PageGroup;
import com.github.framework.evo.common.validate.group.UpdateGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.Serializable;

/**
 * User: Kyll
 * Date: 2018-02-09 15:58
 */
public abstract class BaseRest<B extends BaseBizz, PK extends Serializable, D, PD extends PageDto> {
	@Autowired
	protected B bizz;

	@GetMapping("/{id}")
	@SuppressWarnings("unchecked")
	public D get(@PathVariable PK id) {
		return (D) bizz.get(id);
	}

	@PostMapping("/page")
	@SuppressWarnings("unchecked")
	public PageList<D> findPage(@Validated(PageGroup.class) @RequestBody PD pageDto) {
		return bizz.findPage(pageDto);
	}

	@PostMapping
	@SuppressWarnings("unchecked")
	public PK create(@Validated(CreateGroup.class) @RequestBody D dto) {
		return (PK) bizz.create(dto);
	}

	@PutMapping
	@SuppressWarnings("unchecked")
	public void update(@Validated(UpdateGroup.class) @RequestBody D dto) {
		bizz.update(dto);
	}

	@DeleteMapping("/{id}")
	@SuppressWarnings("unchecked")
	public void delete(@PathVariable PK id) {
		bizz.delete(id);
	}
}
