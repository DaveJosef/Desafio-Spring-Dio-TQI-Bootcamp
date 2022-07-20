package one.digitalinnovation.beerstock.service;

import one.digitalinnovation.beerstock.builder.PurchaseDTOBuilder;
import one.digitalinnovation.beerstock.dto.PurchaseDTO;
import one.digitalinnovation.beerstock.entity.Purchase;
import one.digitalinnovation.beerstock.exception.NoPurchasesForBeerException;
import one.digitalinnovation.beerstock.exception.PurchaseNotFoundException;
import one.digitalinnovation.beerstock.mapper.PurchaseMapper;
import one.digitalinnovation.beerstock.repository.PurchaseRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PurchaseServiceTest {

    @Mock
    private PurchaseRepository purchaseRepository;

    private PurchaseMapper purchaseMapper = PurchaseMapper.INSTANCE;

    @InjectMocks
    private PurchaseService purchaseService;

    @Test
    void whenPurchaseInformedThenItShouldBeCreated() {

        PurchaseDTO expectedPurchaseDTO = PurchaseDTOBuilder.builder().build().toPurchaseDTO();
        Purchase expectedSavedPurchase = purchaseMapper.toModel(expectedPurchaseDTO);

        when(purchaseRepository.save(expectedSavedPurchase)).thenReturn(expectedSavedPurchase);

        PurchaseDTO createdPurchaseDTO = purchaseService.createPurchase(expectedPurchaseDTO);

        assertThat(createdPurchaseDTO.getId(), is(equalTo(expectedPurchaseDTO.getId())));
        assertThat(createdPurchaseDTO.getBeerId(), is(equalTo(expectedPurchaseDTO.getBeerId())));
        assertThat(createdPurchaseDTO.getClientId(), is(equalTo(expectedPurchaseDTO.getClientId())));
        assertThat(createdPurchaseDTO.getQuantity(), is(equalTo(expectedPurchaseDTO.getQuantity())));
    }

    @Test
    void whenValidBeerIdIsGivenThenReturnAPurchaseList() throws NoPurchasesForBeerException {

        List<PurchaseDTO> expectedFoundPurchaseDTO = Collections.singletonList(PurchaseDTOBuilder.builder().build().toPurchaseDTO());
        Purchase expectedFoundPurchase = purchaseMapper.toModel(expectedFoundPurchaseDTO.get(0));

        when(purchaseRepository.findByBeerId(expectedFoundPurchase.getBeerId())).thenReturn(Collections.singletonList(expectedFoundPurchase));

        List<PurchaseDTO> foundPurchaseDTO = purchaseService.findByBeerId(expectedFoundPurchaseDTO.get(0).getBeerId());

        assertThat(expectedFoundPurchaseDTO, is(equalTo(foundPurchaseDTO)));
    }

    @Test
    void whenNonExistentBeerIdIsGivenThenThrowAnException() {

        PurchaseDTO expectedNotFoundPurchaseDTO = PurchaseDTOBuilder.builder().build().toPurchaseDTO();

        when(purchaseRepository.findByBeerId(expectedNotFoundPurchaseDTO.getBeerId())).thenReturn(Collections.EMPTY_LIST);

        assertThrows(NoPurchasesForBeerException.class, () -> purchaseService.findByBeerId(expectedNotFoundPurchaseDTO.getBeerId()));

    }

    @Test
    void whenNonExistentPurchaseIdIsGivenThenThrowAnException() {

        PurchaseDTO expectedNotFoundPurchaseDTO = PurchaseDTOBuilder.builder().build().toPurchaseDTO();

        when(purchaseRepository.findById(expectedNotFoundPurchaseDTO.getId())).thenReturn(Optional.empty());

        assertThrows(PurchaseNotFoundException.class, () -> purchaseService.findById(expectedNotFoundPurchaseDTO.getId()));

    }
}
