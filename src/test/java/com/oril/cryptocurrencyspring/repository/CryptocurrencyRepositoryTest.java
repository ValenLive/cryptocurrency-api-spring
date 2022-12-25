package com.oril.cryptocurrencyspring.repository;

import com.oril.cryptocurrencyspring.model.Data;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import java.math.BigDecimal;
import java.util.List;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
public class CryptocurrencyRepositoryTest {

    private static Data data;
    @MockBean
    private CryptocurrencyRepository cryptocurrencyRepository;

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
    public void deleteAllTest() {
        cryptocurrencyRepository.save(data);
        cryptocurrencyRepository.deleteAll();

        int actual = cryptocurrencyRepository.findAll().size();
        Assertions.assertEquals(0, actual);
    }

    @Test
    public void findAllByPairTest() {
        cryptocurrencyRepository.save(data);
        List<Data> dataList = cryptocurrencyRepository.findAllByPair("BTC");
        Assertions.assertNotNull(dataList);
    }

    @Test
    public void findAllByPairPageTest() {
        cryptocurrencyRepository.save(data);
        Pageable page = PageRequest.of(0, 1, Sort.by("dbid").ascending());
        Page<Data> pagedResult = cryptocurrencyRepository.findAllByPair("BBTC", page);
        Assertions.assertNull(pagedResult);
    }

}
