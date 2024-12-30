package com.alfaris.ipsh.liquidity.repository.specification;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import com.alfaris.ipsh.liquidity.entity.PshLmsCol;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;



public class SamaCollateralSpecs {
	
	private static Logger logger = LogManager.getLogger(SamaCollateralSpecs.class);

	private SamaCollateralSpecs() {

	}

	public static Specification<PshLmsCol> getRecordsBySearchSpec(String searchParam) {
		return new Specification<PshLmsCol>() {
			@Override
			public Predicate toPredicate(Root<PshLmsCol> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
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
						String productId = (String) searchObject.get("productId");
						String dateRange = (String) searchObject.get("valueDate");

						
						if (!StringUtils.isEmpty(dateRange)) {
							date = dateRange.split("-");
							startDate = date[0].trim();
							endDate = date[1].trim();
						}

						

						if (!StringUtils.isEmpty(productId)) {
							Predicate serviceIdPredicate = criteriaBuilder.equal(
									criteriaBuilder.upper(root.get("id").get("productId")), productId.toUpperCase());
							if (finalPredicate != null)
								finalPredicate = criteriaBuilder.and(finalPredicate, serviceIdPredicate);
							else
								finalPredicate = criteriaBuilder.and(serviceIdPredicate);
						}
						
						
			
						if (!StringUtils.isEmpty(startDate)) {

							Date start = format1.parse(startDate);
							Predicate startPredicate = criteriaBuilder.greaterThanOrEqualTo(root.get("id").get("valueDate"),
									start);
							if (finalPredicate != null)
								finalPredicate = criteriaBuilder.and(finalPredicate, startPredicate);
							else
								finalPredicate = criteriaBuilder.and(startPredicate);
						}
						if (!StringUtils.isEmpty(endDate)) {

							Date end = format1.parse(endDate);
							Predicate startPredicate = criteriaBuilder.lessThanOrEqualTo(root.get("id").get("valueDate"),
									end);
							if (finalPredicate != null)
								finalPredicate = criteriaBuilder.and(finalPredicate, startPredicate);
							else
								finalPredicate = criteriaBuilder.and(startPredicate);
						}

						
					}
					query.orderBy(criteriaBuilder.desc(root.get("id").get("valueDate")));
				} catch (Exception e) {
					logger.error("Error : " + e.getMessage(), e);
				}

				return finalPredicate;
			}
		};
	}


}
