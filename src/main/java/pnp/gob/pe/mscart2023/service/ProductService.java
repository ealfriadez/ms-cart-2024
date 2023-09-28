package pnp.gob.pe.mscart2023.service;

import pnp.gob.pe.mscart2023.model.dto.ProductResponseDto;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface ProductService {

    public List<ProductResponseDto> findAll();
    public ProductResponseDto findById(Long id);
    public CompletableFuture<ProductResponseDto> findByIdResilience(Long id);
    public CompletableFuture<ProductResponseDto> findByIdResilienceFallBackMethod(Long id, Throwable t);
}
