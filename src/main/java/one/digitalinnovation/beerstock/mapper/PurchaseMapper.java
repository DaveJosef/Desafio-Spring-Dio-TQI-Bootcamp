package one.digitalinnovation.beerstock.mapper;

import one.digitalinnovation.beerstock.dto.PurchaseDTO;
import one.digitalinnovation.beerstock.entity.Purchase;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PurchaseMapper {

    PurchaseMapper INSTANCE = Mappers.getMapper(PurchaseMapper.class);

    Purchase toModel(PurchaseDTO purchaseDTO);

    PurchaseDTO toDTO(Purchase purchase);
}
