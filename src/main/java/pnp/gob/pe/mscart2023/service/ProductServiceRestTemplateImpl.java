package pnp.gob.pe.mscart2023.service;

import feign.FeignException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import pnp.gob.pe.mscart2023.model.dto.ProductResponseDto;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Slf4j
@RequiredArgsConstructor
//@Primary
@Service
public class ProductServiceRestTemplateImpl implements ProductService{

    private final RestTemplate restTemplate;

    private String urlBase = "http://ms-productos-2023:8001/v1.0";
    //private String urlBase = "http://localhost:8001/v1.0";
    private String urlId = urlBase + "/{id}";

    @Override
    public List<ProductResponseDto> findAll() {
        log.info("findAll");
        ResponseEntity<ProductResponseDto[]> response = restTemplate.getForEntity(urlBase, ProductResponseDto[].class);
        return Arrays.asList(response.getBody());
    }

    @Override
    public ProductResponseDto findById(Long id) {
        log.info("findById");
        Map<String, String> pathVariables = new HashMap<>();
        pathVariables.put("id", id.toString());
        try {
            ResponseEntity<ProductResponseDto> response = restTemplate.getForEntity(urlId, ProductResponseDto.class, pathVariables);
            return response.getBody();
        }catch (HttpClientErrorException e){
            if (e.getStatusCode() == HttpStatus.NOT_FOUND){
                return null;
            }else{
                throw e;
            }
        }
    }

    @CircuitBreaker(name="product", fallbackMethod = "findByIdResilienceFallBackMethod")
    @TimeLimiter(name="product")
    @Override
    public CompletableFuture<ProductResponseDto> findByIdResilience(Long id) {
        log.info("findByIdResilience");
        Map<String, String> pathVariables = new HashMap<>();
        pathVariables.put("id", id.toString());
        return CompletableFuture.supplyAsync(()-> {
                    try {
                        ResponseEntity<ProductResponseDto> response = restTemplate.getForEntity(urlId, ProductResponseDto.class, pathVariables);
                        return response.getBody();
                    }catch (HttpClientErrorException e) {
                        if(e.getStatusCode() == HttpStatus.NOT_FOUND) {
                            return null;
                        }else{
                            throw e;
                        }
                    }
                }
                /*
                //issue: https://docs.spring.io/spring-cloud-sleuth/docs/current-SNAPSHOT/reference/html/integrations.html#sleuth-async-executor-service-integration
                , new TraceableExecutorService(beanFactory, Executors.newFixedThreadPool(2), "findProductByIdResilience")
                */
                );
    }

    @Override
    public CompletableFuture<ProductResponseDto> findByIdResilienceFallBackMethod(Long id, Throwable t) {
        log.info("findByIdResilienceFallBackMethod: " + t.getMessage());
        try {
            ProductResponseDto product = ProductResponseDto.builder()
                    .id(id)
                    .name("TEMPORAL")
                    .stock(0D)
                    .price(BigDecimal.ZERO)
                    .build();
            return CompletableFuture.supplyAsync(()-> product
                    /*,
                    //issue: https://docs.spring.io/spring-cloud-sleuth/docs/current-SNAPSHOT/reference/html/integrations.html#sleuth-async-executor-service-integration
                    new TraceableExecutorService(beanFactory, Executors.newFixedThreadPool(2), "findProductByIdResilienceFallBackMethod")
                    */
            );
        }catch (FeignException e) {
            if(e.status() == HttpStatus.NOT_FOUND.value()) {
                return null;
            }else{
                throw e;
            }
        }
    }
}
