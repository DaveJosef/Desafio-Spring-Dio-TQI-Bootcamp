package one.digitalinnovation.beerstock.controller;

import one.digitalinnovation.beerstock.entity.Beer;
import one.digitalinnovation.beerstock.exception.BeerAlreadyRegisteredException;
import one.digitalinnovation.beerstock.exception.BeerNotFoundException;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Collection;

public interface BeerControllerDocs {

    Beer createBeer(Beer beerDTO) throws BeerAlreadyRegisteredException;

    Beer findByName(@PathVariable String name) throws BeerNotFoundException;

    Collection<Beer> listBeers();

    void deleteById(@PathVariable Long id) throws BeerNotFoundException;
}
