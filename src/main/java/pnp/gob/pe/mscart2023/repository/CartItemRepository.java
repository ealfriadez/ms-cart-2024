package pnp.gob.pe.mscart2023.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import pnp.gob.pe.mscart2023.model.entity.CartItemEntity;

public interface CartItemRepository extends CrudRepository<CartItemEntity, Long> {
	
	@Query("from CartItemEntity c where cart.customerId = :customerId and productId = :productId")
	Optional<CartItemEntity> findByCustomerIdAndProductId(@Param("customerId") Long customerId, @Param("productId") Long productId);
	
	void deleteByCartIdAndProductId(Long cartId, Long productId);

}
