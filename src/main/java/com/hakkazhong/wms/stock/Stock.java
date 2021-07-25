package com.hakkazhong.wms.stock;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.Objects;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.micrometer.core.lang.Nullable;

@Table("wms_stocks")
public class Stock {

	@Id
	private Long id;

	private String code;

	private String name;

	private String marketPlace;

	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate addedDate;

	private String currency;

	/**
	 * For the lump sum like robo advisor, this will always be same as the
	 * invested amount
	 * <p>
	 * For those having unit number, you dont need this, but must provide market
	 * unit price, need it to calculate
	 */
	private BigDecimal marketValue;

	/**
	 * For the lump sum case, this will be added to the market value for the
	 * existing bought one
	 */
	private BigDecimal investedAmount;

	@Nullable
	private Integer unitNumber;

	@Nullable
	private BigDecimal marketUnitPrice;

	@Nullable
	private BigDecimal investedUnitPrice;

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

	/**
	 * To be used over existing stocks so to calculate the invested amount,
	 * invested unit price, market value and market unit price again.
	 * @param topupStock stock bought to top up to existing bought one
	 * @return existing stock with updated amount and price
	 */
	public Stock recalculate(Stock topupStock) {
		// preventive measurement
		if (!Objects.equals(this.code, topupStock.code)
				&& !Objects.equals(this.marketPlace, topupStock.marketPlace)) {
			throw new IllegalArgumentException(
					"both stocks not matched, existing: " + this + "new: " + topupStock);
		}
		
		// +-*/ on BigDecimal should always assign to existing variable. 
		
		// invested amount to be added
		this.investedAmount = this.investedAmount.add(topupStock.investedAmount);

		// unit number to be added if there is any
		if (this.unitNumber != null) {
			this.unitNumber += topupStock.unitNumber;

			// invested unit price to recalculated based on: new invested amount
			// / new unit number
			this.investedUnitPrice = this.investedAmount.divide(new BigDecimal(this.unitNumber), 3, RoundingMode.HALF_UP);

			// market unit price should always override it
			this.marketUnitPrice = topupStock.marketUnitPrice;

			// last, market value
			doCalculateMarketValue();

		}
		else {
			// no unit number, just lump sum amount, to direct add the invested amount (if market value is empty)
			if (this.marketValue == null) {
				this.marketValue = topupStock.marketValue == null ? 
						topupStock.investedAmount : topupStock.marketValue;
			}
			else {
				this.marketValue = this.marketValue.add(topupStock.getInvestedAmount());
			}
		}
		return this;
	}

	public Stock calculateMarketValueIfEmpty() {
		if (this.marketValue == null) {
			doCalculateMarketValue();
		}
		return this;
	}

	private void doCalculateMarketValue() {
		this.marketValue = this.marketUnitPrice.multiply(new BigDecimal(this.unitNumber));
	}

	@Override
	public String toString() {
		return "Stock [code=" + code + ", name=" + name + ", marketPlace=" + marketPlace + ", addedDate=" + addedDate
				+ ", currency=" + currency + ", marketValue=" + marketValue + "]";
	}

}
