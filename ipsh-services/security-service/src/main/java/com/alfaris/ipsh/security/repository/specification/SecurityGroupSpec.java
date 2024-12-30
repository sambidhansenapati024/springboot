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
** Program description  : SecurityGroupSpec
** Version No           : 1.0.0
** Author               : Yadhu
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

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import com.alfaris.ipsh.security.entity.Group;
import com.alfaris.ipsh.security.util.Constants;

public class SecurityGroupSpec {
	public static Specification<Group> getGroupByNameSpec(String searchParam) {
		
		

		return new Specification<Group>() {
			@Override
			public Predicate toPredicate(Root<Group> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				Predicate finalPredicate = null;
				JSONParser parser = new JSONParser();
				JSONObject searchObject;
				try {
					if(!StringUtils.isEmpty(searchParam)) {
					searchObject = (JSONObject) parser.parse(searchParam);
					String groupId = (String) searchObject.get(Constants.GROUP_ID);
					String groupName = (String) searchObject.get(Constants.GROUP_NAME);
					String groupType = (String) searchObject.get(Constants.GROUP_TYPE);
					String status = (String) searchObject.get(Constants.GROUP_STATUS);
		            if(!StringUtils.isEmpty(groupId)) {
		            	Predicate testIdPredicate = criteriaBuilder.equal(root.get(Constants.GROUP_ID), groupId);
		            	finalPredicate = criteriaBuilder.and(testIdPredicate);
		            }
		            
		            if(!StringUtils.isEmpty(groupName)) {
		            	Predicate testNamePredicate = criteriaBuilder.like(root.get(Constants.GROUP_NAME), "%"+groupName+"%");
		            	if(finalPredicate!=null) {
		            		finalPredicate = criteriaBuilder.and(finalPredicate,testNamePredicate);
		            	}else {
		            		finalPredicate = criteriaBuilder.and(testNamePredicate);
		            	}
		            	
		            }
		            
		            if(!StringUtils.isEmpty(groupType)) {
		            	Predicate testTypePredicate = criteriaBuilder.equal(root.get(Constants.GROUP_TYPE), groupType);
		            	if(finalPredicate!=null) {
		            		finalPredicate = criteriaBuilder.and(finalPredicate,testTypePredicate);
		            	}else {
		            		finalPredicate = criteriaBuilder.and(testTypePredicate);
		            	}
		            }
		            
		            if(!StringUtils.isEmpty(status)) {
		            	Predicate statusPredicate = criteriaBuilder.equal(root.get(Constants.GROUP_STATUS), status);
		            	if(finalPredicate!=null) {
		            		finalPredicate = criteriaBuilder.and(finalPredicate,statusPredicate);
		            	}else {
		            		finalPredicate = criteriaBuilder.and(statusPredicate);
		            	}
		            }
					}
					query.orderBy(criteriaBuilder.desc(root.get("sysDate")));

		            
				} catch (ParseException e) {
					e.printStackTrace();
				}
	            
				return finalPredicate;
			}
		};
	
		
		
		
		
		
	}

}
