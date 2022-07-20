package one.digitalinnovation.beerstock.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class NegativeBeerStockException extends Exception {

    public NegativeBeerStockException(Long id, int quantityToDecrement) {
        super(String.format("Beer with ID: %s exceeds min stock capacity by decreasing in %s unities", id, quantityToDecrement));
    }
}
