package com.alfaris.ipsh.liquidity.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Table(name="PSH_LMS_DTR")
@Entity
public class PshLmsDtr implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7493757486027371732L;

	@Id
	@Column(name="UTI")
	private String uti;
	
	@Column(name="bankid_id")
	private String bankId;
	
	@Column(name="product_id")
	private String productId;
	
	@Column(name="service_id")
	private String serviceId;
	
	@Column(name="value_date")
	@Temporal(TemporalType.DATE)
	private Date valueDate;
	
	@Column(name="time_stamp")
	@Temporal(TemporalType.TIMESTAMP)
	private Date timeStamp;
	
	@Column(name="OG_TXN_AMT")
	private BigDecimal ogTxnAmt;
	
	@Column(name="INC_TXN_AMT")
	private BigDecimal incTxnAmt;
	
	@Column(name="NET_AMT")
	private BigDecimal netAmt;
	
	@Column(name="direction")
	private String direction;

	public String getUti() {
		return uti;
	}

	public void setUti(String uti) {
		this.uti = uti;
	}

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

	public String getServiceId() {
		return serviceId;
	}

	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
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

	public BigDecimal getOgTxnAmt() {
		return ogTxnAmt;
	}

	public void setOgTxnAmt(BigDecimal ogTxnAmt) {
		this.ogTxnAmt = ogTxnAmt;
	}

	public BigDecimal getIncTxnAmt() {
		return incTxnAmt;
	}

	public void setIncTxnAmt(BigDecimal incTxnAmt) {
		this.incTxnAmt = incTxnAmt;
	}

	public BigDecimal getNetAmt() {
		return netAmt;
	}

	public void setNetAmt(BigDecimal netAmt) {
		this.netAmt = netAmt;
	}

	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}
	
	
	
	

}
