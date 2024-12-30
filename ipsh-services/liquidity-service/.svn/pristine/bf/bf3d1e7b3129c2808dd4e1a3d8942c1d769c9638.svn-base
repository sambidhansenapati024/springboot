package com.alfaris.ipsh.liquidity.entity;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name="PSH_CUR_COL", schema="IPSH")
public class PshCurCol {
	@EmbeddedId
	PshCurColPK id;
	@Column(name = "CUM_SENT")
	BigDecimal cumSent;
	@Column(name = "PERC_SENT")
	BigDecimal percSent;
	@Column(name = "PERC_RECV")
	BigDecimal percRecv;
	@Column(name = "CUM_RECV")
	BigDecimal cumRecv;
	@Column(name = "gross_payments_sent")
	BigDecimal grossPaySent;
	@Column(name = "gross_payments_received")
	BigDecimal grossPayRecv;
	
	
	public BigDecimal getGrossPaySent() {
		return grossPaySent;
	}
	public void setGrossPaySent(BigDecimal grossPaySent) {
		this.grossPaySent = grossPaySent;
	}
	public BigDecimal getGrossPayRecv() {
		return grossPayRecv;
	}
	public void setGrossPayRecv(BigDecimal grossPayRecv) {
		this.grossPayRecv = grossPayRecv;
	}
	public BigDecimal getPercRecv() {
		return percRecv;
	}
	public void setPercRecv(BigDecimal percRecv) {
		this.percRecv = percRecv;
	}
	public BigDecimal getCumSent() {
		return cumSent;
	}
	public void setCumSent(BigDecimal cumSent) {
		this.cumSent = cumSent;
	}
	public BigDecimal getPercSent() {
		return percSent;
	}
	public void setPercSent(BigDecimal percSent) {
		this.percSent = percSent;
	}
	public BigDecimal getCumRecv() {
		return cumRecv;
	}
	public void setCumRecv(BigDecimal cumRecv) {
		this.cumRecv = cumRecv;
	}
	public PshCurColPK getId() {
		return id;
	}
	public void setId(PshCurColPK id) {
		this.id = id;
	}
	
	

}
