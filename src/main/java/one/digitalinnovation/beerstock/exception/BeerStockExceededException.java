package one.digitalinnovation.beerstock.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BeerStockExceededException extends Exception {

    public BeerStockExceededException(Long id, int quantityToIncrement) {
        super(String.format("Beer with ID: %s exceeds max stock capacity by increasing in %s unities", id, quantityToIncrement));
    }
}
