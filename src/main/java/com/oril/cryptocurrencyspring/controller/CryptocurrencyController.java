package com.oril.cryptocurrencyspring.controller;

import com.oril.cryptocurrencyspring.exception.ResourceNotFoundException;
import com.oril.cryptocurrencyspring.model.Data;
import com.oril.cryptocurrencyspring.service.CryptocurrencyService;
import com.oril.cryptocurrencyspring.util.CsvFileGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/cryptocurrencies")
public class CryptocurrencyController {
    private final CryptocurrencyService cryptocurrencyService;
    private final CsvFileGenerator csvFileGenerator;

    @Autowired
    public CryptocurrencyController(CryptocurrencyService cryptocurrencyService, CsvFileGenerator csvFileGenerator) {
        this.cryptocurrencyService = cryptocurrencyService;
        this.csvFileGenerator = csvFileGenerator;
    }

    @GetMapping("/minprice/{name}")
    public ResponseEntity<Data> getMinPrice(@PathVariable String name) {
        Data minCrypto = cryptocurrencyService.getMinCryptoByCurrencyName(name)
                .orElseThrow(() -> new ResourceNotFoundException("Maximum value of cryptocurrency with name '" + name + "' not found"));

        return ResponseEntity.ok().body(minCrypto);
    }

    @GetMapping("/maxprice/{name}")
    public ResponseEntity<Data> getMaxPrice(@PathVariable String name) {
        Data maxCrypto = cryptocurrencyService.getMaxCryptoByCurrencyName(name)
                .orElseThrow(() -> new ResourceNotFoundException("Minimum value of cryptocurrency with name '" + name + "' not found"));

        return ResponseEntity.ok().body(maxCrypto);
    }

    @GetMapping("{name}/{page}/{size}")
    public ResponseEntity<List<Data>> getNamePageSize(@PathVariable String name, @PathVariable(required = false) Optional<Long> page, @PathVariable(required = false) Optional<Long> size) {
        List<Data> cryptocurrencies = cryptocurrencyService.getAllCryptocurrencies(name, page.orElse(0L), size.orElse(10L))
                .orElseThrow(() -> new ResourceNotFoundException("Currency doesn't match"));

        return ResponseEntity.ok().body(cryptocurrencies);
    }


    @GetMapping("/csv")
    public void exportIntoCSV(HttpServletResponse response) throws IOException {
        response.setContentType("text/csv");
        response.addHeader("Content-Disposition", "attachment; filename=\"crypto.csv\"");
        csvFileGenerator.writeStudentsToCsv(cryptocurrencyService.findAll(), response.getWriter());
    }
}

