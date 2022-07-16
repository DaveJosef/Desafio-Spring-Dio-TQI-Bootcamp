package one.digitalinnovation.beerstock.service;

import one.digitalinnovation.beerstock.dto.BeerDTO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BeerService {

    public BeerDTO createBeer(BeerDTO beerDTO) {
        return new BeerDTO();
    }

    public BeerDTO findByName(String name) {
        return new BeerDTO();
    }

    public List<BeerDTO> listAll() {
        return new ArrayList<>(){{
            add(new BeerDTO());
            add(new BeerDTO());
            add(new BeerDTO());
        }};
    }

    public void deleteById(Long id) {
    }
}
