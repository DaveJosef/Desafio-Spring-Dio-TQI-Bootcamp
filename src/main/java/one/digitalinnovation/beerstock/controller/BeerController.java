package one.digitalinnovation.beerstock.controller;

import one.digitalinnovation.beerstock.dto.BeerDTO;
import one.digitalinnovation.beerstock.dto.PurchaseDTO;
import one.digitalinnovation.beerstock.dto.QuantityDTO;
import one.digitalinnovation.beerstock.exception.*;
import one.digitalinnovation.beerstock.service.BeerService;
import one.digitalinnovation.beerstock.service.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/beers")
public class BeerController implements BeerControllerDocs {

    @Autowired
    private BeerService beerService;
    @Autowired
    private PurchaseService purchaseService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BeerDTO createBeer(@RequestBody @Valid BeerDTO beerDTO) throws BeerAlreadyRegisteredException {
        return beerService.createBeer(beerDTO);
    }

    @PostMapping("/purchase")
    @ResponseStatus(HttpStatus.CREATED)
    public PurchaseDTO createPurchase(@RequestBody @Valid PurchaseDTO purchaseDTO) {
        return purchaseService.createPurchase(purchaseDTO);
    }

    @GetMapping("/purchase/ofBeer/{beerId}")
    public List<PurchaseDTO> findPurchaseByBeerId(@PathVariable Long beerId) throws NoPurchasesForBeerException {
        return purchaseService.findByBeerId(beerId);
    }

    @GetMapping("/purchase/{id}")
    public PurchaseDTO findPurchaseById(@PathVariable Long id) throws PurchaseNotFoundException {
        return purchaseService.findById(id);
    }

    @Override
    @GetMapping("/{name}")
    public BeerDTO findByName(@PathVariable String name) throws BeerNotFoundException {
        return beerService.findByName(name);
    }

    @Override
    @GetMapping
    public List<BeerDTO> listBeers() {
        return beerService.listAll();
    }

    @Override
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) throws BeerNotFoundException {
        beerService.deleteById(id);
    }

    @PatchMapping("/{id}/increment")
    public BeerDTO increment(@PathVariable Long id, @RequestBody @Valid QuantityDTO quantityDTO) throws BeerNotFoundException, BeerStockExceededException {
        return beerService.increment(id, quantityDTO.getQuantity());
    }

    @PatchMapping("/{id}/decrement")
    public BeerDTO decrement(@PathVariable Long id, @RequestBody @Valid QuantityDTO quantityDTO) throws BeerNotFoundException, NegativeBeerStockException {
        return beerService.decrement(id, quantityDTO.getQuantity());
    }
}
