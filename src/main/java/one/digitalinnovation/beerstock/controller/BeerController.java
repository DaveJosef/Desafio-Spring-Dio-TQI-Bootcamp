package one.digitalinnovation.beerstock.controller;

import one.digitalinnovation.beerstock.dto.BeerDTO;
import one.digitalinnovation.beerstock.service.BeerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/beers")
public class BeerController implements BeerControllerDocs {

    @Autowired
    private BeerService beerService;

    @Override
    @PostMapping
    public BeerDTO createBeer(BeerDTO beerDTO) {
        return beerService.createBeer(beerDTO);
    }

    @Override
    @GetMapping("/{name}")
    public BeerDTO findByName(@PathVariable String name) {
        return beerService.findByName(name);
    }

    @Override
    @GetMapping
    public List<BeerDTO> listBeers() {
        return beerService.listAll();
    }

    @Override
    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        beerService.deleteById(id);
    }
}
