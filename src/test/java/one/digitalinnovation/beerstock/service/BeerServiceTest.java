package one.digitalinnovation.beerstock.service;

import one.digitalinnovation.beerstock.mapper.BeerMapper;
import one.digitalinnovation.beerstock.repository.BeerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class BeerServiceTest {

    private static final long INVALID_BEER_ID = 1L;

    @Mock
    private BeerRepository beerRepository;

    private BeerMapper beerMapper = BeerMapper.INSTANCE;

    @InjectMocks
    private BeerService beerService;

    @Test
    void whenBeerInformedThenItShouldBeCreated() {

    }

    @Test
    void whenAlreadyBeerRegisteredInformedThenAnExceptionShouldBeThrown() {

    }

    @Test
    void whenValidBeerNameIsGivenThenReturnABeer() {

    }

    @Test
    void whenNoRegisteredBeerNameIsGivenThenThrowAnException() {

    }

    @Test
    void whenListBeerIsCalledThenReturnAListOfBeers() {

    }

    @Test
    void whenListBeerIsCalledThenReturnAnEmptyListOfBeers() {

    }

    @Test
    void whenExclusionIsCalledWithValidIdThenABeerShouldBeDeleted() {

    }

    @Test
    void whenIncrementIsCalledThenIncrementBeerStock() {

    }

    @Test
    void whenIncrementIsGreaterThanMaxThenThrowException() {

    }

    @Test
    void whenIncrementAfterSumIsGreaterThanMaxThenThrowException() {

    }

    @Test
    void whenIncrementIsCalledWithInvalidIdThenThrowException() {

    }
}