package one.digitalinnovation.beerstock.controller;

import one.digitalinnovation.beerstock.dto.BeerDTO;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface BeerControllerDocs {

    BeerDTO createBeer(BeerDTO beerDTO);

    BeerDTO findByName(@PathVariable String name);

    List<BeerDTO> listBeers();

    void deleteById(@PathVariable Long id);
}
