package com.alfaris.ipsh.security.dto;


import java.util.Date;

import jakarta.persistence.Temporal;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

import com.alfaris.ipsh.security.entity.Group;
import com.alfaris.ipsh.security.entity.validation.CustomSize;

public class UsersDto {
	

	@NotBlank(message = "{NotBlank.usersDto.userId}")
	@CustomSize(minKey = "usersDto.userId.min", maxKey = "usersDto.userId.max", message="{CustomSize.usersDto.userId}")
	@Pattern(regexp = "^[a-zA-Z0-9@\\.\\-\\_ ]+$", message = "{Pattern.usersDto.userId}")
	private String userId;
	
	@NotBlank(message = "{NotBlank.usersDto.userName}")
	@CustomSize(minKey = "usersDto.userName.min", maxKey = "usersDto.userName.max", message="{CustomSize.usersDto.userName}")
	@Pattern(regexp = "^[a-zA-Z0-9@\\.\\-\\_ ]+$", message = "{Pattern.usersDto.userName}")
	private String userName;

//	@CustomSize(minKey = "usersDto.password.min", maxKey = "usersDto.password.max")
//	@Pattern(regexp = "^\\S*$", message = "{Pattern.usersDto.password}")
//	@NotEmpty(message = "{NotEmpty.usersDto.password}")
	private String password;

	private Integer level0;
	
	// this duplicate property is used for the validation
	@NotBlank(message = "{NotBlank.usersDto.groupId}")
	private String groupId;

   

	private Group group;


	@NotEmpty(message = "{NotEmpty.usersDto.orgDept}")
	private String orgDept;

	@CustomSize(minKey = "usersDto.emailId.min", maxKey = "usersDto.emailId.max", message="{CustomSize.usersDto.emailId}")
	@Pattern(regexp = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$", message = "{Pattern.usersDto.emailId}")
	private String emailId;

//	@CustomSize(minKey = "usersDto.cellNo.min", maxKey = "usersDto.cellNo.max", message="{CustomSize.usersDto.cellNo}")
//	@Pattern(regexp = "^[0-9+]+$", message = "{Pattern.usersDto.cellNo}")
	@Pattern(regexp = "^(\\d{9,12})?$", message = "{Pattern.usersDto.cellNo}")
	private String cellNo;


	@Temporal(jakarta.persistence.TemporalType.DATE)
	private Date sysDate;

	@Temporal(jakarta.persistence.TemporalType.DATE)
	private Date createdTime;

	@Temporal(jakarta.persistence.TemporalType.DATE)
	private Date modifiedTime;

	@Temporal(jakarta.persistence.TemporalType.DATE)
	private Date logTime;

	private Integer numAttempt;

	private String accountStatus;

	private Integer renewPassword;

	@Temporal(jakarta.persistence.TemporalType.DATE)
	private Date passwordDate;

	private Integer alertQueue;

	private Integer alertHvl;

	private Integer alertSys;

	private Integer alertRej;

	private String userType;

	private Integer thrLimit;

	private String msgTypes;

	private Integer userLimit;

	private String createdBy;

	private String modifiedBy;

	private String status;

	private String lostPassword1;

	private String lostPassword2;

	private String lostPassword3;

	private String lostPassword4;

	private String lostPassword5;

	private Integer listCount;

	private Integer userCategory;

	private String phyImage;

	private String forgetPasswordQuestion1;

	private String forgetPasswordAnswer1;

	private String forgetPasswordQuestion2;

	private String forgetPasswordAnswer2;

	private String alotIP2;

	private String alotIp1;

	private String benOrg;

	private Integer ipFlag;

	private Integer ipFlag1;

	private Integer loginStatus;

	private Integer userLng;

	private Integer passExpire;

	@Temporal(jakarta.persistence.TemporalType.TIMESTAMP)
	private Date casDate;

	private byte[] profilePicture;

	private String idNumber;

	private Date expiryDate;

	@Temporal(jakarta.persistence.TemporalType.TIMESTAMP)
	private Date tokenGenTime;

	private String token;
	
	@Pattern(regexp = "^[a-zA-Z0-9 ]+$", message = "{Pattern.usersDto.bankId}")
	@NotEmpty(message = "{NotEmpty.usersDto.bankId}")
	private String bankId;
	
//	@CustomSize(minKey = "usersDto.password.min", maxKey = "usersDto.password.max")
//	@Pattern(regexp = "^\\S*$", message = "{Pattern.usersDto.confirmPassword}")
//	@NotEmpty(message = "{NotEmpty.usersDto.confirmPassword}")
	private String confirmPassword;

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

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
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

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}



}
