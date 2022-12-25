package com.oril.cryptocurrencyspring.service;

import com.oril.cryptocurrencyspring.model.Data;
import com.oril.cryptocurrencyspring.repository.CryptocurrencyRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
public class CryptocurrencyServiceTest {
    private static Data data;

    @MockBean
    private CryptocurrencyRepository cryptocurrencyRepository;

    @MockBean
    private CryptocurrencyServiceImpl cryptocurrencyService;

    @BeforeAll
    public static void setup() {
        data = new Data("1671923759",
                "BTC:USD",
                "16808.5",
                "16920.6",
                "16850.9",
                "6.74973413",
                "512.93447321",
                "36.7",
                "0.22",
                BigDecimal.valueOf(16847.8),
                BigDecimal.valueOf(16851.1)
        );
    }

    @Test
    public void getMinCryptoByCurrencyNameTest(){
        when(cryptocurrencyRepository.save(any(Data.class))).thenReturn(data);
        assertNotNull(cryptocurrencyService.getMinCryptoByCurrencyName("BTC"));
    }

    @Test
    public void getMaxCryptoByCurrencyNameTest(){
        when(cryptocurrencyRepository.save(any(Data.class))).thenReturn(data);
        assertNotNull(cryptocurrencyService.getMaxCryptoByCurrencyName("BTC"));
    }

    @Test
    public void getAllCryptocurrenciesTest(){
        when(cryptocurrencyRepository.save(any(Data.class))).thenReturn(data);
        assertNotNull(cryptocurrencyService.getAllCryptocurrencies("BTC", 1L, 1L));
    }

}
