package com.alfaris.ipsh.liquidity.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.hibernate.validator.constraints.Range;

import com.alfaris.ipsh.liquidity.entity.validator.CustomSize;
import com.alfaris.ipsh.liquidity.util.Constants;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;





@Entity
@Table(name="psh_lqm_prm")
public class PshLqmPrm implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	
	@EmbeddedId
	@Valid
	private PshLqmPrmPK id;	
	

	@NotNull(message="{NotNull.pshLqmPrm.prefundLimit}")
	@Digits(integer=11,fraction =4 ,message="{Digits.pshLqmPrm.prefundLimit}")
	@Column(name = "prefund_limit")
	private BigDecimal prefundLimit;
	
	
	@NotNull(message="{NotNull.pshLqmPrm.openingLimit}")
	@Digits(integer=11,fraction =4 ,message="{Digits.pshLqmPrm.openingLimit}")
	@Column(name = "opening_limit")
	private BigDecimal openingLimit;
	
	
	@CustomSize(minKey="pshLqmPrm.limitAccount.min",maxKey="pshLqmPrm.limitAccount.max",message="{CustomSize.pshLqmPrm.limitAccount}")
	@Pattern(regexp =Constants.PATTERN.ALPHANUMERICONLY,message = "{Pattern.pshLqmPrm.limitAccount}")
	@Column(name = "limit_acc")
	private String limitAccount;
	
	
	@CustomSize(minKey="pshLqmPrm.nostroAccount.min",maxKey="pshLqmPrm.nostroAccount.max",message = "{CustomSize.pshLqmPrm.nostroAccount}")
	@Pattern(regexp =Constants.PATTERN.ALPHANUMERICONLY,message = "{Pattern.pshLqmPrm.nostroAccount}")
	@Column(name = "nostro_acc")
	private String nostroAccount;	
	
	@CustomSize(minKey="pshLqmPrm.vostroAccount.min",maxKey="pshLqmPrm.vostroAccount.max",message = "{CustomSize.pshLqmPrm.vostroAccount}")
	@Pattern(regexp =Constants.PATTERN.ALPHANUMERICONLY,message = "{Pattern.pshLqmPrm.vostroAccount}")
	@Column(name = "vostro_acc")
	private String vostroAccount;	
	
	
	@NotNull(message="{NotNull.pshLqmPrm.alertPercentage}")
	@Range(min=0,max=100 , message="{Range.pshLqmPrm.alertPercentage}")
	@Column(name = "alert_percentage")
	private Integer alertPercentage;	
	
	
	@NotNull(message="{NotNull.pshLqmPrm.alertPercentagePayHold}")
	@Range(min=0,max=100 , message="{Range.pshLqmPrm.alertPercentagePayHold}")
	@Column(name = "alert_percentage_pay_hold")
	private Integer alertPercentagePayHold;	
	
	@Column(name = "pay_hold_status")
	private String payHoldStatus;	
	
	@NotEmpty(message="{NotEmpty.pshLqmPrm.alertEmail}")
	@Pattern(regexp =Constants.PATTERN.EMAIL,message = "{Pattern.pshLqmPrm.alertEmail}")
	@Column(name = "alert_email")
	private String alertEmail;	
	
	
	@CustomSize(minKey="pshLqmPrm.alertSms.min",maxKey="pshLqmPrm.alertSms.max",message="{CustomSize.pshLqmPrm.alertSms}")
	@Column(name = "alert_sms")
	private String alertSms;	
	

	@Column(name = "status")
	private String status;	
	
	
	@Column(name="pro_id")
	private String createdBy;
	

	@Column(name="ver_id")
	private String verifiedBy;
	
	
    @Column(name="modified_by")
	private String modifiedBy;
	
	
    @Column(name = "pro_time")
    private Date  createdTime;
    
    @Column(name = "ver_time")
    private Date  verifiedTime;
    
    @Column(name = "modified_on")
    private Date  modifiedTime;

	public PshLqmPrmPK getId() {
		return id;
	}

	public void setId(PshLqmPrmPK id) {
		this.id = id;
	}

	public BigDecimal getPrefundLimit() {
		return prefundLimit;
	}

	public void setPrefundLimit(BigDecimal prefundLimit) {
		this.prefundLimit = prefundLimit;
	}

	public BigDecimal getOpeningLimit() {
		return openingLimit;
	}

	public void setOpeningLimit(BigDecimal openingLimit) {
		this.openingLimit = openingLimit;
	}

	public String getLimitAccount() {
		return limitAccount;
	}

	public void setLimitAccount(String limitAccount) {
		this.limitAccount = limitAccount;
	}

	public String getNostroAccount() {
		return nostroAccount;
	}

	public void setNostroAccount(String nostroAccount) {
		this.nostroAccount = nostroAccount;
	}

	public String getVostroAccount() {
		return vostroAccount;
	}

	public void setVostroAccount(String vostroAccount) {
		this.vostroAccount = vostroAccount;
	}

	public Integer getAlertPercentage() {
		return alertPercentage;
	}

	public void setAlertPercentage(Integer alertPercentage) {
		this.alertPercentage = alertPercentage;
	}

	public Integer getAlertPercentagePayHold() {
		return alertPercentagePayHold;
	}

	public void setAlertPercentagePayHold(Integer alertPercentagePayHold) {
		this.alertPercentagePayHold = alertPercentagePayHold;
	}

	public String getPayHoldStatus() {
		return payHoldStatus;
	}

	public void setPayHoldStatus(String payHoldStatus) {
		this.payHoldStatus = payHoldStatus;
	}

	public String getAlertEmail() {
		return alertEmail;
	}

	public void setAlertEmail(String alertEmail) { 
		this.alertEmail = alertEmail;
	}

	public String getAlertSms() {
		return alertSms;
	}

	public void setAlertSms(String alertSms) {
		this.alertSms = alertSms;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getVerifiedBy() {
		return verifiedBy;
	}

	public void setVerifiedBy(String verifiedBy) {
		this.verifiedBy = verifiedBy;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	public Date getVerifiedTime() {
		return verifiedTime;
	}

	public void setVerifiedTime(Date verifiedTime) {
		this.verifiedTime = verifiedTime;
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

	

	
	
}
