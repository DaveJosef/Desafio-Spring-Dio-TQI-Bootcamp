package one.digitalinnovation.beerstock.builder;

import lombok.Builder;
import one.digitalinnovation.beerstock.dto.PurchaseDTO;

@Builder
public class PurchaseDTOBuilder {

    @Builder.Default
    private Long id = 1L;

    @Builder.Default
    private int quantity = 10;

    @Builder.Default
    private Long clientId = 1L;

    @Builder.Default
    private Long beerId = 1L;

    public PurchaseDTO toPurchaseDTO() {
        return new PurchaseDTO(
                id,
                quantity,
                clientId,
                beerId
        );
    }
}
