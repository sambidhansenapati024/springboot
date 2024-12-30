package com.alfaris.ipsh.subscription.repo.spec;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Order;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import com.alfaris.ipsh.subscription.entity.Subscription;
import com.alfaris.ipsh.subscription.utils.Constants;


public class SubscMangSpec {
	public static Specification<Subscription> getUserSpec(String searchParam) { 
		return new Specification<Subscription>() {
			@Override
			public Predicate toPredicate(Root<Subscription> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
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
						String uniqSubId = (String) searchObject.get("uniqSubId");
						String email = (String) searchObject.get("email");
						String userName = (String) searchObject.get("userName");
						String platforms = (String) searchObject.get("platforms");
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
				            
				            if(!StringUtils.isEmpty(uniqSubId)) {
				            	Predicate uniqSubIdPredicate = criteriaBuilder.like(root.get("pk").get("uniqSubId"),uniqSubId);
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
				            if(!StringUtils.isEmpty(platforms)) {
				            	Predicate platformsPredicate = criteriaBuilder.like(root.get("pk").get("platforms"),platforms);
				            	if(finalPredicate!=null) {
				            		finalPredicate = criteriaBuilder.and(finalPredicate, platformsPredicate);
				            	}else {
				            		finalPredicate = criteriaBuilder.and(platformsPredicate); 
				            	}
				            }
				            
				            if (!StringUtils.isEmpty(startDate)) {

								Date start = format1.parse(startDate);
								Predicate startPredicate = criteriaBuilder.greaterThanOrEqualTo(root.get("subscriptionDate"),
										start);
								if (finalPredicate != null)
									finalPredicate = criteriaBuilder.and(finalPredicate, startPredicate);
								else
									finalPredicate = criteriaBuilder.and(startPredicate);
							}
							if (!StringUtils.isEmpty(endDate)) {

								Date end = format1.parse(endDate);
								Predicate startPredicate = criteriaBuilder.lessThanOrEqualTo(root.get("subscriptionDate"),
										end);
								if (finalPredicate != null)
									finalPredicate = criteriaBuilder.and(finalPredicate, startPredicate);
								else
									finalPredicate = criteriaBuilder.and(startPredicate);
							}
						
					}
		           
		            
		            Order proTimeOrder = criteriaBuilder.desc(root.get("creationTime"));
		            query.orderBy(proTimeOrder);
		            
				} catch (ParseException | java.text.ParseException e) {
					e.printStackTrace();
				}catch (Exception e) {
				}
	            
				return finalPredicate;
			}
		};
	}
}
