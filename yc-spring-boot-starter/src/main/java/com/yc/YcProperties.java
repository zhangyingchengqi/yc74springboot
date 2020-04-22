package com.yc;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix="spring.yc")
public class YcProperties {
	private String name = "yc";

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
