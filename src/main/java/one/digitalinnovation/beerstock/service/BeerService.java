package one.digitalinnovation.beerstock.service;

import one.digitalinnovation.beerstock.entity.Beer;
import one.digitalinnovation.beerstock.repository.BeerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class BeerService {

    @Autowired
    private BeerRepository beerRepository;

    public Beer createBeer(Beer beer) {
        return beerRepository.save(beer);
    }

    public Beer findByName(String name) {
        return beerRepository.findByName(name).get();
    }

    public Collection<Beer> listAll() {
        return beerRepository.findAll();
    }

    public void deleteById(Long id) {
        beerRepository.deleteById(id);
    }
}
