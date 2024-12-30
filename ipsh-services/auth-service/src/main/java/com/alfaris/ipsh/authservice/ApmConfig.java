package com.alfaris.ipsh.authservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import co.elastic.apm.attach.ElasticApmAttacher;

@Configuration
public class ApmConfig {
	 private static final Logger logger = LoggerFactory.getLogger(ApmConfig.class);
	
    private final Environment environment;

    public ApmConfig(Environment environment) {
        this.environment = environment;
    }
    
    @Bean
    public Void configureElasticApm() {
        String serverUrl = environment.getProperty("elastic.apm.server_url");
        String secretToken = environment.getProperty("elastic.apm.api_key");

        if (serverUrl != null && secretToken != null) { 
        	
            System.setProperty("elastic.apm.server_url", serverUrl);
            System.setProperty("elastic.apm.api_key", secretToken);
            ElasticApmAttacher.attach();
        } else {
        	logger.info("----->ERROR =========================>APM url / tocken missing");
            logger.error("Elastic APM configuration properties are missing");
        }
        return null;
    }

}
