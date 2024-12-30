package com.alfaris.ipsh.liquidity.entity;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name="psh_sam_liq_rep")
public class PshSamLiqRep {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="report_id")
	private int reportId;
	
	@Column(name="file_name")
	private String fileName;
	
	@Column(name="file_content")
	@Lob
    private byte[] fileContent;
	
	@Column(name="report_generated_date")
	@Temporal(TemporalType.DATE)
	private Date reportGeneratedDate;
	
	@Column(name="report_type")
	private String reportType;

	public int getReportId() {
		return reportId;
	}

	public void setReportId(int reportId) {
		this.reportId = reportId;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public byte[] getFileContent() {
		return fileContent;
	}

	public void setFileContent(byte[] fileContent) {
		this.fileContent = fileContent;
	}

	public Date getReportGeneratedDate() {
		return reportGeneratedDate;
	}

	public void setReportGeneratedDate(Date reportGeneratedDate) {
		this.reportGeneratedDate = reportGeneratedDate;
	}

	public String getReportType() {
		return reportType;
	}

	public void setReportType(String reportType) {
		this.reportType = reportType;
	}

	public PshSamLiqRep(int reportId, String fileName, byte[] fileContent, Date reportGeneratedDate,
			String reportType) {
		super();
		this.reportId = reportId;
		this.fileName = fileName;
		this.fileContent = fileContent;
		this.reportGeneratedDate = reportGeneratedDate;
		this.reportType = reportType;
	}

	public PshSamLiqRep() {
		super();
		// TODO Auto-generated constructor stub
	}

}
