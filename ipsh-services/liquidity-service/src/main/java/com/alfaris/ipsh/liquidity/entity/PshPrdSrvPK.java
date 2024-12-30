/*********************************************************************************************
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
** Program description  : PshPrdSrvPK
** Version No           : 1.0.0
** Author               : Athul Rajesh
** Date Created         : 21-may-2020
** Modification Log     :   
CRId/ProjectId	Date Modified      	User		         Description		
Prod_1.0.0	               			 
**********************************************************************************************/

package com.alfaris.ipsh.liquidity.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class PshPrdSrvPK implements Serializable{
	
	@Column(name="BANK_ID")
	private String bankId;
	
	@Column(name="PRODUCT_ID")
	private String productId;
	
	@Column(name="SERVICE_ID")
	private String serviceId;
	
	public PshPrdSrvPK() {
		super();
	}
	
	public PshPrdSrvPK(String bankId, String serviceId, String productId) {
		super();
		this.bankId=bankId;
		this.productId=productId;
		this.serviceId=serviceId;
	}

}
