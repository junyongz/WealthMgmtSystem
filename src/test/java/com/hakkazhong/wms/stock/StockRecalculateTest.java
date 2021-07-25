package com.hakkazhong.wms.stock;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

class StockRecalculateTest {

	@Test
	void test() {
		Stock stock = new Stock();
		stock.setCode("Stock888");
		stock.setName("Stock 888");
		stock.setInvestedAmount(new BigDecimal(18000));
		stock.setUnitNumber(5000);
		stock.setInvestedUnitPrice(new BigDecimal(25));
		stock.setMarketValue(new BigDecimal(20000));
		stock.setMarketUnitPrice(new BigDecimal(13.5));
		
		Stock topupStock = new Stock();
		topupStock.setCode("Stock888");
		topupStock.setName("Stock 888");
		topupStock.setInvestedAmount(new BigDecimal(4000));
		topupStock.setUnitNumber(1000);
		topupStock.setInvestedUnitPrice(new BigDecimal(4));
		topupStock.setMarketValue(new BigDecimal(4000));
		topupStock.setMarketUnitPrice(new BigDecimal(4));
		
		stock.recalculate(topupStock);
		assertThat(stock.getInvestedAmount(), is(new BigDecimal(22000)));
		assertThat(stock.getInvestedUnitPrice(), is(new BigDecimal("3.667")));
	}

}
