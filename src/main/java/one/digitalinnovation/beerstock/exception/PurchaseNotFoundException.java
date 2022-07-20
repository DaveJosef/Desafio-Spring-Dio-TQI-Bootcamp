package one.digitalinnovation.beerstock.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class PurchaseNotFoundException extends Exception {

    public PurchaseNotFoundException(Long id) {
        super(String.format("It was not possible to find a purchase with ID: %s", id));
    }
}
