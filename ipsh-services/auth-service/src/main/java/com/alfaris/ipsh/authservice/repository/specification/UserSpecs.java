package com.alfaris.ipsh.authservice.repository.specification;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import com.alfaris.ipsh.authservice.entity.Users;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public class UserSpecs {
	private static Logger logger = LogManager.getLogger(UserSpecs.class);

	private UserSpecs() {
		
	}
	public static Specification<Users> getEmployeesByNameSpec(String searchParam) {
		return new Specification<Users>() {
			@Override
			public Predicate toPredicate(Root<Users> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				Predicate finalPredicate = null;
				JSONParser parser = new JSONParser();
				JSONObject searchObject;
				try {
					searchObject = (JSONObject) parser.parse(searchParam);
					String status = (String) searchObject.get("status");
					String userName = (String) searchObject.get("userName");
					if (!StringUtils.isEmpty(status)) {
						Predicate statusPredicate = criteriaBuilder.equal(root.get("status"), status);
						finalPredicate = criteriaBuilder.and(statusPredicate);
					}

					if (!StringUtils.isEmpty(userName)) {
						Predicate userNamePredicate = criteriaBuilder.like(root.get("userName"), "%" + userName + "%");
						finalPredicate = criteriaBuilder.and(finalPredicate, userNamePredicate);
					}

				} catch (Exception e) {
					logger.error("Error : " + e.getMessage(), e);
				}

				return finalPredicate;
			}
		};
	}
}
