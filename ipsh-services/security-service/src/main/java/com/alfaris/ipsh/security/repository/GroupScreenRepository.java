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
** Program description  : GroupScreenRepository
** Version No           : 1.0.0
** Author               : Adarsh
** Date Created         : 06-April-2020
** Modification Log     :   
CRId/ProjectId	Date Modified      	User		         Description		
Prod_1.0.0	               			 
*****************************************************************************/
package com.alfaris.ipsh.security.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.alfaris.ipsh.security.entity.GroupScreen;
import com.alfaris.ipsh.security.entity.GroupScreenPK;

@Repository
public interface GroupScreenRepository
		extends JpaRepository<GroupScreen, GroupScreenPK>, JpaSpecificationExecutor<GroupScreen> {

	List<GroupScreen> findAllByIdGroupId(String groupId);

	@Query("select a from GroupScreen as a where a.id.groupId=?1")
	List<GroupScreen> findAll(String groupId);

	@Query("select a.id.groupId from GroupScreen as a where a.verFl='Y' AND a.id.screenId=:screenId")
	List<String> findVerifiableGroups(String screenId);

	@Query("select a.id.groupId from GroupScreen as a where a.id.screenId=:screenId AND (a.addFl='Y' OR a.updFl='Y')")
	List<String> findProcessorGroups(String screenId);

	@Query("select a.id.groupId from GroupScreen as a where a.delFl='Y' AND a.id.screenId=:screenId")
	List<String> findDeleteGroups(String screenId);
}
