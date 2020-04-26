package com.yc;

import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.AutoConfigurationImportSelector;
import org.springframework.boot.autoconfigure.AutoConfigurationPackage;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.core.type.AnnotationMetadata;

//springboot程序入口
//@Configuration 
//@EnableAutoConfiguration  -> 
// @AutoConfigurationPackage   -> HelloApplication所在包要扫描.


@SpringBootApplication  // @Configuration      读取xml文件 .    ->  META-INF/spring.factories
public class HelloApplication {

	public static void main(String[] args) {
		SpringApplication.run(  HelloApplication.class   , args);
		//   -> AutoConfigurationImportSelector类 *****selectImports(AnnotationMetadata annotationMetadata)  -> META-INF/spring.factories
		
		// String[] importClassNames = selector.selectImports(currentSourceClass.getMetadata());
		
		//ApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfig2.class);
		
		
	}

}

/*
 先分析  SpringBoot注解部分: 
 1. @EnableAutoConfiguration里通过@Import导入了AutoConfigurationImportSelector
2. AutoConfigurationImportSelector类中有一个   ******    selectImports(AnnotationMetadata annotationMetadata)方法,    ******
     这个方法调用了  :  getAutoConfigurationEntry(autoConfigurationMetadata, annotationMetadata);   
      在这个方法中调用了:  List<String> configurations = getCandidateConfigurations(annotationMetadata, attributes);  获取所有的配置项
那么它是怎么获取的呢?
3. 再观察:  getCandidateConfigurations(annotationMetadata, attributes);
     其中有 List<String> configurations = SpringFactoriesLoader.loadFactoryNames(getSpringFactoriesLoaderFactoryClass(),getBeanClassLoader());
4. 再进入SpringFactoriesLoader.loadFactoryNames(getSpringFactoriesLoaderFactoryClass(),getBeanClassLoader());
     可以看到它取出工厂类型名后，调用了  loadSpringFactories(classLoader)
    它的返回值是一个Map,  在这个方法中有: 
 Enumeration<URL> urls = (classLoader != null ?classLoader.getResources(FACTORIES_RESOURCE_LOCATION) :
       			ClassLoader.getSystemResources(FACTORIES_RESOURCE_LOCATION));
   请观察:    classLoader.getResources(FACTORIES_RESOURCE_LOCATION) :
        从这里可以看到 类加载器在加载  路径:   FACTORIES_RESOURCE_LOCATION="META-INF/spring.factories"
就这里了，  请到  "META-INF/spring.factories"  查看，在这个文件中，可以看到一系列Spring Boot自动配置的列表

 */


/*
 1. SpringApplication.run()  ->  SpringApplication类的   
      316行:   refreshContext(context);   ->     
      397行:  refresh(context);
      775行:  ((AbstractApplicationContext) applicationContext).refresh();  
                因为这里是一个web项目，所以使用的ApplicationContext为  boot.web.servlet.context.ServletWebServerApplicationContext
 2. 所以下一步查看  boot.web.servlet.context.ServletWebServerApplicationContext类. 
      142行     super.refresh()方法  -> 
 3. super指调用 ServletWebServerApplicationContext类的父类AbstractApplicationContext的   refresh()方法 
     所以查看  AbstractApplicationContext类的源码: 
         528行:     invokeBeanFactoryPostProcessors(beanFactory);   这个方法启动工厂处理器以注册bean到容器
         691行:    在invokeBeanFactoryPostProcessors()方法中
             调用 PostProcessorRegistrationDelegate.invokeBeanFactoryPostProcessors(beanFactory, getBeanFactoryPostProcessors());
             在这个方法的 130行  invokeBeanFactoryPostProcessors(registryProcessors, beanFactory);
             
             286行:    
                   for (BeanFactoryPostProcessor postProcessor : postProcessors) {
			           postProcessor.postProcessBeanFactory(beanFactory);
		       }
		       注意:  这里的 BeanFactoryPostProcessor是一个接口，它下面的 org.springframework.context.annotation.ConfigurationClassPostProcessor 类才是我们要看的.
 4. ConfigurationClassPostProcessor类的  postProcessBeanFactory(beanFactory); 方法中的 
      250行: 	processConfigBeanDefinitions((BeanDefinitionRegistry) beanFactory);  处理有注解的Bean的定义
      从processConfigBeanDefinitions()方法中的  307行开始解析  @Configuration注解了, 注意:
          315行  parser.parse(candidates);  
                这里的parser是一个 ConfigurationClassParser 即对加了 @Configuration的类的解析器. 
                这里的  candidates是  274行读取的这些被 @Configuration注解的类. 
                
                
 5.   parser是一个  org.springframework.context.annotation.ConfigurationClassParser类的对象
      上面  parser.parse(candidates) 中 调用context.annotation.ConfigurationClassParser.doProcessConfigurationClass()方法处理ConfigurationClass:
           从  167行  ->  199行  ->  242行  ->  sourceClass = doProcessConfigurationClass(configClass, sourceClass);  按类层次结构追踪  处理ConfigurationClass，
                请具体分析以下这个源码： doProcessConfigurationClass(configClass, sourceClass)，在它里面，它处理了： 
                    1.  @PropertySource
                    2. @ComponentScan
                    3. @Import        注意这个， 与我们讲的  springboot中的  @Import(AutoConfigurationImportSelector.class) 相关
                    4.  @ImportResource
                    5. @Bean
   
          
      6. 接上面的  3   对@Import的处理
         302行  processImports(configClass, sourceClass, getImports(sourceClass), true);
         
      到 544行， 遍历每个@Import标签，生成被注入的ImportSelector子类的实例
             注意从  560行到  566行: 
    |               -> 对于普通ImportSelector，调用其selectImport()方法，筛掉exclude的，再嵌套processImports()，对需要被@Import的类的@Import注解进行处理
    |               -> 对于DefferedImportSelector，只加入deferredImportSelectors列表中
    
         特别是   569行   String[] importClassNames = selector.selectImports(currentSourceClass.getMetadata());   这里调用的就是 springboot中提到的注解了
        
                
      
 
 
 */
