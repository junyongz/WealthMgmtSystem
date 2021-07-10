package com.hakkazhong.wms.stock;

import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

import reactor.core.publisher.Flux;

@Repository
public interface StockHistoryRepository extends R2dbcRepository<StockHistory, Long> {

	Flux<StockHistory> findByMarketPlaceAndCode(String marketPlace, String code);
	
}
