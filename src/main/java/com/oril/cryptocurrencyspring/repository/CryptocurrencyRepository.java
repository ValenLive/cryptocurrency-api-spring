package com.oril.cryptocurrencyspring.repository;

import com.oril.cryptocurrencyspring.model.Data;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CryptocurrencyRepository extends MongoRepository<Data, String>{

    @Override
    void deleteAll();

    @Query("{'pair': ?0}")
    List<Data> findAllByPair(String currencyName);

    @Query("{'pair': ?0}")
    Page<Data> findAllByPair(String pair, Pageable pageable);

}

