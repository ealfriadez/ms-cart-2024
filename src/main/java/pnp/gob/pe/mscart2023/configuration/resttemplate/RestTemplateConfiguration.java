package pnp.gob.pe.mscart2023.configuration.resttemplate;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfiguration {

    @Bean
    //@LoadBala
    public RestTemplate registrarRestTemplate(){
        return new RestTemplate();
    }
}