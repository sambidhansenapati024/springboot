package com.alfaris.ipsh.liquidity.entity;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name = "EFT_SIN_DATA",schema = "IPSH")
public class EftSinData {
	@EmbeddedId
	private EftSinDataPK id;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DATE")
	private Date date;
	
	@Column(name = "DATA")
	private String data;
	
	@Column(name = "STATUS")
	private String status;

	public EftSinDataPK getId() {
		return id;
	}

	public void setId(EftSinDataPK id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "{ \"id\" : \"" + id + "\",  \"date\" : \"" + date + "\",  \"data\" : \"" + data + "\",  \"status\" : \""
				+ status + "}";
	}

}
