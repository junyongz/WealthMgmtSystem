package com.hakkazhong.wms.stock;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

@Table("wms_dividend")
public class Dividend {

	@Id
	private Long id;
	
	private Long stockId;
	
	private String currency;
	
	private BigDecimal amount;
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate addedDate;
	
	private Dividend(Long stockId, String currency, BigDecimal amount, LocalDate addedDate) {
		this.stockId = stockId;
		this.currency = currency;
		this.amount = amount;
		this.addedDate = addedDate;
	}
	
	public static Dividend today(Long stockId, String currency, BigDecimal amount) {
		return new Dividend(stockId, currency, amount, LocalDate.now());
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getStockId() {
		return stockId;
	}

	public void setStockId(Long stockId) {
		this.stockId = stockId;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public LocalDate getAddedDate() {
		return addedDate;
	}

	public void setAddedDate(LocalDate addedDate) {
		this.addedDate = addedDate;
	}
	
	public void forTodayIfEmpty() {
		if (this.addedDate == null) {
			this.addedDate = LocalDate.now();
		}
	}
	
}
