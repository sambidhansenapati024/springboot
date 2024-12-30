/**************************
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
** Program description  : PshUsrDepPK
** Version No           : 1.0.0
** Author               : Sabari S 
** Date Created         : 17-May-2021
** Modification Log     :   
CRId/ProjectId	Date Modified      	User		         Description		
Prod_1.0.0	               			 
***************************/


package com.alfaris.ipsh.security.entity;

import java.io.Serializable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;


/**
 * The primary key class for the UPS_PAY_HDR database table.
 * 
 */
@Embeddable
public class PshUsrDepPK implements Serializable {
	// default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;
	
	@Column(name = "BIC")
	private String bic;

	@Column(name = "DEP")
	private String dep;
	
	@NotNull
	@Column(name = "VERSION_NUMBER")
	private long versionNumber;


	public String getBic() {
		return bic;
	}


	public void setBic(String bic) {
		this.bic = bic;
	}


	public String getDep() {
		return dep;
	}


	public void setDep(String dep) {
		this.dep = dep;
	}


	public long getVersionNumber() {
		return versionNumber;
	}


	public void setVersionNumber(long versionNumber) {
		this.versionNumber = versionNumber;
	}


	public PshUsrDepPK() {
		super();
		// TODO Auto-generated constructor stub
	}


	public PshUsrDepPK(@NotEmpty @Size(max = 11) String bic, @NotEmpty @Size(max = 16) String dep) {
		super();
		this.bic = bic;
		this.dep = dep;
	}


	public PshUsrDepPK(@NotEmpty @Size(max = 11) String bic, @NotEmpty @Size(max = 16) String dep, long versionNumber) {
		super();
		this.bic = bic;
		this.dep = dep;
		this.versionNumber = versionNumber;
	}


	
	
	
	
}