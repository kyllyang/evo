package com.github.framework.evo.sys.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * User: Kyll
 * Date: 2018-04-13 09:53
 */
@Data
public class DataDictDto implements Serializable {
	private Long id;
	private String parentKey;
	private String name;
	private String code;
	private String key;
	private String value;
	private Integer sort;

	private List<DataDictDto> children;
}
