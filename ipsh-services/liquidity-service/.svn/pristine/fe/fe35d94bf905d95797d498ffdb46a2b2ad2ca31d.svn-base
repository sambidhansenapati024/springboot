package com.alfaris.ipsh.liquidity.entity;

import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Embeddable
public class PshCurColPK implements Serializable{
	
	@Column(name = "bankid_id")
	String bankId;
	@Column(name = "product_id")
	String productId;
	@Column(name = "value_date")
	@Temporal(TemporalType.DATE)
	Date valueDate;
	@Column(name = "time_stamp")
	@Temporal(TemporalType.TIMESTAMP)
	Date timeStamp;
	public String getBankId() {
		return bankId;
	}
	public void setBankId(String bankId) {
		this.bankId = bankId;
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
	public Date getTimeStamp() {
		return timeStamp;
	}
	public void setTimeStamp(Date timeStamp) {
		this.timeStamp = timeStamp;
	}
	
	

}
