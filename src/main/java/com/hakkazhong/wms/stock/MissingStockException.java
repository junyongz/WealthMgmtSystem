package com.hakkazhong.wms.stock;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_ACCEPTABLE, reason = "No valid stock provided for this dividend")
public class MissingStockException extends RuntimeException {

	private static final long serialVersionUID = 6772467919952453116L;
	
	private Dividend dividend;
	
	public MissingStockException(Dividend dividend) {
		this.dividend = dividend;
	}
	
	public Dividend getDividend() {
		return this.dividend;
	}
	
	public boolean isEmptyStockId() {
		return this.dividend.getStockId() == null;
	}
	
	public String getMessage() {
		return (isEmptyStockId()) ? "No stock provided"
				: String.format("Stock (id: %s) is not created before.", this.dividend.getStockId());
	}

}
