package com.github.framework.evo.controller.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.github.framework.evo.base.entity.BasePlusEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * User: Kyll
 * Date: 2019-05-24 13:45
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("config_property")
public class ConfigProperty extends BasePlusEntity<Long> {
	@TableId(type = IdType.AUTO)
	private Long id;
	private String label;
	private String application;
	private String profile;
	@TableField("key_")
	private String key;
	private String value;
	private String comment;
}
