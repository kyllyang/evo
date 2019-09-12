package com.github.framework.evo.controller.model.dockerswarm;

import lombok.Data;

/**
 * User: Kyll
 * Date: 2019-09-12 16:45
 */
@Data
public class TaskDto {
	private String id;
	private Integer versionIndex;
	private String createdAt;
	private String updatedAt;
	private String image;
	private String serviceId;
	private String nodeId;
	private String state;
	private String message;
	private String containerId;
	private Integer pid;
	private String desiredState;
}
