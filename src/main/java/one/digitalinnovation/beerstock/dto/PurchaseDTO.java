package one.digitalinnovation.beerstock.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import one.digitalinnovation.beerstock.entity.Beer;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseDTO {

    private Long id;

    @NotNull
    @Max(100)
    private int quantity;

    @NotNull
    private Long clientId;

    @NotNull
    private Long beerId;

}
