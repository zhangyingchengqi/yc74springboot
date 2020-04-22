package com.yc;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@ConditionalOnClass(IYc.class)
@EnableConfigurationProperties(YcProperties.class)
public class YcAppConfig {

	@Bean
	@ConditionalOnMissingBean
	public IYc yc() {
		return new YcImpl();
	}
}
