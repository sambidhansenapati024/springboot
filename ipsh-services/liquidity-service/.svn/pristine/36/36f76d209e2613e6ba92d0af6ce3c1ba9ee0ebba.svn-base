package com.alfaris.ipsh.liquidity.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Table(name="PSH_RTGS_SDI")
@Entity
public class PshRtgsSdi implements Serializable{
	
	@Id
	@Column(name="psh_reference")
	private String pshReference;
	
	@Column(name = "value_date")
	@Temporal(TemporalType.DATE)
	private Date valueDate;
	
	@Column(name = "create_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createDate;

	@Column(name = "opening_balance")
	private BigDecimal openingBalance;
	

	@Column(name = "forward_position")
	private BigDecimal forwardPosition;
	
	@Column(name="raw_data")
	@Lob
    private byte[] rawData;
	
	@Column(name="transaction_reference")
	private String transactionReference;

	public String getPshReference() {
		return pshReference;
	}

	public void setPshReference(String pshReference) {
		this.pshReference = pshReference;
	}

	public Date getValueDate() {
		return valueDate;
	}

	public void setValueDate(Date valueDate) {
		this.valueDate = valueDate;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public BigDecimal getOpeningBalance() {
		return openingBalance;
	}

	public void setOpeningBalance(BigDecimal openingBalance) {
		this.openingBalance = openingBalance;
	}

	public BigDecimal getForwardPosition() {
		return forwardPosition;
	}

	public void setForwardPosition(BigDecimal forwardPosition) {
		this.forwardPosition = forwardPosition;
	}

	public byte[] getRawData() {
		return rawData;
	}

	public void setRawData(byte[] rawData) {
		this.rawData = rawData;
	}

	public String getTransactionReference() {
		return transactionReference;
	}

	public void setTransactionReference(String transactionReference) {
		this.transactionReference = transactionReference;
	}

	@Override
	public String toString() {
		return "PshRtgsSdi [pshReference=" + pshReference + ", valueDate=" + valueDate + ", createDate=" + createDate
				+ ", openingBalance=" + openingBalance + ", forwardPosition=" + forwardPosition + ", rawData="
				+ Arrays.toString(rawData) + ", transactionReference=" + transactionReference + "]";
	}
	
	
	
	
}
