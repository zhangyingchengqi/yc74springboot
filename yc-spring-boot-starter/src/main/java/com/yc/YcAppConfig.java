package com.yc;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@ConditionalOnClass(IYc.class)    //条件装配:表示在classpath类路径中有IYc 这个接口的class文件才会 装配 YcAppConfig 对象
@EnableConfigurationProperties(YcProperties.class)
public class YcAppConfig {

	@Bean
	@ConditionalOnMissingBean  //表示spring容器中没有托管  IYc接口下的子类的对象.
	public IYc yc() {
		return new YcImpl();
	}
	
	
}
