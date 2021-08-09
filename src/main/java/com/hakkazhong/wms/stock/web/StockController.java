package com.hakkazhong.wms.stock.web;

import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hakkazhong.wms.stock.Stock;
import com.hakkazhong.wms.stock.StockHistory;
import com.hakkazhong.wms.stock.StockHistoryRepository;
import com.hakkazhong.wms.stock.StockRepository;
import com.hakkazhong.wms.stock.StockService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/stocks")
public class StockController {

	private StockRepository stockRepository;

	private StockHistoryRepository stockHistoryRepository;

	private StockService stockService;

	public StockController(StockRepository stockRepository, StockHistoryRepository stockHistoryRepository,
			StockService stockService) {
		this.stockRepository = stockRepository;
		this.stockHistoryRepository = stockHistoryRepository;
		this.stockService = stockService;
	}

	@PostMapping
	public Mono<Stock> buyStock(@RequestBody Mono<Stock> newStock) {
		return stockService.buyStock(newStock);
	}

	@GetMapping
	public Flux<Stock> allStocks() {
		return stockRepository.findAll(Sort.by("addedDate").descending());
	}

	@GetMapping("/{id}")
	public Mono<Stock> getStockById(@PathVariable("id") Long id) {
		return stockRepository.findById(id);
	}

	@GetMapping("/{id}/histories")
	public Flux<StockHistory> getStockHistoriesById(@PathVariable("id") Long id) {
		return stockRepository.findById(id).flatMapMany(
				stock -> stockHistoryRepository.findByMarketPlaceAndCode(stock.getMarketPlace(), stock.getCode()));
	}

	@GetMapping(params = "search=by-code")
	public Mono<Stock> findStockByCodeAndMarketPlace(@RequestParam("code") String code,
			@RequestParam("marketPlace") String marketPlace) {
		return stockRepository.findByCodeAndMarketPlace(code, marketPlace);
	}

}
