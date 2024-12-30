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
** Program description  : SecurityUserLog
** Version No           : 1.0.0
** Author               : Harichand H S
** Date Created         : 06-April-2020
** Modification Log     :   
CRId/ProjectId	Date Modified      	User		         Description		
Prod_1.0.0	               			 
*****************************************************************************/
package com.alfaris.ipsh.security.repository.specification;

import java.text.SimpleDateFormat;
import java.util.Date;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import com.alfaris.ipsh.security.dto.SearchUserLog;
import com.alfaris.ipsh.security.entity.LogDetails;
import com.alfaris.ipsh.security.util.Constants;

public class SecurityUserLogSpec {
	
	public static Specification<LogDetails> getHeaderBySearchSpec(String searchParam) {

		return new Specification<LogDetails>() {
			
			@Override
			public Predicate toPredicate(Root<LogDetails> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				
				JSONParser parser = new JSONParser();
				JSONObject searchObject;
				Predicate finalPredicate = null;
				String date[]=null;
				String startDate="";
				String endDate="";
				try {
					if(!StringUtils.isEmpty(searchParam)) {
					SimpleDateFormat format1 = new SimpleDateFormat(Constants.USER_LOG_DATE_FORMAT1);
				    SimpleDateFormat format2 = new SimpleDateFormat(Constants.USER_LOG_DATE_FORMAT2);
					searchObject = (JSONObject) parser.parse(searchParam);
					String userId = (String) searchObject.get(Constants.USER_LOG_USER_ID);
					String ipAddress = (String) searchObject.get(Constants.USER_LOG_IP_ADDRESS);
					String status = (String) searchObject.get(Constants.USER_LOG_STATUS);
					String dateRange = (String) searchObject.get(Constants.USER_LOG_LOG_TIME);
					if(!StringUtils.isEmpty(dateRange)) {
						 date = dateRange.split("-");
						 startDate = date[0].trim();
						 endDate = date[1].trim();
					}
					
					if(!StringUtils.isEmpty(userId)) {
						Predicate userIdPredicate = criteriaBuilder.equal(root.get(Constants.USER_LOG_PK).get(Constants.USER_LOG_USER_ID),userId);
						finalPredicate = criteriaBuilder.and(userIdPredicate);
					}
					if(!StringUtils.isEmpty(status)) {
		            	Predicate statusPredicate = criteriaBuilder.equal(root.get(Constants.USER_LOG_STATUS),status);
		            	if(finalPredicate!=null) 
		            		finalPredicate = criteriaBuilder.and(finalPredicate,statusPredicate);
		            	else
		            		finalPredicate = criteriaBuilder.and(statusPredicate);
		            		
		            }
		            
		            if(!StringUtils.isEmpty(ipAddress)) {
		            	Predicate ipAdPredicate = criteriaBuilder.equal(root.get(Constants.USER_LOG_IP_ADDRESS),ipAddress);
		            	if(finalPredicate!=null) 
		            		finalPredicate = criteriaBuilder.and(finalPredicate,ipAdPredicate);
		            	else
		            		finalPredicate = criteriaBuilder.and(ipAdPredicate); 
		            }
		            if(!StringUtils.isEmpty(startDate)) {
		            	Date date1 = format1.parse(startDate);
		            	String date3 = format2.format(date1);
		            	Date start = format2.parse(date3);
		            	Predicate startPredicate = criteriaBuilder.greaterThanOrEqualTo(root.<Date>get(Constants.USER_LOG_SYSTEM_DATE),start);
		            	if(finalPredicate!=null) 
		            		finalPredicate = criteriaBuilder.and(finalPredicate,startPredicate);
		            	else
		            		finalPredicate = criteriaBuilder.and(startPredicate); 
		            }
		            if(!StringUtils.isEmpty(endDate)) {
		            	Date date2 = format1.parse(endDate);
		            	String date4= format2.format(date2);
		            	Date end = format2.parse(date4);
		            	Predicate startPredicate = criteriaBuilder.lessThanOrEqualTo(root.<Date>get(Constants.USER_LOG_SYSTEM_DATE), end);
		            	if(finalPredicate!=null) 
		            		finalPredicate = criteriaBuilder.and(finalPredicate,startPredicate);
		            	else
		            		finalPredicate = criteriaBuilder.and(startPredicate); 
		            }
		            query.orderBy(criteriaBuilder.desc(root.get(Constants.USER_LOG_PK).get(Constants.USER_LOG_LOG_TIME)));
					}
					}catch (Exception e) {
					e.printStackTrace();
				}
				return finalPredicate;
			}
			
		};
	
	}

