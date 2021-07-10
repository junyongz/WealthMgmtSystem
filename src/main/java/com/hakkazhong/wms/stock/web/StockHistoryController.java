package com.hakkazhong.wms.stock.web;

import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hakkazhong.wms.stock.StockHistory;
import com.hakkazhong.wms.stock.StockHistoryRepository;

import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/stock-histories")
public class StockHistoryController {

	private StockHistoryRepository stockHistoryRepository;
	
	public StockHistoryController(StockHistoryRepository stockHistoryRepository) {
		this.stockHistoryRepository = stockHistoryRepository;
	}
	
	@GetMapping("/{marketPlace}/{code}")
	public Flux<StockHistory> findByMarketPlaceAndCode(@PathVariable String marketPlace, @PathVariable String code) {
		return this.stockHistoryRepository.findByMarketPlaceAndCode(marketPlace, code);
	}
	
	@GetMapping
	public Flux<StockHistory> allHistories() {
		return this.stockHistoryRepository.findAll(Sort.by("addedDate").descending());
	}
	
}
