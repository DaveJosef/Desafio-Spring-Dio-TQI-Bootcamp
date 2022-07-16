package one.digitalinnovation.beerstock.service;

import one.digitalinnovation.beerstock.entity.Beer;
import one.digitalinnovation.beerstock.exception.BeerAlreadyRegisteredException;
import one.digitalinnovation.beerstock.exception.BeerNotFoundException;
import one.digitalinnovation.beerstock.repository.BeerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
public class BeerService {

    @Autowired
    private BeerRepository beerRepository;

    public Beer createBeer(Beer beer) throws BeerAlreadyRegisteredException {
        verifyIfIsAlreadyRegistered(beer.getName());
        return beerRepository.save(beer);
    }

    public Beer findByName(String name) throws BeerNotFoundException {
        return beerRepository.findByName(name).orElseThrow(() -> new BeerNotFoundException(name));
    }

    public Collection<Beer> listAll() {
        return beerRepository.findAll();
    }

    public void deleteById(Long id) throws BeerNotFoundException {
        verifyIfExists(id);
        beerRepository.deleteById(id);
    }

    private void verifyIfIsAlreadyRegistered(String name) throws BeerAlreadyRegisteredException {
        Optional<Beer> optionalSavedBeer = beerRepository.findByName(name);
        if (optionalSavedBeer.isPresent()) {
            throw new BeerAlreadyRegisteredException(name);
        }
    }

    private Beer verifyIfExists(Long id) throws BeerNotFoundException {
        return beerRepository.findById(id)
                .orElseThrow(() -> new BeerNotFoundException(id));
    }
}
