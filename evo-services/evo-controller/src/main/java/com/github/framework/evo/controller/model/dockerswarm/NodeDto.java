package com.github.framework.evo.controller.model.dockerswarm;

import lombok.Data;

/**
 * User: Kyll
 * Date: 2019-06-14 08:34
 */
@Data
public class NodeDto {
	private String id;
	private Integer versionIndex;
	private String createdAt;
	private String updatedAt;
	private String role;
	private String availability;
	private String hostname;
	private String architecture;
	private String os;
	private Long nanoCPUs;
	private Long memoryBytes;
	private String engineVersion;
	private String state;
	private String addr;
	private ManagerStatusDto managerStatusDto;
}
