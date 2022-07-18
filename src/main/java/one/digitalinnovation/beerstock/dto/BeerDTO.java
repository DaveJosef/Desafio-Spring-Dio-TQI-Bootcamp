package one.digitalinnovation.beerstock.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@NoArgsConstructor
@AllArgsConstructor
public class BeerDTO {

    private Long id = 1L;

    @NotNull
    @Size(min = 1, max = 200)
    private String name = "Nome Padrão";

    @NotNull
    @Size(min = 1, max = 200)
    private String brand = "Marca Padrão";

    @NotNull
    @Max(500)
    private int max = 100;

    @NotNull
    @Max(100)
    private int quantity = 50;

    @NotNull
    private String type = "Tipo Padrão";

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
