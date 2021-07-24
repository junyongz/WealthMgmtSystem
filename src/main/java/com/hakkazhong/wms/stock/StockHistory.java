package com.hakkazhong.wms.stock;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.boot.context.properties.PropertyMapper;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import io.micrometer.core.lang.Nullable;

@Table("wms_stocks_history")
public class StockHistory {

	@Id
	private Long id;

	private String code;

	private String name;

	private String marketPlace;

	private LocalDate addedDate;

	private String currency;

	private BigDecimal marketValue;

	private BigDecimal investedAmount;

	@Nullable
	private Integer unitNumber;

	@Nullable
	private BigDecimal marketUnitPrice;

	@Nullable
	private BigDecimal investedUnitPrice;
	
	private String addedBy;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMarketPlace() {
		return marketPlace;
	}

	public void setMarketPlace(String marketPlace) {
		this.marketPlace = marketPlace;
	}

	public LocalDate getAddedDate() {
		return addedDate;
	}

	public void setAddedDate(LocalDate addedDate) {
		this.addedDate = addedDate;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public BigDecimal getMarketValue() {
		return marketValue;
	}

	public void setMarketValue(BigDecimal marketValue) {
		this.marketValue = marketValue;
	}

	public BigDecimal getInvestedAmount() {
		return investedAmount;
	}

	public void setInvestedAmount(BigDecimal investedAmount) {
		this.investedAmount = investedAmount;
	}

	public Integer getUnitNumber() {
		return unitNumber;
	}

	public void setUnitNumber(Integer unitNumber) {
		this.unitNumber = unitNumber;
	}

	public BigDecimal getMarketUnitPrice() {
		return marketUnitPrice;
	}

	public void setMarketUnitPrice(BigDecimal marketUnitPrice) {
		this.marketUnitPrice = marketUnitPrice;
	}

	public BigDecimal getInvestedUnitPrice() {
		return investedUnitPrice;
	}

	public void setInvestedUnitPrice(BigDecimal investedUnitPrice) {
		this.investedUnitPrice = investedUnitPrice;
	}
	
	public String getAddedBy() {
		return addedBy;
	}

	public void setAddedBy(String addedBy) {
		this.addedBy = addedBy;
	}
	
	public static StockHistory fromStock(Stock stock) {
		StockHistory hist = new StockHistory();

		PropertyMapper mapper = PropertyMapper.get();
		mapper.from(stock::getCode).to(hist::setCode);
		mapper.from(stock::getName).to(hist::setName);
		mapper.from(stock::getCurrency).to(hist::setCurrency);
		mapper.from(stock::getInvestedAmount).to(hist::setInvestedAmount);
		mapper.from(stock::getInvestedUnitPrice).to(hist::setInvestedUnitPrice);
		mapper.from(stock::getMarketPlace).to(hist::setMarketPlace);
		mapper.from(stock::getMarketValue).to(hist::setMarketValue);
		mapper.from(stock::getMarketUnitPrice).to(hist::setMarketUnitPrice);
		mapper.from(stock::getUnitNumber).to(hist::setUnitNumber);
		mapper.from(stock::getAddedDate).to(hist::setAddedDate);
		
		return hist;
	}

	@Override
	public String toString() {
		return "Stock [code=" + code + ", name=" + name + ", marketPlace=" + marketPlace + ", addedDate=" + addedDate
				+ ", currency=" + currency + ", marketValue=" + marketValue + "]";
	}

}
