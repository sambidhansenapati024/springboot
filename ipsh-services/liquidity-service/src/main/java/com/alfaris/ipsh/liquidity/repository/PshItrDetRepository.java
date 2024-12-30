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
** Version No           : 1.0.0
** Author               : Sujith K S
** Date Created         : 21-may-2020
** Modification Log     :   
CRId/ProjectId	Date Modified      	User		         Description		
Prod_1.0.0	               			 
**********************************************************************************************/
package com.alfaris.ipsh.liquidity.repository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.alfaris.ipsh.liquidity.entity.PshItrDet;
import com.alfaris.ipsh.liquidity.entity.PshItrDetPK;


@Repository
public interface PshItrDetRepository
		extends JpaRepository<PshItrDet, PshItrDetPK>, JpaSpecificationExecutor<PshItrDet> {
	
	@Query("SELECT sum(d.transactionAmount) FROM PshItrDet d WHERE d.valueDate=:todayDate AND d.status=:statusApplied")
	BigDecimal getTotalIncoming(Date todayDate, String statusApplied);

	@Query("SELECT d FROM PshItrDet d WHERE  d.createdDate >=:startDate AND d.createdDate < :endDate AND d.valueDate=:todayDate AND d.status='APPLIED'")
	Page<PshItrDet> getTodayIncoming(Date startDate,Date endDate,Date todayDate,Pageable page);
	
	
}