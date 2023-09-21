package pnp.gob.pe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class MsCart2024Application {

	public static void main(String[] args) {
		SpringApplication.run(MsCart2024Application.class, args);
	}

}
