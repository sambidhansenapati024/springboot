package com.alfaris.ipsh.liquidity.entity;

import java.io.Serializable;

import jakarta.persistence.Column;

public class PshItrDetPK implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Column(name="bankid_id")
	private String bankId;

	@Column(name="channel_id")
	private String channelId;
	
	@Column(name="psh_reference")
	private String pshReference;

	public String getBankId() {
		return bankId;
	}

	public void setBankId(String bankId) {
		this.bankId = bankId;
	}

	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	public String getPshReference() {
		return pshReference;
	}

	public void setPshReference(String pshReference) {
		this.pshReference = pshReference;
	}

	public PshItrDetPK(String bankId, String channelId, String pshReference) {
		super();
		this.bankId = bankId;
		this.channelId = channelId;
		this.pshReference = pshReference;
	}

	public PshItrDetPK() {
		super();
	}

}
