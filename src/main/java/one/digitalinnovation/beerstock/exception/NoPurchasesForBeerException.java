package one.digitalinnovation.beerstock.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NoPurchasesForBeerException extends Exception {

    public NoPurchasesForBeerException(Long beerId) {
        super(String.format("It was not possible to find a purchase for beer with ID: %s", beerId));
    }
}
