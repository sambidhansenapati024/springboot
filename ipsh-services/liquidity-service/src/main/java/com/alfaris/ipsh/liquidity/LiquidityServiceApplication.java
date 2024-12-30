package com.alfaris.ipsh.liquidity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.client.RestTemplate;

import com.alfaris.ipsh.liquidity.entity.PshLqmPrmPK;

@SpringBootApplication
@EnableDiscoveryClient

@EnableFeignClients
@EnableMethodSecurity
public class LiquidityServiceApplication extends SpringBootServletInitializer {
	
	@Value("${productType}")
	String productType;
	
	@Value("${ccyCode}")
	String ccyCode;
	
	@Value("${bankId}")
	String bankId;

	public static void main(String[] args) {
		SpringApplication.run(LiquidityServiceApplication.class, args);
	}
	
	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.setConnectTimeout(Duration.ofMillis(10000)).setReadTimeout(Duration.ofMillis(10000)).build();
	}
	
	@Bean()
	public DateFormat dateFormatWithSpace() {
		return  new SimpleDateFormat("yyyy-MM-dd' 'HH:mm:ss.SSS");
	}
	@Bean()
	public DateFormat dateFormatReverse() {
		return   new SimpleDateFormat("yyyy-MM-dd");
	}
	
	@Bean()
	public PshLqmPrmPK pshLqmPrmPk() {
		PshLqmPrmPK pk = new PshLqmPrmPK();
		pk.setBankId(bankId);
		pk.setCurrencyCode(ccyCode);
		pk.setProductType(productType);
		return pk;
	}

}
