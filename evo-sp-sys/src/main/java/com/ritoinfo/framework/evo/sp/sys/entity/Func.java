package com.ritoinfo.framework.evo.sp.sys.entity;

import com.ritoinfo.framework.evo.sp.base.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * User: Kyll
 * Date: 2018-02-28 15:27
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Func extends BaseEntity<Long> {
	private String name;// 功能名称
	private String code;// 功能编码
	private Long parentId;// 上级功能ID
	private String url;// 请求地址
	private Integer sort;// 排序号
}