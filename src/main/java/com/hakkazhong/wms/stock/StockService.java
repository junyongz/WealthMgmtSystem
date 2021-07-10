package com.hakkazhong.wms.stock;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import reactor.core.publisher.Mono;

@Transactional
@Service
public class StockService {

	private StockRepository stockRepository;

	private StockHistoryRepository stockHistoryRepository;

	public StockService(StockRepository stockRepository, StockHistoryRepository stockHistoryRepository) {
		this.stockRepository = stockRepository;
		this.stockHistoryRepository = stockHistoryRepository;
	}

	public Mono<Stock> buyStock(Mono<Stock> newStock) {
		return newStock.flatMap(stock -> {

			return this.stockRepository.findByCodeAndMarketPlace(stock.getCode(), stock.getMarketPlace()) // find any existing
					.flatMap(existing -> {
						StockHistory hist = StockHistory.fromStock(stock.calculateMarketValueIfEmpty());

						return stockHistoryRepository.save(hist).flatMap(newHist ->
							stockRepository.save(existing.recalculate(stock)));
					})
					.switchIfEmpty(Mono.just(stock))
					.flatMap(newlyBoughtStock -> {
						if (newlyBoughtStock.getId() == null) { // continue to save it if is really brand new
							newlyBoughtStock.calculateMarketValueIfEmpty();
							
							StockHistory hist = StockHistory.fromStock(newlyBoughtStock);
			
							return stockHistoryRepository.save(hist).flatMap(newHist -> 
								stockRepository.save(newlyBoughtStock));
						}
						else {
							return Mono.just(newlyBoughtStock);
						}
					});
		});
	}
	
}
