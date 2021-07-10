package com.hakkazhong.wms.stock.web;

import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.hakkazhong.wms.stock.Dividend;
import com.hakkazhong.wms.stock.DividendRepository;
import com.hakkazhong.wms.stock.MissingStockException;
import com.hakkazhong.wms.stock.StockRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class DividendController {
	
	private StockRepository stockRepository;

	private DividendRepository dividendRepository;

	public DividendController(StockRepository stockRepository, DividendRepository dividendRepository) {
		this.stockRepository = stockRepository;
		this.dividendRepository = dividendRepository;
	}
	
	@GetMapping("/stocks/{stockId}/dividends")
	public Flux<Dividend> allDividends(@PathVariable Long stockId) {
		return this.dividendRepository.findByStockIdOrderByAddedDateDesc(stockId);
	}

	@GetMapping("/dividends")
	public Flux<Dividend> allDividends() {
		return dividendRepository.findAll(Sort.by("addedDate").descending());
	}

	@PostMapping("/stocks/{stockId}/dividends")
	public Mono<Dividend> addDividend(@PathVariable Long stockId, @RequestBody Mono<Dividend> dividend) {
		return dividend.flatMap(div -> {
			div.setStockId(stockId);
			div.forTodayIfEmpty();
			
			return this.stockRepository.existsById(div.getStockId())
					.flatMap(exists -> {
						if (exists) {
							return this.dividendRepository.save(div);
						}
						throw new MissingStockException(div);
					});
		});
	}

}
