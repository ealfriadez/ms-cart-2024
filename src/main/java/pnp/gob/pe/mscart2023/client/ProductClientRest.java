package pnp.gob.pe.mscart2023.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import pnp.gob.pe.mscart2023.model.dto.ProductResponseDto;

@FeignClient(name = "ms-productos-2023", url = "http://localhost:8001", path = "/v1.0")
public interface ProductClientRest {
	
	@GetMapping
	public List<ProductResponseDto> findAll();
	
	@GetMapping("/{id}")
	public ProductResponseDto findById(@PathVariable Long id);

}
