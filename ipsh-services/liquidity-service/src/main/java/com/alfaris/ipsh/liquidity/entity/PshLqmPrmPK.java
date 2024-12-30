package com.alfaris.ipsh.liquidity.entity;

import java.io.Serializable;

import com.alfaris.ipsh.liquidity.entity.validator.CustomSize;
import com.alfaris.ipsh.liquidity.util.Constants;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;



@Embeddable
public class PshLqmPrmPK implements Serializable { 
	
	private static final long serialVersionUID = 1L;
	
	
	@Column(name = "our_bank_id")
	private String bankId;
	
	@NotEmpty(message="{NotEmpty.pshLqmPrm.id.productType}")
	@Column(name = "product_type")
	private String productType;
	
	
	@NotEmpty(message="{NotEmpty.pshLqmPrm.id.currencyCode}")
	@Pattern(regexp=Constants.PATTERN.ALPHABETSONLY,message="{Pattern.pshLqmPrm.id.currencyCode}")
	@CustomSize(minKey="pshLqmPrm.id.currencyCode.min",maxKey="pshLqmPrm.id.currencyCode.max",message= "{CustomSize.pshLqmPrm.id.currencyCode}")
	@Column(name = "currency_code")
	private String currencyCode;

	public String getBankId() {
		return bankId;
	}

	public void setBankId(String bankId) {
		this.bankId = bankId;
	}

	

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	@Override
	public String toString() {
		return "PshLqmPrmPK [bankId=" + bankId + ", serviceType=" + productType + ", currencyCode=" + currencyCode
				+ "]";
	}

}
