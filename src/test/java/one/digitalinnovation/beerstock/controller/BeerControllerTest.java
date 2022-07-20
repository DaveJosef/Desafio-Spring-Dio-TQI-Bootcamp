package one.digitalinnovation.beerstock.controller;

import one.digitalinnovation.beerstock.builder.BeerDTOBuilder;
import one.digitalinnovation.beerstock.builder.PurchaseDTOBuilder;
import one.digitalinnovation.beerstock.dto.BeerDTO;
import one.digitalinnovation.beerstock.dto.PurchaseDTO;
import one.digitalinnovation.beerstock.dto.QuantityDTO;
import one.digitalinnovation.beerstock.exception.*;
import one.digitalinnovation.beerstock.service.BeerService;
import one.digitalinnovation.beerstock.service.PurchaseService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import java.util.Collections;

import static one.digitalinnovation.beerstock.utils.JsonConvertionUtils.asJsonString;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class BeerControllerTest {

    private static final String BEER_API_URL_PATH = "/api/v1/beers";
    private static final Long VALID_BEER_ID = 1L;
    private static final Long INVALID_BEER_ID = 2L;
    private static final String BEER_API_SUBPATH_INCREMENT_URL = "/increment";
    private static final String BEER_API_SUBPATH_DECREMENT_URL = "/decrement";
    private static final String BEER_API_SUBPATH_PURCHASE_URL = "/purchase";
    private static final String BEER_API_SUBPATH_PURCHASE_OF_BEER_URL = "/purchase/ofBeer";

    private MockMvc mockMvc;

    @Mock
    private BeerService beerService;
    @Mock
    private PurchaseService purchaseService;

    @InjectMocks
    private BeerController beerController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(beerController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .setViewResolvers((s, locale) -> new MappingJackson2JsonView())
                .build();
    }

    @Test
    void whenPOSTIsCalledThenABeerIsCreated() throws Exception {
        // given
        BeerDTO beerDTO = BeerDTOBuilder.builder().build().toBeerDTO();

        // when
        when(beerService.createBeer(beerDTO)).thenReturn(beerDTO);

        // then
        mockMvc.perform(post(BEER_API_URL_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(beerDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is(beerDTO.getName())))
                .andExpect(jsonPath("$.brand", is(beerDTO.getBrand())))
                .andExpect(jsonPath("$.type", is(beerDTO.getType().toString())));
    }

    @Test
    void whenPOSTPurchaseIsCalledThenAPurchaseIsCreated() throws Exception {

        PurchaseDTO purchaseDTO = PurchaseDTOBuilder.builder().build().toPurchaseDTO();

        when(purchaseService.createPurchase(purchaseDTO)).thenReturn(purchaseDTO);

        mockMvc.perform(
                post(BEER_API_URL_PATH + "/" + BEER_API_SUBPATH_PURCHASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(purchaseDTO)))
                        .andExpect(status().isCreated())
                        .andExpect(jsonPath("$.quantity", is(purchaseDTO.getQuantity())));
    }

    @Test
    void whenPOSTPurchaseIsCalledWithoutRequiredFieldThenAnErrorIsReturned() throws Exception {

        PurchaseDTO purchaseDTO = PurchaseDTOBuilder.builder().build().toPurchaseDTO();
        purchaseDTO.setBeerId(null);

        mockMvc.perform(post(BEER_API_URL_PATH + "/" + BEER_API_SUBPATH_PURCHASE_URL).contentType(MediaType.APPLICATION_JSON).content(asJsonString(purchaseDTO))).andExpect(status().isBadRequest());

    }

    @Test
    void whenPOSTIsCalledWithoutRequiredFieldThenAnErrorIsReturned() throws Exception {

        BeerDTO beerDTO = BeerDTOBuilder.builder().build().toBeerDTO();
        beerDTO.setBrand(null);

        mockMvc.perform(post(BEER_API_URL_PATH).contentType(MediaType.APPLICATION_JSON).content(asJsonString(beerDTO))).andExpect(status().isBadRequest());

    }

    @Test
    void whenGETPurchaseIsCalledWithValidIdThenOkStatusIsReturned() throws Exception {

        PurchaseDTO purchaseDTO = PurchaseDTOBuilder.builder().build().toPurchaseDTO();

        when(purchaseService.findById(purchaseDTO.getId())).thenReturn(purchaseDTO);

        mockMvc.perform(
                get(BEER_API_URL_PATH + "/" + BEER_API_SUBPATH_PURCHASE_URL + "/" + purchaseDTO.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.quantity", is(purchaseDTO.getQuantity())));
    }

    @Test
    void whenGETIsCalledWithValidNameThenOkStatusIsReturned() throws Exception {

        BeerDTO beerDTO = BeerDTOBuilder.builder().build().toBeerDTO();

        when(beerService.findByName(beerDTO.getName())).thenReturn(beerDTO);

        mockMvc.perform(get(BEER_API_URL_PATH + "/" + beerDTO.getName()).contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.name", is(beerDTO.getName())))
                .andExpect(jsonPath("$.brand", is(beerDTO.getBrand())))
                .andExpect(jsonPath("$.type", is(beerDTO.getType().toString())));
    }

    @Test
    void whenGETPurchaseIsCalledWithoutRegisteredIdThenNotFoundStatusIsReturned() throws Exception {

        PurchaseDTO purchaseDTO = PurchaseDTOBuilder.builder().build().toPurchaseDTO();

        when(purchaseService.findById(purchaseDTO.getId())).thenThrow(PurchaseNotFoundException.class);

        mockMvc.perform(get(BEER_API_URL_PATH + "/" + BEER_API_SUBPATH_PURCHASE_URL + "/" + purchaseDTO.getId()).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isNotFound());

    }

    @Test
    void whenGETPurchaseIsCalledWithoutRegisteredBeerIdThenNotFoundStatusIsReturned() throws Exception {

        PurchaseDTO purchaseDTO = PurchaseDTOBuilder.builder().build().toPurchaseDTO();

        when(purchaseService.findByBeerId(purchaseDTO.getBeerId())).thenThrow(NoPurchasesForBeerException.class);

        mockMvc.perform(get(BEER_API_URL_PATH + "/" + BEER_API_SUBPATH_PURCHASE_OF_BEER_URL + "/" + purchaseDTO.getBeerId()).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isNotFound());

    }

    @Test
    void whenGETIsCalledWithoutRegisteredNameThenNotFoundStatusIsReturned() throws Exception {

        BeerDTO beerDTO = BeerDTOBuilder.builder().build().toBeerDTO();

        when(beerService.findByName(beerDTO.getName())).thenThrow(BeerNotFoundException.class);

        mockMvc.perform(get(BEER_API_URL_PATH + "/" + beerDTO.getName()).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void whenGETListWithBeersIsCalledThenOkStatusIsReturned() throws Exception {

        BeerDTO beerDTO = BeerDTOBuilder.builder().build().toBeerDTO();

        when(beerService.listAll()).thenReturn(Collections.singletonList(beerDTO));

        mockMvc.perform(get(BEER_API_URL_PATH).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name", is(beerDTO.getName())))
                .andExpect(jsonPath("$[0].brand", is(beerDTO.getBrand())))
                .andExpect(jsonPath("$[0].type", is(beerDTO.getType().toString())));
    }

    @Test
    void whenGETListWithoutBeersIsCalledThenOkStatusIsReturned() throws Exception {

        BeerDTO beerDTO = BeerDTOBuilder.builder().build().toBeerDTO();

        when(beerService.listAll()).thenReturn(Collections.singletonList(beerDTO));

        mockMvc.perform(get(BEER_API_URL_PATH).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void whenDELETEIsCalledWithValidIdThenNoContentStatusIsReturned() throws Exception {

        BeerDTO beerDTO = BeerDTOBuilder.builder().build().toBeerDTO();

        doNothing().when(beerService).deleteById(beerDTO.getId());

        mockMvc.perform(delete(BEER_API_URL_PATH + "/" + beerDTO.getId()).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

    }

    @Test
    void whenDELETEIsCalledWithInvalidIdThenNotFoundStatusIsReturned() throws Exception {

        doThrow(BeerNotFoundException.class).when(beerService).deleteById(INVALID_BEER_ID);

        mockMvc.perform(delete(BEER_API_URL_PATH + "/" + INVALID_BEER_ID).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

    }

    @Test
    void whenPATCHIsCalledToIncrementDiscountThenOkStatusIsReturned() throws Exception {

        QuantityDTO quantityDTO = QuantityDTO.builder().quantity(10).build();
        BeerDTO beerDTO = BeerDTOBuilder.builder().build().toBeerDTO();
        beerDTO.setQuantity(beerDTO.getQuantity() + quantityDTO.getQuantity());

        when(beerService.increment(VALID_BEER_ID, quantityDTO.getQuantity())).thenReturn(beerDTO);

        mockMvc.perform(patch(BEER_API_URL_PATH + "/" + VALID_BEER_ID + BEER_API_SUBPATH_INCREMENT_URL).contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(quantityDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(beerDTO.getName())))
                .andExpect(jsonPath("$.brand", is(beerDTO.getBrand())))
                .andExpect(jsonPath("$.type", is(beerDTO.getType().toString())))
                .andExpect(jsonPath("$.quantity", is(beerDTO.getQuantity())));

    }

    @Test
    void whenPATCHIsCalledToIncrementDiscountAndQuantityPosIncrementIsGreaterThanMaxThenBadRequestStatusIsReturned() throws Exception {

        QuantityDTO quantityDTO = QuantityDTO.builder().quantity(10).build();
        doThrow(BeerStockExceededException.class).when(beerService).increment(VALID_BEER_ID, quantityDTO.getQuantity());

        mockMvc.perform(patch(BEER_API_URL_PATH + "/" + VALID_BEER_ID + "/" + BEER_API_SUBPATH_INCREMENT_URL).contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(quantityDTO)))
                .andExpect(status().isBadRequest());

    }

    @Test
    void whenPATCHIsCalledToDecrementDiscountThenOkStatusIsReturned() throws Exception {

        QuantityDTO quantityDTO = QuantityDTO.builder().quantity(10).build();
        BeerDTO beerDTO = BeerDTOBuilder.builder().build().toBeerDTO();
        beerDTO.setQuantity(beerDTO.getQuantity() - quantityDTO.getQuantity());

        when(beerService.decrement(VALID_BEER_ID, quantityDTO.getQuantity())).thenReturn(beerDTO);

        mockMvc.perform(patch(BEER_API_URL_PATH + "/" + VALID_BEER_ID + BEER_API_SUBPATH_DECREMENT_URL).contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(quantityDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(beerDTO.getName())))
                .andExpect(jsonPath("$.brand", is(beerDTO.getBrand())))
                .andExpect(jsonPath("$.type", is(beerDTO.getType().toString())))
                .andExpect(jsonPath("$.quantity", is(beerDTO.getQuantity())));

    }

    @Test
    void whenPATCHIsCalledToDecrementDiscountAndQuantityPosDecrementIsLesserThanMinThenBadRequestStatusIsReturned() throws Exception {

        QuantityDTO quantityDTO = QuantityDTO.builder().quantity(10).build();
        doThrow(NegativeBeerStockException.class).when(beerService).decrement(VALID_BEER_ID, quantityDTO.getQuantity());

        mockMvc.perform(patch(BEER_API_URL_PATH + "/" + VALID_BEER_ID + "/" + BEER_API_SUBPATH_DECREMENT_URL).contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(quantityDTO)))
                .andExpect(status().isBadRequest());

    }

}
