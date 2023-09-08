package pnp.gob.pe;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import pnp.gob.pe.mscart2023.model.entity.CartEntity;
import pnp.gob.pe.mscart2023.model.entity.CartItemEntity;
import pnp.gob.pe.mscart2023.repository.CartRepository;

@DataJpaTest
public class CartRepositoryTest {

	@Autowired
	private CartRepository cartRepository;
	
	@Test
	void whenValidGetByCustomerid_thenReturnCart() {
		Optional<CartEntity> cartOptional = cartRepository.findByCustomerId(333L);
		assertTrue(cartOptional.isPresent());
		assertEquals(2, cartOptional.get().getItems().size());
	}
	
	@Test
	void whenValidSave_ThenReturnCart() {
		
		CartItemEntity cartItemEntity1 = CartItemEntity.builder()
				.productId(3L)
				.name("Teclado")
				.price(BigDecimal.valueOf(300))
				.quantity(3)
				//.cart(cartEntity)
				.build();
		//cartRepository.save(cartEntity);
		//cartItemRepository.save(cartItemEntity1);
		
		CartItemEntity cartItemEntity2 = CartItemEntity.builder()
				.productId(2L)
				.name("Monitor")
				.price(BigDecimal.valueOf(200))
				.quantity(2)
				//.cart(cartEntity)
				.build();
		//cartItemRepository.save(cartItemEntity2);
		
		CartEntity cartEntity = CartEntity.builder()
				.customerId(1L)
				.items(List.of(cartItemEntity1, cartItemEntity2))
				.build();
		cartRepository.save(cartEntity);
		
		Optional<CartEntity> cartEntityFound = cartRepository.findByCustomerId(1L);
		assertTrue(cartEntityFound.isPresent());
		assertEquals(2, cartEntityFound.get().getItems().size());
		assertEquals(new BigDecimal(900), cartEntityFound.get().getItems().get(0).getSubTotal());
	}
	
	@Test
	void whenValidDeleteCartItemByCustomerId_ThenDeleted() {
		List<Long> productIdList = List.of(1L, 2L);
		Optional<CartEntity> cartEntity = cartRepository.findByCustomerId(333L);
		productIdList.forEach(p -> {
			cartEntity.get().getItems().removeIf(i -> i.getProductId() == p);
		});
		Optional<CartEntity>  cartEntity2 = cartRepository.findByCustomerId(333L);
		assertEquals(0, cartEntity2.get().getItems().size());
	}
}
