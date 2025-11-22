package org.example.kiosk.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

  @Override
  public void addCorsMappings(CorsRegistry registry) {
    registry.addMapping("/**")               // 모든 경로 허용
            .allowedOrigins("*")            // 모든 오리진 허용
            .allowedMethods("*")            // GET, POST, PUT, DELETE 등 모든 메서드 허용
            .allowedHeaders("*")            // 모든 헤더 허용
            .allowCredentials(false);       // 쿠키/세션 허용 X ( "*"과 함께는 true 사용 불가 )
  }
}