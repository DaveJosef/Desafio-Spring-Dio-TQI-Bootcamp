package one.digitalinnovation.beerstock.controller;

import one.digitalinnovation.beerstock.entity.Beer;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Collection;

public interface BeerControllerDocs {

    Beer createBeer(Beer beerDTO);

    Beer findByName(@PathVariable String name);

    Collection<Beer> listBeers();

    void deleteById(@PathVariable Long id);
}
