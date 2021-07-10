package com.hakkazhong.wms.stock;

import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

import reactor.core.publisher.Mono;

@Repository
public interface StockRepository extends R2dbcRepository<Stock, Long> {

	Mono<Stock> findByCodeAndMarketPlace(String code, String marketPlace);

}
