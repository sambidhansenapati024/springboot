package com.alfaris.ipsh.liquidity.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;


/**
 * The persistent class for the psh_lms_col database table.
 * 
 */
@Entity
@Table(name="psh_lms_col")
@NamedQuery(name="PshLmsCol.findAll", query="SELECT p FROM PshLmsCol p")
public class PshLmsCol implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private PshLmsColPK id;

	@Column(name="col_pldg_sama")
	private BigDecimal colPldgSama;

	@Column(name="col_plds_anci")
	private BigDecimal colPldsAnci;

	@Column(name="committed_crline")
	private BigDecimal committedCrline;

	private BigDecimal others;

	@Column(name="sama_reserve")
	private BigDecimal samaReserve;

	@Column(name="secured_crline")
	private BigDecimal securedCrline;

	private String status;

	@Column(name="un_liquid_assets")
	private BigDecimal unLiquidAssets;
	
	@Column(name="modified_by")
	private String modifiedBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="modified_time")
	private Date modifiedTime;
	
	@Column(name="total")
	private BigDecimal total;
	
	@Column(name="totalCrline")
	private BigDecimal totalCrline;

	@Column(name="bal_with_othr_bnks")
	private BigDecimal balWithOthrBnks;
	
	
	
	public BigDecimal getBalWithOthrBnks() {
		return balWithOthrBnks;
	}

	public void setBalWithOthrBnks(BigDecimal balWithOthrBnks) {
		this.balWithOthrBnks = balWithOthrBnks;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	public BigDecimal getTotalCrline() {
		return totalCrline;
	}

	public void setTotalCrline(BigDecimal totalCrline) {
		this.totalCrline = totalCrline;
	}

	public PshLmsCol() {
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Date getModifiedTime() {
		return modifiedTime;
	}

	public void setModifiedTime(Date modifiedTime) {
		this.modifiedTime = modifiedTime;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public PshLmsColPK getId() {
		return this.id;
	}

	public void setId(PshLmsColPK id) {
		this.id = id;
	}

	public BigDecimal getColPldgSama() {
		return this.colPldgSama;
	}

	public void setColPldgSama(BigDecimal colPldgSama) {
		this.colPldgSama = colPldgSama;
	}

	public BigDecimal getColPldsAnci() {
		return this.colPldsAnci;
	}

	public void setColPldsAnci(BigDecimal colPldsAnci) {
		this.colPldsAnci = colPldsAnci;
	}

	public BigDecimal getCommittedCrline() {
		return this.committedCrline;
	}

	public void setCommittedCrline(BigDecimal committedCrline) {
		this.committedCrline = committedCrline;
	}

	public BigDecimal getOthers() {
		return this.others;
	}

	public void setOthers(BigDecimal others) {
		this.others = others;
	}

	public BigDecimal getSamaReserve() {
		return this.samaReserve;
	}

	public void setSamaReserve(BigDecimal samaReserve) {
		this.samaReserve = samaReserve;
	}

	public BigDecimal getSecuredCrline() {
		return this.securedCrline;
	}

	public void setSecuredCrline(BigDecimal securedCrline) {
		this.securedCrline = securedCrline;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public BigDecimal getUnLiquidAssets() {
		return this.unLiquidAssets;
	}

	public void setUnLiquidAssets(BigDecimal unLiquidAssets) {
		this.unLiquidAssets = unLiquidAssets;
	}

}