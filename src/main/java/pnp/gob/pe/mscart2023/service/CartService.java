package pnp.gob.pe.mscart2023.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import pnp.gob.pe.mscart2023.configuration.error.ResourceNotFoundException;
import pnp.gob.pe.mscart2023.model.dto.CartRequestDeleteDto;
import pnp.gob.pe.mscart2023.model.dto.CartRequestDto;
import pnp.gob.pe.mscart2023.model.dto.CartResponseDto;
import pnp.gob.pe.mscart2023.model.dto.ProductResponseDto;
import pnp.gob.pe.mscart2023.model.entity.CartEntity;
import pnp.gob.pe.mscart2023.model.mapper.CartMapper;
import pnp.gob.pe.mscart2023.repository.CartRepository;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class CartService {

	private final CartRepository cartRepository;
	
	private final CartMapper mapper;

	private final ProductService productService;

	@Transactional(readOnly = true)
	public CartResponseDto findByCustomerId(Long customerId){
		log.info("findByCustomerId");
		return cartRepository.findByCustomerId(customerId)
				.map(mapper::entityToResponse).orElseThrow(() -> new ResourceNotFoundException("Resource not found", HttpStatus.NOT_FOUND));
	}

	@Transactional
	public CartResponseDto addItem(Long customerId, CartRequestDto cartRequestDto){
		log.info("addItem");
		Optional<CartEntity> cartOptional = cartRepository.findByCustomerId(customerId);
		if (cartOptional.isEmpty()){
			throw new ResourceNotFoundException("Resource not found", HttpStatus.NOT_FOUND);
		}

		CartEntity cartEntity = cartOptional.get();

		cartRequestDto.getItems().forEach(p -> {
			ProductResponseDto product = productService.findById(p.getProductId());
			if (product == null){
				throw new ResourceNotFoundException("Product not found with id " + p.getProductId(), HttpStatus.NOT_FOUND);
			}else{
				cartEntity.getItems().add(mapper.responseToEntity(product, p.getQuantity()));
			}
		});

		cartRepository.save(cartEntity);

		return mapper.entityToResponse(cartEntity);
	}

	@Transactional
	public CartResponseDto removeItem(Long customerId, CartRequestDeleteDto cartRequestDeleteDto){
		log.info("removeItem");
		Optional<CartEntity> cartOptional = cartRepository.findByCustomerId(customerId);
		if (cartOptional.isEmpty()){
			throw new ResourceNotFoundException("Resource not found", HttpStatus.NOT_FOUND);
		}

		CartEntity cartEntity = cartOptional.get();

		cartRequestDeleteDto.getItems().forEach(p -> {
			cartEntity.getItems().removeIf(pt -> pt.getProductId() == p.getProductId());
		});

		cartRepository.save(cartEntity);

		return mapper.entityToResponse(cartEntity);
	}
}
