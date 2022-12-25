package com.oril.cryptocurrencyspring.controller;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
public class CryptocurrencyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @SneakyThrows
    public void minPricePageTest() {
        mockMvc.perform(get("/cryptocurrencies/minprice/{name}", "BTC"))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.pair").value("BTC:USD"));
    }

    @Test
    @SneakyThrows
    public void maxPricePageTest() {
        mockMvc.perform(get("/cryptocurrencies/maxprice/{name}", "XRP"))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.pair").value("XRP:USD"));
    }

    @Test
    @SneakyThrows
    public void getNamePageSizeTest(){
        mockMvc.perform(get("/cryptocurrencies/{name}/{page}/{size}", "ETH", 1, 1))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().contentType("application/json"));
    }

    @Test
    @SneakyThrows
    public void exportIntoCsvTest(){
        mockMvc.perform(get("/cryptocurrencies/csv"))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().contentType("text/csv"))
                .andExpect(header().exists("Content-Disposition"));
    }

}
