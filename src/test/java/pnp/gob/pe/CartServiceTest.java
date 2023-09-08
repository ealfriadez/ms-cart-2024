package com.codearti;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
public class CartServiceTest {
	
	@MockBean
	private CartRepository productRepository;
	
	@Autowired
	private CartService cartService;
	
	@BeforeEach
	public void setup() {
		
		CartItemEntity cartItemEntity1 = CartItemEntity.builder()
				.productId(3L)
				.name("Teclado")
				.price(BigDecimal.valueOf(300))
				.quantity(3)
				//.cart(cartEntity)
				.build();
		
		CartItemEntity cartItemEntity2 = CartItemEntity.builder()
				.productId(2L)
				.name("Monitor")
				.price(BigDecimal.valueOf(200))
				.quantity(2)
				//.cart(cartEntity)
				.build();
		
		CartEntity cartEntity = CartEntity.builder()
				.customerId(1L)
				.items(List.of(cartItemEntity1, cartItemEntity2))
				.build();
		
		Mockito.when(productRepository.findByCustomerId(1L)).thenReturn(Optional.of(cartEntity));
	}
	
	@Test
	public void whenValidGetId_ThenReturnCart() {
		CartResponseDto cartResponseDto = cartService.findByCustomerId(1L);
		assertEquals(1L, cartResponseDto.getCustomerId());
		assertEquals(2, cartResponseDto.getItems().size());
		assertEquals(3L, cartResponseDto.getItems().get(0).getProductId());
		assertEquals("Teclado", cartResponseDto.getItems().get(0).getName());
		assertEquals(BigDecimal.valueOf(300), cartResponseDto.getItems().get(0).getPrice());
		assertEquals(3, cartResponseDto.getItems().get(0).getQuantity());
	}

}
