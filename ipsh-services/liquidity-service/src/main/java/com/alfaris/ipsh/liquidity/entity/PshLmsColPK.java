package com.alfaris.ipsh.liquidity.entity;

import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

/**
 * The primary key class for the psh_lms_col database table.
 * 
 */
@Embeddable
public class PshLmsColPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="bankid_id")
	private String bankidId;

	@Column(name="product_id")
	private String productId;

	@Column(name = "value_date")
	@Temporal(TemporalType.DATE)
	private Date valueDate;
	
	@Column(name="time_stamp")
    @Temporal(TemporalType.TIMESTAMP)
	private Date timeStamp;

	public PshLmsColPK() {
	}
	
	
	public Date getValueDate() {
		return valueDate;
	}


	public void setValueDate(Date valueDate) {
		this.valueDate = valueDate;
	}


	public String getBankidId() {
		return this.bankidId;
	}
	public void setBankidId(String bankidId) {
		this.bankidId = bankidId;
	}
	public String getProductId() {
		return this.productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	
	
	public Date getTimeStamp() {
		return this.timeStamp;
	}
	public void setTimeStamp(Date timeStamp) {
		this.timeStamp = timeStamp;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof PshLmsColPK)) {
			return false;
		}
		PshLmsColPK castOther = (PshLmsColPK)other;
		return 
			this.bankidId.equals(castOther.bankidId)
			&& this.productId.equals(castOther.productId)
			&& this.valueDate.equals(castOther.valueDate)
			&& this.timeStamp.equals(castOther.timeStamp);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.bankidId.hashCode();
		hash = hash * prime + this.productId.hashCode();
		hash = hash * prime + this.valueDate.hashCode();
		hash = hash * prime + this.timeStamp.hashCode();
		
		return hash;
	}
	
	public PshLmsColPK(String bankidId, String productId, Date valueDate, Date timeStamp) {
		super();
		this.bankidId = bankidId;
		this.productId = productId;
		this.valueDate = valueDate;
		this.timeStamp = timeStamp;
	}
	
	
}