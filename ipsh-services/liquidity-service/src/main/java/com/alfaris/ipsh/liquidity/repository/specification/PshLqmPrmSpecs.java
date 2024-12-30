package com.alfaris.ipsh.liquidity.repository.specification;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import com.alfaris.ipsh.liquidity.entity.PshLqmPrm;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public class PshLqmPrmSpecs {
	private static Logger logger = LogManager.getLogger(PshLqmPrmSpecs.class);

	public static Specification<PshLqmPrm> getRecordsBySearchSpec(String searchParam) {
		return new Specification<PshLqmPrm>() {
			private static final long serialVersionUID = 1L;

			@Override
			public Predicate toPredicate(Root<PshLqmPrm> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				Predicate finalPredicate = null;
				JSONParser parser = new JSONParser();
				JSONObject searchObject;
				try {
					if (!StringUtils.isEmpty(searchParam)) {
						searchObject = (JSONObject) parser.parse(searchParam);
						String serviceType = (String) searchObject.get("productType");
						String currencyCode = (String) searchObject.get("currencyCode");
						String limitAccount = (String) searchObject.get("limitAccount");
						String nostroAccount = (String) searchObject.get("nostroAccount");
						String vostroAccount = (String) searchObject.get("vostroAccount");
						String status = (String) searchObject.get("status");

						if (!StringUtils.isEmpty(serviceType)) {
							Predicate serviceTypePredicate = criteriaBuilder.equal(criteriaBuilder.upper(root.get("id").get("productType")), 
									serviceType.toUpperCase());
							finalPredicate = criteriaBuilder.and(serviceTypePredicate);
						}

						if (!StringUtils.isEmpty(currencyCode)) {
							Predicate currencyCodePredicate = criteriaBuilder.equal(criteriaBuilder.upper(root.get("id").get("currencyCode")),
									currencyCode.toUpperCase());
							if (finalPredicate != null)
								finalPredicate = criteriaBuilder.and(finalPredicate, currencyCodePredicate);
							else
								finalPredicate = criteriaBuilder.and(currencyCodePredicate);
						}

						if (!StringUtils.isEmpty(limitAccount)) {
							Predicate limitAccountPredicate = criteriaBuilder.like(criteriaBuilder.upper(root.get("limitAccount")), 
									"%" + limitAccount.toUpperCase() + "%");
							if (finalPredicate != null)
								finalPredicate = criteriaBuilder.and(finalPredicate, limitAccountPredicate);
							else
								finalPredicate = criteriaBuilder.and(limitAccountPredicate);
						}

						if (!StringUtils.isEmpty(nostroAccount)) {
							Predicate nostroAccountPredicate = criteriaBuilder.like(criteriaBuilder.upper(root.get("nostroAccount")), 
									"%" + nostroAccount.toUpperCase() + "%");
							if (finalPredicate != null)
								finalPredicate = criteriaBuilder.and(finalPredicate, nostroAccountPredicate);
							else
								finalPredicate = criteriaBuilder.and(nostroAccountPredicate);
						}

						if (!StringUtils.isEmpty(vostroAccount)) {
							Predicate vostroAccountPredicate = criteriaBuilder.like(criteriaBuilder.upper(root.get("vostroAccount")),
									"%" + vostroAccount.toUpperCase() + "%");
							if (finalPredicate != null)
								finalPredicate = criteriaBuilder.and(finalPredicate, vostroAccountPredicate);
							else
								finalPredicate = criteriaBuilder.and(vostroAccountPredicate);
						}

						if (!StringUtils.isEmpty(status)) {
							Predicate statusPredicate = criteriaBuilder.equal(criteriaBuilder.upper(root.get("status")), status.toUpperCase());
							if (finalPredicate != null)
								finalPredicate = criteriaBuilder.and(finalPredicate, statusPredicate);
							else
								finalPredicate = criteriaBuilder.and(statusPredicate);
						}
					}
					query.orderBy(criteriaBuilder.desc(root.get("modifiedTime")));
				} catch (Exception e) {
					logger.error("Error : " + e.getMessage(), e);
				}

				return finalPredicate;
			}
		};
	}

}
