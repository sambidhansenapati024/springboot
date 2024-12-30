package com.alfaris.ipsh.authservice.repository.specification;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import com.alfaris.ipsh.authservice.entity.PshCasHry;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Order;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public class CaseAuditSpecification {
	private static Logger logger = LogManager.getLogger(CaseAuditSpecification.class);

	private CaseAuditSpecification() {

	}

	public static Specification<PshCasHry> getSearchSpecification(String searchParam) {
		return new Specification<PshCasHry>() {
			@Override
			public Predicate toPredicate(Root<PshCasHry> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				Predicate finalPredicate = null;
				JSONParser parser = new JSONParser();
				JSONObject searchObject;
				try {
					if (!StringUtils.isEmpty(searchParam)) {
					searchObject = (JSONObject) parser.parse(searchParam);
					if (searchObject != null) {
						String actionStatus = (String) searchObject.get("actionStatus");
						String actionDescription = (String) searchObject.get("actionDescription");
						JSONObject id = (JSONObject) searchObject.get("id");
						String functionId = null;
						String screenId = null;
						String userId = null;
						if (id != null) {
							functionId = (String) id.get("functionId");
							screenId = (String) id.get("screenId");
							userId = (String) id.get("userId");
						}

						if (!StringUtils.isEmpty(actionStatus)) {
							Predicate actionStatusPredicate = criteriaBuilder.like(criteriaBuilder.upper(root.get("actionStatus")), "%" + actionStatus.toUpperCase() + "%");
							finalPredicate = criteriaBuilder.and(actionStatusPredicate);
						}

						if (!StringUtils.isEmpty(actionDescription)) {
							Predicate actionDescriptionPredicate = criteriaBuilder.like(criteriaBuilder.upper(root.get("actionDescription")),
									"%" + actionDescription.toUpperCase() + "%");
							if(finalPredicate!=null) {
								finalPredicate = criteriaBuilder.and(finalPredicate, actionDescriptionPredicate);
							}else {
								finalPredicate = criteriaBuilder.and(actionDescriptionPredicate);
							}
						}

						if (!StringUtils.isEmpty(functionId)) {
							Predicate functionIdPredicate = criteriaBuilder.equal(root.get("id").get("functionId"),
									functionId);
							if(finalPredicate!=null) {
								finalPredicate = criteriaBuilder.and(finalPredicate, functionIdPredicate);
							}else {
								finalPredicate = criteriaBuilder.and(functionIdPredicate);
							}
						}

						if (!StringUtils.isEmpty(screenId)) {
							Predicate screenIdPredicate = criteriaBuilder.like(criteriaBuilder.upper(root.get("id").get("screenId")), "%" + screenId.toUpperCase() + "%");
							if(finalPredicate!=null) {
								finalPredicate = criteriaBuilder.and(finalPredicate, screenIdPredicate);
							}else {
								finalPredicate = criteriaBuilder.and(screenIdPredicate);
							}
						}

						if (!StringUtils.isEmpty(userId)) {
							Predicate userIdPredicate = criteriaBuilder.like(criteriaBuilder.upper(root.get("id").get("userId")), "%" + userId.toUpperCase() + "%");
							if(finalPredicate!=null) {
								finalPredicate = criteriaBuilder.and(finalPredicate, userIdPredicate);
							}else {
								finalPredicate = criteriaBuilder.and(userIdPredicate);
							}
						}
					}
				}
				}catch (Exception e) {
					logger.error("Error : " + e.getMessage(), e);
				}
				Order proTimeOrder = criteriaBuilder.desc(root.get("id").get("dateTime"));
				query.orderBy(proTimeOrder);

				return finalPredicate;
			}
		};
	}
}
