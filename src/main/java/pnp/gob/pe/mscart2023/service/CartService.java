package pnp.gob.pe.mscart2023.service;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pnp.gob.pe.mscart2023.model.mapper.CartMapper;
import pnp.gob.pe.mscart2023.repository.CartRepository;

@Slf4j
@RequiredArgsConstructor
@Service
public class CartService {

	private final CartRepository cartRepository;
	
	private final CartMapper cartMapper;
}
