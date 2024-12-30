/****************************************************************************
 *                              COPYRIGHT NOTICE
*
*                      Copyright(@2006) by Interland Technology Services PVT. LTD **
*
*      This program is used to monitor the stream control and Stop/Start
*      the streams. The program and related materials are confidential and
*      proprietary of Interland Technology Services PVT. LTD and no part of these materials
*      should be reproduced, published in any forms without the written
*      approval of INTERLAND
*
** Project Name         : iPSH
** Program description  : Group
** Version No           : 1.0.0
** Author               : Yadhu
** Date Created         : 06-April-2020
** Modification Log     :   
CRId/ProjectId	Date Modified      	User		         Description		
Prod_1.0.0	               			 
*****************************************************************************/

package com.alfaris.ipsh.security.entity;

import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

import com.alfaris.ipsh.security.entity.validation.CustomSize;

/**
*
* @author Interland
*/
@Entity
@Table(name = "EFTSECGRP", schema = "IPSH")
public class Group implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@NotEmpty(message = "{NotEmpty.group.groupId}")  
	@Pattern(regexp="^[a-zA-Z0-9 ]+$", message="{Pattern.group.groupId}")
	@CustomSize(minKey="group.groupId.min", maxKey="group.groupId.max")
	@Column(name = "group_id")
    private String  groupId;
	
	@NotEmpty(message = "{NotEmpty.group.groupName}")  
	@Pattern(regexp="^[a-zA-Z0-9 ]+$", message="{Pattern.group.groupName}")
	@CustomSize(minKey="group.groupName.min",maxKey="group.groupName.max")
    @Column(name = "group_name")
    private String  groupName;
    
    @Column(name = "sys_date")
	@Temporal(TemporalType.TIMESTAMP)
    private Date  sysDate;
    
    @Column(name = "time_cre")
    @Temporal(jakarta.persistence.TemporalType.DATE)
    private Date  timeCre;
    
    @Column(name = "pro_id")
    private String  createdBy;
    
    @Column(name = "ver_id")
    private String  modifiedBy;
    
    @Column(name = "status")
    private String  status;
    
    @Column(name = "pro_time")
    @Temporal(jakarta.persistence.TemporalType.DATE)
    private Date  proTime;
    
    @Column(name = "ver_time")
    @Temporal(jakarta.persistence.TemporalType.DATE)
    private Date  verTime;
    
    @NotEmpty(message = "{NotEmpty.group.homeScreenUrl}")  
    @Column(name="HOME_SCREEN_URL")
    private String homeScreenUrl;
    
    @Column(name="GROUP_TYPE")
    private Integer groupType;
    
    @Column(name="NOTIFICATION_ID")
	private String notificationId;
    
    

	public String getNotificationId() {
		return notificationId;
	}

	public void setNotificationId(String notificationId) {
		this.notificationId = notificationId;
	}

	public Integer getGroupType() {
		return groupType;
	}

	public void setGroupType(Integer groupType) {
		this.groupType = groupType;
	}

	public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public Date getSysDate() {
        return sysDate;
    }

    public void setSysDate(Date sysDate) {
        this.sysDate = sysDate;
    }

    public Date getTimeCre() {
        return timeCre;
    }

    public void setTimeCre(Date timeCre) {
        this.timeCre = timeCre;
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getProTime() {
        return proTime;
    }

    public void setProTime(Date proTime) {
        this.proTime = proTime;
    }

    public Date getVerTime() {
        return verTime;
    }

    public void setVerTime(Date verTime) {
        this.verTime = verTime;
    }

    public String getHomeScreenUrl() {
        return homeScreenUrl;
    }

    public void setHomeScreenUrl(String homeScreenUrl) {
        this.homeScreenUrl = homeScreenUrl;
    }

    
    
}
