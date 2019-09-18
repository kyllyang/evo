package com.github.framework.evo.controller.model.eureka;

import lombok.Data;

/**
 * User: Kyll
 * Date: 2019-09-18 14:42
 */
@Data
public class InstanceDto {
	private String instanceId;
	private String hostName;
	private String app;
	private String ipAddr;
	private String status;
	private String overriddenStatus;
	private String port;
	private String securePort;
	private String countryId;
	private String dataCenterInfo;
}
