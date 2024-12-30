package com.alfaris.ipsh.authservice.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;



/**
 * The persistent class for the PSH_COL_CNF database table.
 * 
 */
@Entity
@Table(name="PSH_COL_CNF", schema = "IPSH")
//@NamedQuery(name="PshColCnf.findAll", query="SELECT p FROM PshColCnf p")
public class PshColCnf implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private PshColCnfPK id;

	@Column(name="COLUMN_ORDER")
	private String columnOrder;

	@Column(name="COLUMN_VISIBILITY")
	private String columnVisibility;

	public PshColCnf() {
	}

	public PshColCnfPK getId() {
		return this.id;
	}

	public void setId(PshColCnfPK id) {
		this.id = id;
	}

	public String getColumnOrder() {
		return this.columnOrder;
	}

	public void setColumnOrder(String columnOrder) {
		this.columnOrder = columnOrder;
	}

	public String getColumnVisibility() {
		return this.columnVisibility;
	}

	public void setColumnVisibility(String columnVisibility) {
		this.columnVisibility = columnVisibility;
	}

}