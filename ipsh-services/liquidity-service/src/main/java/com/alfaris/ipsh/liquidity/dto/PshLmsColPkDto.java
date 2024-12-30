package com.alfaris.ipsh.liquidity.dto;

import java.util.Date;

public class PshLmsColPkDto {
	
	private String bankidId;

	private String productId;


	private Date valueDate;


	private java.util.Date timeStamp;


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


	public Date getValueDate() {
		return valueDate;
	}


	public void setValueDate(Date valueDate) {
		this.valueDate = valueDate;
	}


	public java.util.Date getTimeStamp() {
		return timeStamp;
	}


	public void setTimeStamp(java.util.Date timeStamp) {
		this.timeStamp = timeStamp;
	}
	
	

}
