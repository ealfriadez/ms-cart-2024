package pnp.gob.pe;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.Optional;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import pnp.gob.pe.mscart2023.model.entity.CartItemEntity;
import pnp.gob.pe.mscart2023.repository.CartItemRepository;

@DataJpaTest
public class CartItemRepositoryTest {
	
	@Autowired
	private CartItemRepository cartItemRepository;
	
	@Test
	void whenValidDeleteCartItemByCustomerIdAndProductId_ThenDeleted() {
		List<Long> productIdList = List.of(1L, 2L);
		productIdList.forEach(p -> {
			Optional<CartItemEntity> cartItemEntity = cartItemRepository.findByCustomerIdAndProductId(333L, p);
			cartItemRepository.delete(cartItemEntity.get());
		});
		assertEquals(0, cartItemRepository.count());
	}
	
	@Test
	void whenValidDeleteCartItemByCartIdAndProductId_ThenDeleted() {
		List<Long> productIdList = List.of(1L, 2L);
		productIdList.forEach(p -> {
			cartItemRepository.deleteByCartIdAndProductId(1L, p);			
		});
		assertEquals(0, cartItemRepository.count());
	}
	
}
