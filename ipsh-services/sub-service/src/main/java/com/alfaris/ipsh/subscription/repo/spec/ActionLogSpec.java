package com.alfaris.ipsh.subscription.repo.spec;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import com.alfaris.ipsh.subscription.dto.ActionLogDto;
import com.alfaris.ipsh.subscription.entity.ActionLog;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Order;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public class ActionLogSpec {
	public static Specification<ActionLog> getUserSpec(String searchParam) { 
		return new Specification<ActionLog>() {
			@Override
			public Predicate toPredicate(Root<ActionLog> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				Predicate finalPredicate = null;
				JSONParser parser = new JSONParser();
				JSONObject searchObject;
				String date[] = null;
				String startDate = "";
				String endDate = "";
				try {
					
					SimpleDateFormat format1 = new SimpleDateFormat("dd/MM/yyyy");

					SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
					
					if (!StringUtils.isEmpty(searchParam)) {
						searchObject = (JSONObject) parser.parse(searchParam);
						String status = (String) searchObject.get("status");
						String action = (String) searchObject.get("action");
						String sysTime = (String) searchObject.get("systime");
						String email = (String) searchObject.get("Reason");
						String userName = (String) searchObject.get("userName");
						String dateRange = (String) searchObject.get("valueDate");
						if (!StringUtils.isEmpty(dateRange)) {
							date = dateRange.split("-");
							startDate = date[0].trim();
							endDate = date[1].trim();
						}
						
						
						 if(!StringUtils.isEmpty(status)) {
				            	Predicate statusPredicate = criteriaBuilder.equal(root.get("status"), status);
				            	finalPredicate = criteriaBuilder.and(statusPredicate);
				            }
				            
				            if(!StringUtils.isEmpty(sysTime)) {
				            	Predicate uniqSubIdPredicate = criteriaBuilder.like(root.get("uniqSubId"),sysTime);
				            	if(finalPredicate!=null) {
				            		finalPredicate = criteriaBuilder.and(finalPredicate, uniqSubIdPredicate);
				            	}else {
				            		finalPredicate = criteriaBuilder.and(uniqSubIdPredicate);
				            	}
				            	
				            }
				            if(!StringUtils.isEmpty(action)) {
				            	Predicate uniqSubIdPredicate = criteriaBuilder.like(root.get("action"),action);
				            	if(finalPredicate!=null) {
				            		finalPredicate = criteriaBuilder.and(finalPredicate, uniqSubIdPredicate);
				            	}else {
				            		finalPredicate = criteriaBuilder.and(uniqSubIdPredicate);
				            	}
				            	 
				            }
				            
				            if(!StringUtils.isEmpty(email)) {
				            	Predicate userTypePredicate = criteriaBuilder.equal(root.get("email"), email);
				            	if(finalPredicate!=null) {
				            		finalPredicate = criteriaBuilder.and(finalPredicate, userTypePredicate);
				            	}else {
				            		finalPredicate = criteriaBuilder.and(userTypePredicate);
				            	}
				            }
				            
				            if(!StringUtils.isEmpty(userName)) {
				            	Predicate userNamePredicate = criteriaBuilder.like(root.get("pk").get("userName"),userName);
				            	if(finalPredicate!=null) {
				            		finalPredicate = criteriaBuilder.and(finalPredicate, userNamePredicate);
				            	}else {
				            		finalPredicate = criteriaBuilder.and(userNamePredicate); 
				            	}
				            }
//				            if(!StringUtils.isEmpty(platforms)) {
//				            	Predicate platformsPredicate = criteriaBuilder.like(root.get("pk").get("platforms"),platforms);
//				            	if(finalPredicate!=null) {
//				            		finalPredicate = criteriaBuilder.and(finalPredicate, platformsPredicate);
//				            	}else {
//				            		finalPredicate = criteriaBuilder.and(platformsPredicate); 
//				            	}
//				            }
				            
				            if(!StringUtils.isEmpty(startDate)) {
				            	Date date1 = format1.parse(startDate);
				            	String date3 = formatter.format(date1);
				            	Date start = formatter.parse(date3);
				            	Predicate startPredicate = criteriaBuilder.greaterThanOrEqualTo(root.get("pk").get("Actiontime"),start);
				            	if(finalPredicate!=null) 
				            		finalPredicate = criteriaBuilder.and(finalPredicate,startPredicate);
				            	else
				            		finalPredicate = criteriaBuilder.and(startPredicate); 
				            }
				            if(!StringUtils.isEmpty(endDate)) {
				            	Date date2 = format1.parse(endDate);
				            	String date4= formatter.format(date2);
				            	Date end = formatter.parse(date4);
				            	Predicate startPredicate = criteriaBuilder.lessThanOrEqualTo(root.get("pk").get("Actiontime"), end);
				            	if(finalPredicate!=null) 
				            		finalPredicate = criteriaBuilder.and(finalPredicate,startPredicate);
				            	else
				            		finalPredicate = criteriaBuilder.and(startPredicate); 
				            }						
					}
		           
		            
		            Order proTimeOrder = criteriaBuilder.desc(root.get("systime"));
		            query.orderBy(proTimeOrder);
		            
				} catch (ParseException | java.text.ParseException e) {
					e.printStackTrace();
				}catch (Exception e) {
				}
	            
				return finalPredicate;
			}
		};
	}
	
	public static Specification<ActionLog> getHeaderBySearchSpecReport(ActionLogDto usersearch) { 
		return new Specification<ActionLog>() {
			@Override
			public Predicate toPredicate(Root<ActionLog> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				Predicate finalPredicate = null;
				JSONParser parser = new JSONParser();
				JSONObject searchObject;
				String date[] = null;
				String startDate = "";
				String endDate = "";
				try {
					
					SimpleDateFormat format1 = new SimpleDateFormat("dd/MM/yyyy");

					SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
					
					
						String status = usersearch.getStatus();
						String action = usersearch.getAction();
						String userName = usersearch.getUserName();
					
						
						
						 if(!StringUtils.isEmpty(status)) {
				            	Predicate statusPredicate = criteriaBuilder.equal(root.get("status"), status);
				            	finalPredicate = criteriaBuilder.and(statusPredicate);
				            }
				            
	
				            if(!StringUtils.isEmpty(action)) {
				            	Predicate uniqSubIdPredicate = criteriaBuilder.like(root.get("action"),action);
				            	if(finalPredicate!=null) {
				            		finalPredicate = criteriaBuilder.and(finalPredicate, uniqSubIdPredicate);
				            	}else {
				            		finalPredicate = criteriaBuilder.and(uniqSubIdPredicate);
				            	}
				            	 
				            }
				            
				           
				            if(!StringUtils.isEmpty(userName)) {
				            	Predicate userNamePredicate = criteriaBuilder.like(root.get("pk").get("userName"),userName);
				            	if(finalPredicate!=null) {
				            		finalPredicate = criteriaBuilder.and(finalPredicate, userNamePredicate);
				            	}else {
				            		finalPredicate = criteriaBuilder.and(userNamePredicate); 
				            	}
				            }
//				            if(!StringUtils.isEmpty(platforms)) {
//				            	Predicate platformsPredicate = criteriaBuilder.like(root.get("pk").get("platforms"),platforms);
//				            	if(finalPredicate!=null) {
//				            		finalPredicate = criteriaBuilder.and(finalPredicate, platformsPredicate);
//				            	}else {
//				            		finalPredicate = criteriaBuilder.and(platformsPredicate); 
//				            	}
//				            }
				            
				            if(!StringUtils.isEmpty(startDate)) {
				            	Date date1 = format1.parse(startDate);
				            	String date3 = formatter.format(date1);
				            	Date start = formatter.parse(date3);
				            	Predicate startPredicate = criteriaBuilder.greaterThanOrEqualTo(root.<Date>get("pk").get("Actiontime"),start);
				            	if(finalPredicate!=null) 
				            		finalPredicate = criteriaBuilder.and(finalPredicate,startPredicate);
				            	else
				            		finalPredicate = criteriaBuilder.and(startPredicate); 
				            }
				            if(!StringUtils.isEmpty(endDate)) {
				            	Date date2 = format1.parse(endDate);
				            	String date4= formatter.format(date2);
				            	Date end = formatter.parse(date4);
				            	Predicate startPredicate = criteriaBuilder.lessThanOrEqualTo(root.<Date>get("pk").get("Actiontime"), end);
				            	if(finalPredicate!=null) 
				            		finalPredicate = criteriaBuilder.and(finalPredicate,startPredicate);
				            	else
				            		finalPredicate = criteriaBuilder.and(startPredicate); 
				            }						
					
		           
		            
		            Order proTimeOrder = criteriaBuilder.desc(root.get("systime"));
		            query.orderBy(proTimeOrder);
		            
				} catch (java.text.ParseException e) {
					e.printStackTrace();
				}catch (Exception e) {
				}
	            
				return finalPredicate;
			}
		};
	}

}
