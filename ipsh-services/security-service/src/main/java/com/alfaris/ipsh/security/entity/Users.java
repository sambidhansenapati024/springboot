/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.alfaris.ipsh.security.entity;

import java.util.Arrays;
import java.util.Date;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

import com.alfaris.ipsh.security.entity.validation.CustomSize;

/**
 *
 * @author Interland
 */
@Entity
@Table(name = "EFTSECUSR", schema = "IPSH")
public class Users {

	@Id
	@NotBlank(message = "{NotBlank.users.userId}")
	@CustomSize(minKey = "users.userId.min", maxKey = "users.userId.max", message = "{CustomSize.users.userId}")
	@Pattern(regexp = "^[a-zA-Z0-9@\\.\\-\\_ ]+$", message = "{Pattern.users.userId}")
	@Column(name = "user_id")
	private String userId;
	
	@NotBlank(message = "{NotBlank.users.userName}")
	@CustomSize(minKey = "users.userName.min", maxKey = "users.userName.max", message = "{CustomSize.users.userName}")
	@Pattern(regexp = "^[a-zA-Z0-9@\\.\\-\\_ ]+$", message = "{Pattern.users.userName}")
	@Column(name = "user_name")
	private String userName;

	@Column(name = "password")
	private String password;
	@Column(name = "level0")
	private Integer level0;
	// @Column(name = "group_id")
	// private String groupID;

   
	@ManyToOne(cascade = CascadeType.DETACH)
	@JoinColumn(name = "group_id", referencedColumnName = "group_id", nullable = false)
	private Group group;

	@Column(name = "orgdep")
	@NotEmpty(message = "{NotEmpty.users.orgDept}")
	private String orgDept;

