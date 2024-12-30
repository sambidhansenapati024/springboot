package com.alfaris.ipsh.kycservice.repo.spec;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Order;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import com.alfaris.ipsh.kycservice.entity.KycRequest;
import com.alfaris.ipsh.kycservice.utils.Constants;


public class KycUserSpec {
	public static Specification<KycRequest> getUserSpec(String searchParam) {
		return new Specification<KycRequest>() {
			@Override
			public Predicate toPredicate(Root<KycRequest> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				Predicate finalPredicate = null;
				JSONParser parser = new JSONParser();
				JSONObject searchObject;
				try {
					searchObject = (JSONObject) parser.parse(searchParam);
					String status = (String) searchObject.get(Constants.USER_STATUS);
					String userName = (String) searchObject.get(Constants.USER_USER_NAME);
					String email = (String) searchObject.get("email");
					String userId = (String) searchObject.get(Constants.USER_USER_ID);
		            if(!StringUtils.isEmpty(status)) {
		            	Predicate statusPredicate = criteriaBuilder.equal(root.get(Constants.USER_STATUS), status);
		            	finalPredicate = criteriaBuilder.and(statusPredicate);
		            }
		            
		            if(!StringUtils.isEmpty(userName)) {
		            	Predicate userNamePredicate = criteriaBuilder.like(criteriaBuilder.upper(root.get("userName")),"%"+userName.toUpperCase()+"%");
		            	if(finalPredicate!=null) {
		            		finalPredicate = criteriaBuilder.and(finalPredicate, userNamePredicate);
		            	}else {
		            		finalPredicate = criteriaBuilder.and(userNamePredicate);
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
		            
		            if(!StringUtils.isEmpty(userId)) {
		            	Predicate userIdPredicate = criteriaBuilder.like(criteriaBuilder.upper(root.get(Constants.USER_USER_ID)), "%"+userId.toUpperCase()+"%");
		            	if(finalPredicate!=null) {
		            		finalPredicate = criteriaBuilder.and(finalPredicate, userIdPredicate);
		            	}else {
		            		finalPredicate = criteriaBuilder.and(userIdPredicate);
		            	}
		            }
		            
		            Order proTimeOrder = criteriaBuilder.desc(root.get("createdAt"));
		            query.orderBy(proTimeOrder);
		            
				} catch (ParseException e) {
					e.printStackTrace();
				}
	            
				return finalPredicate;
			}
		};
	}
}
