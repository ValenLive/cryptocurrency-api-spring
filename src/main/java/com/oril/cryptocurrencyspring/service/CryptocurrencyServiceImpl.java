package com.oril.cryptocurrencyspring.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.oril.cryptocurrencyspring.model.Crypto;
import com.oril.cryptocurrencyspring.model.Data;
import com.oril.cryptocurrencyspring.repository.CryptocurrencyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;


@Service
public class CryptocurrencyServiceImpl implements CryptocurrencyService {

    private final CryptocurrencyRepository cryptocurrencyRepository;

    @Autowired
    public CryptocurrencyServiceImpl(CryptocurrencyRepository cryptocurrencyRepository) {
        this.cryptocurrencyRepository = cryptocurrencyRepository;
    }

    @PostConstruct
    private void postConstruct() {
        List<Data> dataList = callCexApi();
        List<Data> filteredData = filterData(dataList);
        cryptocurrencyRepository.saveAll(filteredData);
    }

    @PreDestroy
    private void destroy() {
        cryptocurrencyRepository.deleteAll();
    }

    @Override
    public List<Data> callCexApi() {
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://cex.io/api/tickers/BTC/USD,ETH/USD,XRP/USD";
        String crypto = restTemplate.getForObject(url, String.class);

        ObjectMapper mapper = new ObjectMapper();
        Crypto value;
        try {
            value = mapper.readValue(crypto, Crypto.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return value.data();
    }

    private List<Data> filterData(List<Data> dataList) {
        return dataList.stream()
                .filter(data -> data.pair().equals("BTC:USD") ||
                        data.pair().equals("XRP:USD") ||
                        data.pair().equals("ETH:USD"))
                .toList();
    }

    @Override
    public List<Data> findAll() {
        return cryptocurrencyRepository.findAll();
    }

    @Override
    public Optional<Data> getMinCryptoByCurrencyName(String currencyName) {
        return cryptocurrencyRepository.findAllByPair(currencyName + ":USD")
                .stream()
                .min(Comparator.comparing(Data::bid));
    }

    @Override
    public Optional<Data> getMaxCryptoByCurrencyName(String currencyName) {
        return cryptocurrencyRepository.findAllByPair(currencyName + ":USD")
                .stream()
                .max(Comparator.comparing(Data::bid));
    }

    @Override
    public Optional<List<Data>> getAllCryptocurrencies(String name, Long page, Long size) {
        Pageable paging = PageRequest.of(Math.toIntExact(page), Math.toIntExact(size), Sort.by("bid").ascending());

        Page<Data> pagedResult = cryptocurrencyRepository.findAllByPair(name + ":USD", paging);

        if (pagedResult.hasContent()) {
            return Optional.of(pagedResult.getContent());
        } else {
            return Optional.of(new ArrayList<>());
        }
    }

}
