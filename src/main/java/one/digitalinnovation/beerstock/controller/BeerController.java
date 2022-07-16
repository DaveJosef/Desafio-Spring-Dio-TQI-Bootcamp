package one.digitalinnovation.beerstock.controller;

import one.digitalinnovation.beerstock.entity.Beer;
import one.digitalinnovation.beerstock.exception.BeerAlreadyRegisteredException;
import one.digitalinnovation.beerstock.exception.BeerNotFoundException;
import one.digitalinnovation.beerstock.service.BeerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/api/v1/beers")
public class BeerController implements BeerControllerDocs {

    @Autowired
    private BeerService beerService;

    @Override
    @PostMapping
    public Beer createBeer(Beer beerDTO) throws BeerAlreadyRegisteredException {
        return beerService.createBeer(beerDTO);
    }

    @Override
    @GetMapping("/{name}")
    public Beer findByName(@PathVariable String name) throws BeerNotFoundException {
        return beerService.findByName(name);
    }

    @Override
    @GetMapping
    public Collection<Beer> listBeers() {
        return beerService.listAll();
    }

    @Override
    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) throws BeerNotFoundException {
        beerService.deleteById(id);
    }
}
