package pnp.gob.pe.mscart2023.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import pnp.gob.pe.mscart2023.model.dto.ProductResponseDto;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
}
