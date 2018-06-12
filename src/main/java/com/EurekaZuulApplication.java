package com;

import java.io.File;

import javax.servlet.MultipartConfigElement;

import org.apache.catalina.connector.Connector;
import org.apache.coyote.http11.Http11NioProtocol;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;



@EnableEurekaServer
@EnableZuulProxy
@EnableEurekaClient
@EnableFeignClients
@EnableHystrix
@EnableHystrixDashboard
@SpringBootApplication
public class EurekaZuulApplication {
	@Autowired
	private Environment env;
	public static void main(String[] args) {
			SpringApplication.run(EurekaZuulApplication.class, args);
	
	}
	 @Bean
	    public EmbeddedServletContainerFactory servletContainer() {
	        TomcatEmbeddedServletContainerFactory tomcat = new TomcatEmbeddedServletContainerFactory();
	        tomcat.addAdditionalTomcatConnectors(createSslConnector()); // 添加http
	        return tomcat;
	    }

	 private Connector createSslConnector() {
	        Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
	        Http11NioProtocol protocol = (Http11NioProtocol) connector.getProtocolHandler();
	        String x = env.getProperty("https.file");
	        String y = env.getProperty("https.password");
	        String p = env.getProperty("https.port");
	            File keystore = new File(x);
	            /*File truststore = new ClassPathResource("sample.jks").getFile();*/
	            connector.setScheme("https");
	            connector.setSecure(true);
	            connector.setPort(Integer.parseInt(p));
	            protocol.setSSLEnabled(true);
	            protocol.setKeystoreFile(keystore.getAbsolutePath());
	            protocol.setKeystorePass(y);
	           // protocol.setKeyPass(key_password);
	            return connector;
	      
	     
	    }
	 
	
}