	public static Specification<LogDetails> getHeaderBySearchSpecReport(SearchUserLog usersearch) {
			
		return new Specification<LogDetails>() {
			
			@Override
			public Predicate toPredicate(Root<LogDetails> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				
				
				Predicate finalPredicate = null;
				String date[]=null;
				String startDate="";
				String endDate="";
				try {
					SimpleDateFormat format1 = new SimpleDateFormat(Constants.USER_LOG_DATE_FORMAT1);
				    SimpleDateFormat format2 = new SimpleDateFormat(Constants.USER_LOG_DATE_FORMAT2);
					
					String userId = usersearch.getUserId();
					String ipAddress = usersearch.getIpAddress();
					String status = usersearch.getLogStatus();
					String dateRange = usersearch.getLogTime();
					if(!StringUtils.isEmpty(dateRange)) {
						 date = dateRange.split("-");
						 startDate = date[0].trim();
						 endDate = date[1].trim();
					}
					
					if(!StringUtils.isEmpty(userId)) {
						Predicate userIdPredicate = criteriaBuilder.equal(root.get(Constants.USER_LOG_PK).get(Constants.USER_LOG_USER_ID),userId);
						finalPredicate = criteriaBuilder.and(userIdPredicate);
					}
					if(!StringUtils.isEmpty(status)) {
		            	Predicate statusPredicate = criteriaBuilder.equal(root.get(Constants.USER_LOG_STATUS),status);
		            	if(finalPredicate!=null) 
		            		finalPredicate = criteriaBuilder.and(finalPredicate,statusPredicate);
		            	else
		            		finalPredicate = criteriaBuilder.and(statusPredicate);
		            		
		            }
		            
		            if(!StringUtils.isEmpty(ipAddress)) {
		            	Predicate ipAdPredicate = criteriaBuilder.equal(root.get(Constants.USER_LOG_IP_ADDRESS),ipAddress);
		            	if(finalPredicate!=null) 
		            		finalPredicate = criteriaBuilder.and(finalPredicate,ipAdPredicate);
		            	else
		            		finalPredicate = criteriaBuilder.and(ipAdPredicate); 
		            }
		            if(!StringUtils.isEmpty(startDate)) {
		            	Date date1 = format1.parse(startDate);
		            	String date3 = format2.format(date1);
		            	Date start = format2.parse(date3);
		            	Predicate startPredicate = criteriaBuilder.greaterThanOrEqualTo(root.<Date>get(Constants.USER_LOG_SYSTEM_DATE),start);
		            	if(finalPredicate!=null) 
		            		finalPredicate = criteriaBuilder.and(finalPredicate,startPredicate);
		            	else
		            		finalPredicate = criteriaBuilder.and(startPredicate); 
		            }
		            if(!StringUtils.isEmpty(endDate)) {
		            	Date date2 = format1.parse(endDate);
		            	String date4= format2.format(date2);
		            	Date end = format2.parse(date4);
		            	Predicate startPredicate = criteriaBuilder.lessThanOrEqualTo(root.<Date>get(Constants.USER_LOG_SYSTEM_DATE), end);
		            	if(finalPredicate!=null) 
		            		finalPredicate = criteriaBuilder.and(finalPredicate,startPredicate);
		            	else
		            		finalPredicate = criteriaBuilder.and(startPredicate); 
		            }
		            query.orderBy(criteriaBuilder.desc(root.get(Constants.USER_LOG_PK).get(Constants.USER_LOG_LOG_TIME)));
				}catch (Exception e) {
					e.printStackTrace();
				}
				return finalPredicate;
			}
			
		};
	
	}

}