	@CustomSize(minKey = "users.emailId.min", maxKey = "users.emailId.max", message = "{CustomSize.users.emailId}")
	@Pattern(regexp = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$", message = "{Pattern.users.emailId}")
	@Column(name = "email_id")
	private String emailId;

//	@CustomSize(minKey = "users.cellNo.min", maxKey = "users.cellNo.max", message = "{CustomSize.users.cellNo}")
//	@Pattern(regexp = "^[0-9+]+$", message = "{Pattern.users.cellNo}")
	@Column(name = "cell_no")
	private String cellNo;

	@Column(name = "sys_date")
	@Temporal(jakarta.persistence.TemporalType.DATE)
	private Date sysDate;
	
	@Column(name = "time_cre")
	@Temporal(jakarta.persistence.TemporalType.TIMESTAMP)
	private Date createdTime;
	
	@Column(name = "time_ver")
	@Temporal(jakarta.persistence.TemporalType.TIMESTAMP)
	private Date modifiedTime;
	
	@Column(name = "log_time")
	@Temporal(jakarta.persistence.TemporalType.DATE)
	private Date logTime;
	
	@Column(name = "num_atept")
	private Integer numAttempt;
	@Column(name = "acc_sts")
	private String accountStatus;
	@Column(name = "rnv_pwsd")
	private Integer renewPassword;
	@Column(name = "pwd_date")
	@Temporal(jakarta.persistence.TemporalType.DATE)
	private Date passwordDate;
	@Column(name = "alert_que")
	private Integer alertQueue;
	@Column(name = "alert_hvl")
	private Integer alertHvl;
	@Column(name = "alert_sys")
	private Integer alertSys;
	@Column(name = "alert_rej")
	private Integer alertRej;
	@Column(name = "usertype")
	private String userType;
	@Column(name = "thr_lmt")
	private Integer thrLimit;
	@Column(name = "msg_types")
	private String msgTypes;
	@Column(name = "usr_lmt")
	private Integer userLimit;
	@Column(name = "pro_id")
	private String createdBy;
	@Column(name = "ver_id")
	private String modifiedBy;
	@Column(name = "status")
	private String status;
	@Column(name = "lst_pwd1")
	private String lostPassword1;
	@Column(name = "lst_pwd2")
	private String lostPassword2;
	@Column(name = "lst_pwd3")
	private String lostPassword3;
	@Column(name = "lst_pwd4")
	private String lostPassword4;
	@Column(name = "lst_pwd5")
	private String lostPassword5;
	@Column(name = "lst_cnt")
	private Integer listCount;
	@Column(name = "usercat")
	private Integer userCategory;
	@Column(name = "phy_img")
	private String phyImage;
	@Column(name = "fget_pwd_q1")
	private String forgetPasswordQuestion1;
	@Column(name = "fget_pwd_a1")
	private String forgetPasswordAnswer1;
	@Column(name = "fget_pwd_q2")
	private String forgetPasswordQuestion2;
	@Column(name = "fget_pwd_a2")
	private String forgetPasswordAnswer2;
	@Column(name = "alot_ip1")
	private String alotIP2;
	@Column(name = "alot_ip2")
	private String alotIp1;
	@Column(name = "ben_org")
	private String benOrg;
	@Column(name = "ipflag")
	private Integer ipFlag;
	@Column(name = "ipflag1")
	private Integer ipFlag1;
	@Column(name = "loginstatus")
	private Integer loginStatus;
	@Column(name = "user_lng")
	private Integer userLng;
	@Column(name = "pwd_exp")
	private Integer passExpire;
	@Column(name = "LINK_CAS_HRY")
	@Temporal(jakarta.persistence.TemporalType.TIMESTAMP)
	private Date casDate;
	@Column(name = "PROFILE_PICTURE")
	private byte[] profilePicture;
	@Column(name = "ID_NUMBER")
	private String idNumber;
	@Column(name = "ID_EXPIRY_DATE")
	private Date expiryDate;
	@Column(name = "TOKENTIME")
	@Temporal(jakarta.persistence.TemporalType.TIMESTAMP)
	private Date tokenGenTime;
	@Column(name = "TOKEN")
	private String token;
	
	@Pattern(regexp = "^[a-zA-Z0-9 ]+$", message = "{Pattern.users.bankId}")
	@NotEmpty(message = "{NotEmpty.users.bankId}")
	@Column(name = "BANK_ID")
	private String bankId;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getLevel0() {
		return level0;
	}

	public void setLevel0(Integer level0) {
		this.level0 = level0;
	}

	public Group getGroup() {
		return group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}

	public String getOrgDept() {
		return orgDept;
	}

	public void setOrgDept(String orgDept) {
		this.orgDept = orgDept;
	}

	

	public String getCellNo() {
		return cellNo;
	}

	public void setCellNo(String cellNo) {
		this.cellNo = cellNo;
	}

	public Date getSysDate() {
		return sysDate;
	}

	public void setSysDate(Date sysDate) {
		this.sysDate = sysDate;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	public Date getModifiedTime() {
		return modifiedTime;
	}

	public void setModifiedTime(Date modifiedTime) {
		this.modifiedTime = modifiedTime;
	}

	public Date getLogTime() {
		return logTime;
	}

	public void setLogTime(Date logTime) {
		this.logTime = logTime;
	}

	public Integer getNumAttempt() {
		return numAttempt;
	}

	public void setNumAttempt(Integer numAttempt) {
		this.numAttempt = numAttempt;
	}

	public String getAccountStatus() {
		return accountStatus;
	}

	public void setAccountStatus(String accountStatus) {
		this.accountStatus = accountStatus;
	}

	public Integer getRenewPassword() {
		return renewPassword;
	}

	public void setRenewPassword(Integer renewPassword) {
		this.renewPassword = renewPassword;
	}

	public Date getPasswordDate() {
		return passwordDate;
	}

	public void setPasswordDate(Date passwordDate) {
		this.passwordDate = passwordDate;
	}

	public Integer getAlertQueue() {
		return alertQueue;
	}

	public void setAlertQueue(Integer alertQueue) {
		this.alertQueue = alertQueue;
	}

	public Integer getAlertHvl() {
		return alertHvl;
	}

	public void setAlertHvl(Integer alertHvl) {
		this.alertHvl = alertHvl;
	}

	public Integer getAlertSys() {
		return alertSys;
	}

	public void setAlertSys(Integer alertSys) {
		this.alertSys = alertSys;
	}

	public Integer getAlertRej() {
		return alertRej;
	}

	public void setAlertRej(Integer alertRej) {
		this.alertRej = alertRej;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public Integer getThrLimit() {
		return thrLimit;
	}

	public void setThrLimit(Integer thrLimit) {
		this.thrLimit = thrLimit;
	}

	public String getMsgTypes() {
		return msgTypes;
	}

	public void setMsgTypes(String msgTypes) {
		this.msgTypes = msgTypes;
	}

	public Integer getUserLimit() {
		return userLimit;
	}

	public void setUserLimit(Integer userLimit) {
		this.userLimit = userLimit;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getLostPassword1() {
		return lostPassword1;
	}

	public void setLostPassword1(String lostPassword1) {
		this.lostPassword1 = lostPassword1;
	}

	public String getLostPassword2() {
		return lostPassword2;
	}

	public void setLostPassword2(String lostPassword2) {
		this.lostPassword2 = lostPassword2;
	}

	public String getLostPassword3() {
		return lostPassword3;
	}

	public void setLostPassword3(String lostPassword3) {
		this.lostPassword3 = lostPassword3;
	}

	public String getLostPassword4() {
		return lostPassword4;
	}

	public void setLostPassword4(String lostPassword4) {
		this.lostPassword4 = lostPassword4;
	}

	public String getLostPassword5() {
		return lostPassword5;
	}

	public void setLostPassword5(String lostPassword5) {
		this.lostPassword5 = lostPassword5;
	}

	public Integer getListCount() {
		return listCount;
	}

	public void setListCount(Integer listCount) {
		this.listCount = listCount;
	}

	public Integer getUserCategory() {
		return userCategory;
	}

	public void setUserCategory(Integer userCategory) {
		this.userCategory = userCategory;
	}

	public String getPhyImage() {
		return phyImage;
	}

	public void setPhyImage(String phyImage) {
		this.phyImage = phyImage;
	}

	public String getForgetPasswordQuestion1() {
		return forgetPasswordQuestion1;
	}

	public void setForgetPasswordQuestion1(String forgetPasswordQuestion1) {
		this.forgetPasswordQuestion1 = forgetPasswordQuestion1;
	}

	public String getForgetPasswordAnswer1() {
		return forgetPasswordAnswer1;
	}

	public void setForgetPasswordAnswer1(String forgetPasswordAnswer1) {
		this.forgetPasswordAnswer1 = forgetPasswordAnswer1;
	}

	public String getForgetPasswordQuestion2() {
		return forgetPasswordQuestion2;
	}

	public void setForgetPasswordQuestion2(String forgetPasswordQuestion2) {
		this.forgetPasswordQuestion2 = forgetPasswordQuestion2;
	}

	public String getForgetPasswordAnswer2() {
		return forgetPasswordAnswer2;
	}

	public void setForgetPasswordAnswer2(String forgetPasswordAnswer2) {
		this.forgetPasswordAnswer2 = forgetPasswordAnswer2;
	}

	public String getAlotIP2() {
		return alotIP2;
	}

	public void setAlotIP2(String alotIP2) {
		this.alotIP2 = alotIP2;
	}

	public String getAlotIp1() {
		return alotIp1;
	}

	public void setAlotIp1(String alotIp1) {
		this.alotIp1 = alotIp1;
	}

	public String getBenOrg() {
		return benOrg;
	}

	public void setBenOrg(String benOrg) {
		this.benOrg = benOrg;
	}

	public Integer getIpFlag() {
		return ipFlag;
	}

	public void setIpFlag(Integer ipFlag) {
		this.ipFlag = ipFlag;
	}

	public Integer getIpFlag1() {
		return ipFlag1;
	}

	public void setIpFlag1(Integer ipFlag1) {
		this.ipFlag1 = ipFlag1;
	}

	public Integer getLoginStatus() {
		return loginStatus;
	}

	public void setLoginStatus(Integer loginStatus) {
		this.loginStatus = loginStatus;
	}

	public Integer getUserLng() {
		return userLng;
	}

	public void setUserLng(Integer userLng) {
		this.userLng = userLng;
	}

	public Integer getPassExpire() {
		return passExpire;
	}

	public void setPassExpire(Integer passExpire) {
		this.passExpire = passExpire;
	}

	public Date getCasDate() {
		return casDate;
	}

	public void setCasDate(Date casDate) {
		this.casDate = casDate;
	}

	public byte[] getProfilePicture() {
		return profilePicture;
	}

	public void setProfilePicture(byte[] profilePicture) {
		this.profilePicture = profilePicture;
	}

	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}

	public Date getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}

	public Date getTokenGenTime() {
		return tokenGenTime;
	}

	public void setTokenGenTime(Date tokenGenTime) {
		this.tokenGenTime = tokenGenTime;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getBankId() {
		return bankId;
	}

	public void setBankId(String bankId) {
		this.bankId = bankId;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	@Override
	public String toString() {
		return "Users [userId=" + userId + ", userName=" + userName + ", password=" + password + ", level0=" + level0
				+ ", group=" + group + ", orgDept=" + orgDept + ", emailId=" + emailId + ", cellNo=" + cellNo
				+ ", sysDate=" + sysDate + ", createdTime=" + createdTime + ", modifiedTime=" + modifiedTime
				+ ", logTime=" + logTime + ", numAttempt=" + numAttempt + ", accountStatus=" + accountStatus
				+ ", renewPassword=" + renewPassword + ", passwordDate=" + passwordDate + ", alertQueue=" + alertQueue
				+ ", alertHvl=" + alertHvl + ", alertSys=" + alertSys + ", alertRej=" + alertRej + ", userType="
				+ userType + ", thrLimit=" + thrLimit + ", msgTypes=" + msgTypes + ", userLimit=" + userLimit
				+ ", createdBy=" + createdBy + ", modifiedBy=" + modifiedBy + ", status=" + status + ", lostPassword1="
				+ lostPassword1 + ", lostPassword2=" + lostPassword2 + ", lostPassword3=" + lostPassword3
				+ ", lostPassword4=" + lostPassword4 + ", lostPassword5=" + lostPassword5 + ", listCount=" + listCount
				+ ", userCategory=" + userCategory + ", phyImage=" + phyImage + ", forgetPasswordQuestion1="
				+ forgetPasswordQuestion1 + ", forgetPasswordAnswer1=" + forgetPasswordAnswer1
				+ ", forgetPasswordQuestion2=" + forgetPasswordQuestion2 + ", forgetPasswordAnswer2="
				+ forgetPasswordAnswer2 + ", alotIP2=" + alotIP2 + ", alotIp1=" + alotIp1 + ", benOrg=" + benOrg
				+ ", ipFlag=" + ipFlag + ", ipFlag1=" + ipFlag1 + ", loginStatus=" + loginStatus + ", userLng="
				+ userLng + ", passExpire=" + passExpire + ", casDate=" + casDate + ", profilePicture="
				+ Arrays.toString(profilePicture) + ", idNumber=" + idNumber + ", expiryDate=" + expiryDate
				+ ", tokenGenTime=" + tokenGenTime + ", token=" + token + ", bankId=" + bankId + "]";
	}

	
	

}
