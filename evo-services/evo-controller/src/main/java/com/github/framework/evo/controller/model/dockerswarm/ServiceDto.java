package com.github.framework.evo.controller.model.dockerswarm;

import lombok.Data;

import java.util.List;

/**
 * User: Kyll
 * Date: 2019-06-14 10:50
 */
@Data
public class ServiceDto {
	private String id;
	private Integer versionIndex;
	private String createdAt;
	private String updatedAt;
	private String name;
	private String image;
	private List<String> envDtoList;
	private List<MountDto> mountDtoList;
	private String mode;
	private Integer replicas;
	private List<PortDto> portList;
	private List<VirtualIPDto> virtualIPList;
}
