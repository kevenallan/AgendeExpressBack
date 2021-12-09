package br.edu.ifpb.agendeexpress.AgendeExpress.Configuration;

import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

public class CorsConfig implements WebMvcConfigurer{

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")
							.allowedMethods("PUT","POST","GET","PATCH","DELETE");
	}
}
