package one.digitalinnovation.beerstock.builder;

import lombok.Builder;
import one.digitalinnovation.beerstock.dto.BeerDTO;

@Builder
public class BeerDTOBuilder {

    @Builder.Default
    private Long id = 1L;

    @Builder.Default
    private String name = "Brahma";

    @Builder.Default
    private String brand = "Ambev";

    @Builder.Default
    private int max = 50;

    @Builder.Default
    private int quantity = 10;

    @Builder.Default
    private String type = "Lager";

    public BeerDTO toBeerDTO() {
        return new BeerDTO(
                id,
                name,
                brand,
                max,
                quantity,
                type
        );
    }
}
