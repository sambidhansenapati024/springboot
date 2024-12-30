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
** Program description  : ProductAndServiceRepository
** Version No           : 1.0.0
** Author               : Athul Rajesh
** Date Created         : 21-may-2020
** Modification Log     :   
CRId/ProjectId	Date Modified      	User		         Description		
Prod_1.0.0	               			 
**********************************************************************************************/

package com.alfaris.ipsh.liquidity.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.alfaris.ipsh.liquidity.entity.PshPrdSrv;
import com.alfaris.ipsh.liquidity.entity.PshPrdSrvPK;

@Repository
public interface ProductAndServiceRepository extends JpaRepository<PshPrdSrv, PshPrdSrvPK> {

//	@Query("SELECT distinct d.id.serviceId FROM PshPrdSrv d where d.id.bankId =:bankId")
	@Query("SELECT distinct d.id.serviceId FROM PshPrdSrv d")
	List<String> getServiceIds();

}
