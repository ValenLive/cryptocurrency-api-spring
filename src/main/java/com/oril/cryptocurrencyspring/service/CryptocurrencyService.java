package com.oril.cryptocurrencyspring.service;

import com.oril.cryptocurrencyspring.model.Data;
import java.util.List;
import java.util.Optional;

public interface CryptocurrencyService {

    List<Data> findAll();

    List<Data> callCexApi();

    Optional<Data> getMinCryptoByCurrencyName(String currencyName);

    Optional<Data> getMaxCryptoByCurrencyName(String currencyName);

    Optional<List<Data>> getAllCryptocurrencies(String name, Long page, Long size);
}
