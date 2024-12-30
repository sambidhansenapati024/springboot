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
package com.alfaris.ipsh.liquidity.repository.specification;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import com.alfaris.ipsh.liquidity.entity.PshItrDet;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;


public class PshItrDetSpecs {
	private static Logger logger = LogManager.getLogger(PshItrDetSpecs.class);

	private PshItrDetSpecs() {

	}

	public static Specification<PshItrDet> getRecordsBySearchSpec(String searchParam) {
		return new Specification<PshItrDet>() {
			@Override
			public Predicate toPredicate(Root<PshItrDet> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				Predicate finalPredicate = null;
				JSONParser parser = new JSONParser();
				JSONObject searchObject;
				try {
					SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
					SimpleDateFormat formatter2 = new SimpleDateFormat("dd-MM-yyyy");
					
					 
					 
					 
					 
					 
					
					if (!StringUtils.isEmpty(searchParam)) {
						searchObject = (JSONObject) parser.parse(searchParam);
						String createdDateFromStr = (String) searchObject.get("createdDateFrom");
						String createdDateToStr = (String) searchObject.get("createdDateTo");
						
						int createdDateFrom = Integer.parseInt(createdDateFromStr); 
						int createdDateTo = Integer.parseInt(createdDateToStr); 
						
						
 						if (!StringUtils.isEmpty(createdDateFrom)) {
 							Calendar calendar = Calendar.getInstance();
 							calendar.set(Calendar.HOUR_OF_DAY,createdDateFrom);
 							calendar.set(Calendar.MINUTE,0);
 							calendar.set(Calendar.SECOND,0);
 							calendar.set(Calendar.MILLISECOND,0);

 							
 							Date start = calendar.getTime();
 							
 							
							Predicate startPredicate = criteriaBuilder.greaterThanOrEqualTo(root.<Date>get("createdDate"),
									start);
							if (finalPredicate != null)
								finalPredicate = criteriaBuilder.and(finalPredicate, startPredicate);
							else
								finalPredicate = criteriaBuilder.and(startPredicate);
						}

						if (!StringUtils.isEmpty(createdDateTo)) {
							Calendar calendar = Calendar.getInstance();
 							calendar.set(Calendar.HOUR_OF_DAY,createdDateTo);
 							calendar.set(Calendar.MINUTE,0);
 							calendar.set(Calendar.SECOND,0);
 							calendar.set(Calendar.MILLISECOND,0);

 							
 							Date end = calendar.getTime();
							Predicate endPredicate = criteriaBuilder.lessThan(root.<Date>get("createdDate"),
									end);
							if (finalPredicate != null)
								finalPredicate = criteriaBuilder.and(finalPredicate, endPredicate);
							else
								finalPredicate = criteriaBuilder.and(endPredicate);
						}
					}
					
					//ValueDate
					Date today = formatter2.parse(formatter2.format(new Date())) ;
					Predicate endPredicate = criteriaBuilder.equal(root.<Date>get("valueDate"),
							today);
					if (finalPredicate != null)
						finalPredicate = criteriaBuilder.and(finalPredicate, endPredicate);
					else
						finalPredicate = criteriaBuilder.and(endPredicate);
					
				
					//status
					Predicate statusPredicate = criteriaBuilder.equal(criteriaBuilder.upper(root.get("status")),"APPLIED");
					if (finalPredicate != null)
						finalPredicate = criteriaBuilder.and(finalPredicate, statusPredicate);
					else
						finalPredicate = criteriaBuilder.and(statusPredicate);
					query.orderBy(criteriaBuilder.asc(root.get("createdDate")));
				} catch (Exception e) {
					logger.error("Error : " + e.getMessage(), e);
				}

				return finalPredicate;
			}
		};
	}

}
