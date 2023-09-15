package pnp.gob.pe;

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
import pnp.gob.pe.mscart2023.model.dto.CartResponseDto;
import pnp.gob.pe.mscart2023.model.entity.CartEntity;
import pnp.gob.pe.mscart2023.model.entity.CartItemEntity;
import pnp.gob.pe.mscart2023.repository.CartRepository;
import pnp.gob.pe.mscart2023.service.CartService;

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
	


}
