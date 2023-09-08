package pnp.gob.pe.mscart2023.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import pnp.gob.pe.mscart2023.model.entity.CartEntity;

public interface CartRepository extends CrudRepository<CartEntity, Long>{
	
	Optional<CartEntity> findByCustomerId(Long customerId);
}
