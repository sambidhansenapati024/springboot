package com.alfaris.ipsh.liquidity.dto;

import java.util.Date;

public class PshLmsColPkVerifyDto {
	
	private String bankidId;

	private String productId;


	private String valueDate;


	private String timeStamp;


	public String getBankidId() {
		return bankidId;
	}


	public void setBankidId(String bankidId) {
		this.bankidId = bankidId;
	}


	public String getProductId() {
		return productId;
	}


	public void setProductId(String productId) {
		this.productId = productId;
	}


	public String getValueDate() {
		return valueDate;
	}


	public void setValueDate(String valueDate) {
		this.valueDate = valueDate;
	}


	public String getTimeStamp() {
		return timeStamp;
	}


	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}
	
	

}
