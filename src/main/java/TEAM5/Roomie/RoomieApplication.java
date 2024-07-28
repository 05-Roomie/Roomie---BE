package TEAM5.Roomie;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@SpringBootApplication
@PropertySources({@PropertySource("classpath:properties/env.properties")})
public class RoomieApplication {

	public static void main(String[] args) {

		SpringApplication.run(RoomieApplication.class, args);
	}

}
