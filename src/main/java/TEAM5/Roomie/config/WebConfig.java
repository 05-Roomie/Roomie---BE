package TEAM5.Roomie.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Value("${client.url}")
    private String frontendUrl;

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                System.out.println("CORS 설정을 위한 CLIENT_URL: " + frontendUrl); // 로그 출력
                registry.addMapping("/**")
                        .allowCredentials(true)
                        .allowedOrigins(frontendUrl)
                        .allowedMethods("*");
            }
        };
    }
}
