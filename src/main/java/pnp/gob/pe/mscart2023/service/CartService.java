package pnp.gob.pe.mscart2023.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import pnp.gob.pe.mscart2023.configuration.error.ResourceNotFoundException;
import pnp.gob.pe.mscart2023.model.dto.CartResponseDto;
import pnp.gob.pe.mscart2023.model.mapper.CartMapper;
import pnp.gob.pe.mscart2023.repository.CartRepository;

@Slf4j
@RequiredArgsConstructor
@Service
public class CartService {

	private final CartRepository cartRepository;
	
	private final CartMapper mapper;

	@Transactional(readOnly = true)
	public CartResponseDto findByCustomerId(Long customerId){
		log.info("findByCustomerId");
		return cartRepository.findByCustomerId(customerId)
				.map(mapper::entityToResponse).orElseThrow(() -> new ResourceNotFoundException("Resource not found", HttpStatus.NOT_FOUND));
	}
}
