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
** Program description  : AccountDefinitionRepository 
** Version No           : 1.0.0
** Author               : Jamespaul Jose
** Date Created         : 14-May-2021
** Modification Log     :   
CRId/ProjectId	Date Modified      	User		         Description		
Prod_1.0.0	               			 
*****************************************************************************/
package com.alfaris.ipsh.liquidity.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.alfaris.ipsh.liquidity.entity.EftSinData;
import com.alfaris.ipsh.liquidity.entity.EftSinDataPK;



public interface EftSinDataRepository
		extends JpaRepository<EftSinData, EftSinDataPK>, JpaSpecificationExecutor<EftSinData> {



	
	@Query("SELECT o FROM EftSinData o WHERE o.date>=:todayDate AND o.status=:status AND o.data LIKE '%SDI   %'")
	List<EftSinData> findSdiByTodayandStatus(Date todayDate, String status);
}
