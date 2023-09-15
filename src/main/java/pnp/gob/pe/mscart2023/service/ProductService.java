package pnp.gob.pe.mscart2023.service;

import pnp.gob.pe.mscart2023.model.dto.ProductResponseDto;

import java.util.List;

public interface ProductService {

    public List<ProductResponseDto> findAll();
    public ProductResponseDto findById(Long id);
}
