package pnp.gob.pe.mscart2023.service;

import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import pnp.gob.pe.mscart2023.client.ProductClientRest;
import pnp.gob.pe.mscart2023.model.dto.ProductResponseDto;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Primary
@Service
public class ProductServiceFeignImpl implements ProductService{

    private final ProductClientRest productClientRest;

    @Override
    public List<ProductResponseDto> findAll() {
        log.info("findAll");
        return productClientRest.findAll();
    }

    @Override
    public ProductResponseDto findById(Long id) {
        log.info("findById");
        try {
            return productClientRest.findById(id);
        }catch (FeignException e){
            if (e.status() == HttpStatus.NOT_FOUND.value()) {
                return null;
            }else {
                throw e;
            }
        }
    }
}
