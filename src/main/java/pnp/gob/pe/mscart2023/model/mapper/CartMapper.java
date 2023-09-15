package pnp.gob.pe.mscart2023.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import org.springframework.beans.factory.annotation.Autowired;
import pnp.gob.pe.mscart2023.model.dto.CartItemResponseDto;
import pnp.gob.pe.mscart2023.model.dto.CartResponseDto;
import pnp.gob.pe.mscart2023.model.dto.ProductResponseDto;
import pnp.gob.pe.mscart2023.model.entity.CartEntity;
import pnp.gob.pe.mscart2023.model.entity.CartItemEntity;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CartMapper {

	@Mapping(source = "entity.customerId", target = "customerId")
	@Mapping(source = "entity.creationDate", target = "creationDate")
	@Mapping(source = "entity.items", target = "items", qualifiedByName = "entityToResponseForItems")
	CartResponseDto entityToResponse(CartEntity entity);

	@Mapping(source = "entity.productId", target = "productId")
	@Mapping(source = "entity.name", target = "name")
	@Mapping(source = "entity.price", target = "price")
	@Mapping(source = "entity.quantity", target = "quantity")
	@Mapping(source = "entity.subTotal", target = "subTotal")
	@Mapping(source = "entity.creationDate", target = "creationDate")
	CartItemResponseDto entityToResponse(CartItemEntity entity);

	@Named("entityToResponseForItems")
	default List<CartItemResponseDto> entityToResponseForItems(List<CartItemEntity> items){
		return items.stream().map(p -> entityToResponse(p)).toList();
	}

	@Mapping(source = "response.id", target = "productId")
	@Mapping(source = "response.name", target = "name")
	@Mapping(source = "response.price", target = "price")
	@Mapping(source = "quantity", target = "quantity")
	CartItemEntity responseToEntity(ProductResponseDto response, Integer quantity);
}
