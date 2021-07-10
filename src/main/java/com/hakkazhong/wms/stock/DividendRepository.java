package com.hakkazhong.wms.stock;

import org.springframework.data.r2dbc.repository.R2dbcRepository;

import reactor.core.publisher.Flux;

public interface DividendRepository extends R2dbcRepository<Dividend, Long> {

	Flux<Dividend> findByStockIdOrderByAddedDateDesc(Long stockId);

}
