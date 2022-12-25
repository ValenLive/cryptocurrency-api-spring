package com.oril.cryptocurrencyspring.util;

import com.oril.cryptocurrencyspring.model.Data;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.Writer;
import java.util.List;

@Component
public class CsvFileGenerator {

    public void writeStudentsToCsv(List<Data> cryptoCurrencies, Writer writer) {
        try {
            CSVPrinter printer = new CSVPrinter(writer, CSVFormat.DEFAULT);
            for (Data crypto : cryptoCurrencies) {
                printer.printRecord(crypto.pair(), crypto.low(), crypto.high());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
