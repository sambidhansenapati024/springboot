package com.alfaris.ipsh.security.entity;

import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

/**
 * The persistent class for the PSH_USR_NTY database table.
 * 
 */
@Entity
@Table(name = "PSH_USR_NTY", schema = "IPSH")
@NamedQuery(name = "PshUsrNty.findAll", query = "SELECT p FROM PshUsrNty p")
public class PshUsrNty implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "NOTIFICATION_ID")
	private String notificationId;

	@Column(name = "ACTION_BY")
	private String actionBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "ACTION_ON")
	private Date actionOn;

	@Column(name = "CREATED_BY")
	private String createdBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATED_ON")
	private Date createdOn;

	private String description;

	@Column(name = "NOTIFICATION_MESSAGE")
	private String notificationMessage;

	@Column(name = "NOTIFY_GROUP")
	private String notifyGroup;

	@Column(name = "SERVICE_TYPE")
	private String serviceType;

	private String status;

	@Column(name = "NOTIFICATION_TYPE")
	private String notificationType;
	
	@Column(name = "SCREEN_ID")
	private String screenId;

	public PshUsrNty() {
	}

	public String getNotificationId() {
		return notificationId;
	}

	public void setNotificationId(String notificationId) {
		this.notificationId = notificationId;
	}

	public String getActionBy() {
		return actionBy;
	}

	public void setActionBy(String actionBy) {
		this.actionBy = actionBy;
	}

	public Date getActionOn() {
		return actionOn;
	}

	public void setActionOn(Date actionOn) {
		this.actionOn = actionOn;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getNotificationMessage() {
		return notificationMessage;
	}

	public void setNotificationMessage(String notificationMessage) {
		this.notificationMessage = notificationMessage;
	}

	public String getNotifyGroup() {
		return notifyGroup;
	}

	public void setNotifyGroup(String notifyGroup) {
		this.notifyGroup = notifyGroup;
	}

	public String getServiceType() {
		return serviceType;
	}

	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getNotificationType() {
		return notificationType;
	}

	public void setNotificationType(String notificationType) {
		this.notificationType = notificationType;
	}

	public String getScreenId() {
		return screenId;
	}

	public void setScreenId(String screenId) {
		this.screenId = screenId;
	}

}