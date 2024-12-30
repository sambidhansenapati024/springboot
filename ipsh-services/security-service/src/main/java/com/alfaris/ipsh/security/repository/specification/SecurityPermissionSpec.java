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
** Program description  : SecurityPermissionSpec
** Version No           : 1.0.0
** Author               : Adarsh
** Date Created         : 06-April-2020
** Modification Log     :   
CRId/ProjectId	Date Modified      	User		         Description		
Prod_1.0.0	               			 
*****************************************************************************/
package com.alfaris.ipsh.security.repository.specification;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import com.alfaris.ipsh.security.entity.Screen;
import com.alfaris.ipsh.security.util.Constants;

public class SecurityPermissionSpec {
	public static Specification<Screen> getScreen(String screenId, String screenType) {
		return new Specification<Screen>() {
			@Override
			public Predicate toPredicate(Root<Screen> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				Predicate finalPredicate = null;
				try {
		            if(!StringUtils.isEmpty(screenId)) {
		            	Predicate testIdPredicate = criteriaBuilder.equal(root.get(Constants.PERMISSION_SCREEN_ID), screenId);
		            	finalPredicate = criteriaBuilder.and(testIdPredicate);
		            }
		            
		            if(!StringUtils.isEmpty(screenType)) {
		            	int type = Integer.parseInt(screenType);
		            	if(type>0){
		            		Predicate testTypePredicate = criteriaBuilder.equal(root.get(Constants.PERMISSION_CATEGORY_NO), type);
			            	finalPredicate = criteriaBuilder.and(finalPredicate,testTypePredicate);
		            	}
		            	
		            }
		            
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	            
				return finalPredicate;
			}
		};
	}
}
