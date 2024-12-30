package com.alfaris.ipsh.liquidity.repository.specification;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import com.alfaris.ipsh.liquidity.entity.PshSamLiqRep;
import com.alfaris.ipsh.liquidity.util.Constants;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public class SamaLiquidityReportSpec {
	
	public static Specification<PshSamLiqRep> getRecordsBySearchSpec(String searchParam) {
		return new Specification<PshSamLiqRep>() {
			@Override
			public Predicate toPredicate(Root<PshSamLiqRep> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				Predicate finalPredicate = null;
				JSONParser parser = new JSONParser();
				JSONObject searchObject;
				SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
				SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");
				try {
					if (!StringUtils.isEmpty(searchParam)) {
						
						searchObject = (JSONObject) parser.parse(searchParam);
						String dateSearch = (String) searchObject.get(Constants.SAMA_LIQUIDITY_REPORT.REPORT_GENERATED_DATE );
						String yearSearch = (String) searchObject.get(Constants.SAMA_LIQUIDITY_REPORT.REPORT_GENERATED_YEAR );

						if (!StringUtils.isEmpty(dateSearch)) {
							Date date = format.parse(dateSearch);
							Predicate toSALIRE = criteriaBuilder.equal(root.get(Constants.SAMA_LIQUIDITY_REPORT.REPORT_GENERATED_DATE),date);
							if (finalPredicate != null)
								finalPredicate = criteriaBuilder.and(finalPredicate, toSALIRE);
							else
								finalPredicate = criteriaBuilder.and(toSALIRE);
						}
						if (!StringUtils.isEmpty(yearSearch)) {
							Date year = yearFormat.parse(yearSearch);
							Predicate toSALIRE = criteriaBuilder.greaterThanOrEqualTo(root.get(Constants.SAMA_LIQUIDITY_REPORT.REPORT_GENERATED_DATE),year);
							if (finalPredicate != null)
								finalPredicate = criteriaBuilder.and(finalPredicate, toSALIRE);
							else
								finalPredicate = criteriaBuilder.and(toSALIRE);
						}
						if (!StringUtils.isEmpty(yearSearch)) {
							Date year = yearFormat.parse(yearSearch);
							year.setMonth(12);
							Predicate toSALIRE = criteriaBuilder.lessThan(root.get(Constants.SAMA_LIQUIDITY_REPORT.REPORT_GENERATED_DATE),year);
							if (finalPredicate != null)
								finalPredicate = criteriaBuilder.and(finalPredicate, toSALIRE);
							else
								finalPredicate = criteriaBuilder.and(toSALIRE);
							
						}
						query.orderBy(criteriaBuilder.desc(root.get(Constants.SAMA_LIQUIDITY_REPORT.REPORT_ID)));
						
					}
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (java.text.ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return finalPredicate;
				
			}
			
		};
		}
	}
