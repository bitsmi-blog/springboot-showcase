package com.bitsmi.springbootshowcase.web.config;

import org.apache.catalina.connector.Connector;
import org.springframework.boot.autoconfigure.web.servlet.TomcatServletWebServerFactoryCustomizer;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.AsyncSupportConfigurer;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.mvc.WebContentInterceptor;

@Configuration
public class WebConfig implements WebMvcConfigurer
{
	/**
     * Set async events timeout (180 seg.)
     */
    @Override
    public void configureAsyncSupport(AsyncSupportConfigurer configurer) 
    {
        configurer.setDefaultTimeout(180000);
    }
    
    /**
     * Enable parameter pass through request body in POST, PUT and DELETE requests. By default Tomcat only allows in POST 
     * @return {@link TomcatServletWebServerFactoryCustomizer} - 
     */
    @Bean
    public TomcatServletWebServerFactory containerFactory() 
    {
        return new TomcatServletWebServerFactory() 
        {
            protected void customizeConnector(Connector connector) 
            {
                super.customizeConnector(connector);
                connector.setParseBodyMethods("POST,PUT,DELETE");
            }
        };
    }

	@Override
	public void addInterceptors(InterceptorRegistry registry) 
	{
		registry.addInterceptor(webContentInterceptor());
	}
	
	public WebContentInterceptor webContentInterceptor() 
	{
        WebContentInterceptor interceptor = new WebContentInterceptor();
        // Cache-Control: no-store
        interceptor.setCacheSeconds(0);
        
        return interceptor;
    }
}
