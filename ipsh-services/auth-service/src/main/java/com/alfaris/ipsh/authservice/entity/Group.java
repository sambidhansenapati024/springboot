/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.alfaris.ipsh.authservice.entity;

import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;



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
    @Column(name = "group_id")
    private String  groupId;
    @Column(name = "group_name")
    private String  groupName;
    @Column(name = "sys_date")
    @Temporal(TemporalType.DATE)
    private Date  sysDate;
    @Column(name = "time_cre")
    @Temporal(TemporalType.DATE)
    private Date  timeCre;
    @Column(name = "pro_id")
    private String  createdBy;
    @Column(name = "ver_id")
    private String  modifiedBy;
    @Column(name = "status")
    private String  status;
    @Column(name = "pro_time")
    @Temporal(TemporalType.DATE)
    private Date  proTime;
    @Column(name = "ver_time")
    @Temporal(TemporalType.DATE)
    private Date  verTime;
    @Column(name="HOME_SCREEN_URL")
    private String homeScreenUrl;
    @Column(name="GROUP_TYPE")
    private Integer groupType;
    
    

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
