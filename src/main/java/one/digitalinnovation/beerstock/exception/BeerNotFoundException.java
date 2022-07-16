package one.digitalinnovation.beerstock.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class BeerNotFoundException extends Exception {

    public BeerNotFoundException(String name) {
        super(String.format("It was not possible to find a beer named %s", name));
    }

    public BeerNotFoundException(Long id) {
        super(String.format("It was not possible to find a beer with id %s", id));
    }
}
