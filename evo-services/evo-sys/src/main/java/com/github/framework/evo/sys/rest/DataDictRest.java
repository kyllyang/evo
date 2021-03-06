package com.github.framework.evo.sys.rest;

import com.github.framework.evo.base.rest.BaseRest;
import com.github.framework.evo.sys.bizz.DataDictBizz;
import com.github.framework.evo.sys.condition.DataDictCondition;
import com.github.framework.evo.sys.dto.DataDictDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * User: Kyll
 * Date: 2018-04-13 09:57
 */
@Slf4j
@RestController
public class DataDictRest extends BaseRest<DataDictBizz, Long, DataDictDto, DataDictCondition> {
	@GetMapping("/code/{code}")
	public List<DataDictDto> findByCode(@PathVariable String code) {
		return bizz.findByCode(code);
	}

	@GetMapping("/code/{code}/children")
	public List<DataDictDto> findByCodeForChildren(@PathVariable String code) {
		return bizz.findByCodeForChildren(code);
	}

	@GetMapping("/code/{code}/key/{key}")
	public DataDictDto findByCodeAndKey(@PathVariable String code, @PathVariable String key) {
		return bizz.getByCodeAndKey(DataDictCondition.builder().code(code).key(key).build());
	}

	@GetMapping("/code/{code}/key/{key}/children")
	public DataDictDto findByCodeAndKeyForChildren(@PathVariable String code, @PathVariable String key) {
		return bizz.findByCodeAndKeyForChildren(DataDictCondition.builder().code(code).key(key).build());
	}

	@DeleteMapping("/id/{id}/code/{code}")
	public void delete(@PathVariable Long id, @PathVariable String code) {
		bizz.delete(id, code);
	}
}
