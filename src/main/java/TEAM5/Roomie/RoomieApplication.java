package TEAM5.Roomie;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
@PropertySources({@PropertySource("classpath:properties/env.properties")})
public class RoomieApplication {

	public static void main(String[] args) {

		SpringApplication.run(RoomieApplication.class, args);
	}

}
