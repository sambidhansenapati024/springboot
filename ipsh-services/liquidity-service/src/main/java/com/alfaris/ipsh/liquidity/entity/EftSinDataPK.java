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
** Program description  : AccountDefintionPK
** Version No           : 1.0.0
** Author               : Jamespaul Jose
** Date Created         : 14-May-2021
** Modification Log     :   
CRId/ProjectId	Date Modified      	User		         Description		
Prod_1.0.0	               			 
*****************************************************************************/
package  com.alfaris.ipsh.liquidity.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class EftSinDataPK implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Column(name = "SSN")
	private Integer ssn;
	
	@Column(name = "SEQN")
	private Integer seqn;

	public Integer getSsn() {
		return ssn;
	}

	public void setSsn(Integer ssn) {
		this.ssn = ssn;
	}

	public Integer getSeqn() {
		return seqn;
	}

	public void setSeqn(Integer seqn) {
		this.seqn = seqn;
	}

	public EftSinDataPK(Integer ssn, Integer seqn) {
		super();
		this.ssn = ssn;
		this.seqn = seqn;
	}

	public EftSinDataPK() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "EftSinDataPK [ssn=" + ssn + ", seqn=" + seqn + "]";
	}

	

	


}
