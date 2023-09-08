package pnp.gob.pe.mscart2023.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import pnp.gob.pe.mscart2023.model.dto.CartResponseDto;
import pnp.gob.pe.mscart2023.model.entity.CartEntity;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CartMapper {

	CartResponseDto entityToResponse(CartEntity entity);
}
