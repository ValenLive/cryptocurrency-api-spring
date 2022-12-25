package com.oril.cryptocurrencyspring;

import com.oril.cryptocurrencyspring.controller.CryptocurrencyController;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
class CryptocurrencySpringApplicationTests {

    @Autowired
    CryptocurrencyController cryptocurrencyController;

    @Test
    @SneakyThrows
    public void contextLoads() {
        assertThat(cryptocurrencyController).isNotNull();
    }

}
