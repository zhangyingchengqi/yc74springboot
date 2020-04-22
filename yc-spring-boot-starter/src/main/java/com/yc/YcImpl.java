package com.yc;

import org.springframework.beans.factory.annotation.Autowired;

public class YcImpl implements IYc {
	
	@Autowired
	private YcProperties ycProperties;

	@Override
	public void sayHello() {
		String name=ycProperties.getName();
		System.out.println("say hello:"+name);
	}

}
