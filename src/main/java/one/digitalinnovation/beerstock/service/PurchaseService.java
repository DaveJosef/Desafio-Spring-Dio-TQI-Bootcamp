package one.digitalinnovation.beerstock.service;

import lombok.AllArgsConstructor;
import one.digitalinnovation.beerstock.dto.PurchaseDTO;
import one.digitalinnovation.beerstock.entity.Purchase;
import one.digitalinnovation.beerstock.exception.NoPurchasesForBeerException;
import one.digitalinnovation.beerstock.exception.PurchaseNotFoundException;
import one.digitalinnovation.beerstock.mapper.PurchaseMapper;
import one.digitalinnovation.beerstock.repository.PurchaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class PurchaseService {

    private final PurchaseRepository purchaseRepository;
    private final PurchaseMapper purchaseMapper = PurchaseMapper.INSTANCE;

    public PurchaseDTO createPurchase(PurchaseDTO purchaseDTO) {
        Purchase purchase = purchaseMapper.toModel(purchaseDTO);
        Purchase savedPurchase = purchaseRepository.save(purchase);
        return purchaseMapper.toDTO(savedPurchase);
    }

    public List<PurchaseDTO> findByBeerId(Long beerId) throws NoPurchasesForBeerException {
        List<Purchase> purchases = purchaseRepository.findByBeerId(beerId);
        if (purchases.size() > 0) {
            return purchases.stream().map(purchaseMapper::toDTO).collect(Collectors.toList());
        }
        throw new NoPurchasesForBeerException(beerId);
    }

    public PurchaseDTO findById(Long id) throws PurchaseNotFoundException {
        Purchase purchase = purchaseRepository.findById(id).orElseThrow(() -> new PurchaseNotFoundException(id));
        return purchaseMapper.toDTO(purchase);
    }

}
