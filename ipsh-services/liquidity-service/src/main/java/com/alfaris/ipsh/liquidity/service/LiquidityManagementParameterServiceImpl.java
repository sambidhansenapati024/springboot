package com.alfaris.ipsh.liquidity.service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import com.alfaris.ipsh.liquidity.client.SecurityServiceFeignClient;
import com.alfaris.ipsh.liquidity.dto.ServiceResponse;
import com.alfaris.ipsh.liquidity.entity.PshLqmPrm;
import com.alfaris.ipsh.liquidity.entity.PshLqmPrmPK;
import com.alfaris.ipsh.liquidity.exception.RecordCreateException;
import com.alfaris.ipsh.liquidity.exception.RecordNotFoundException;
import com.alfaris.ipsh.liquidity.exception.RecordUpdateException;
import com.alfaris.ipsh.liquidity.repository.LiquidityManagementParameterRepository;
import com.alfaris.ipsh.liquidity.repository.ProductAndServiceRepository;
import com.alfaris.ipsh.liquidity.repository.specification.PshLqmPrmSpecs;
import com.alfaris.ipsh.liquidity.util.Constants;

import jakarta.validation.Validator;

@Service
public class LiquidityManagementParameterServiceImpl implements LiquidityManagementParameterService {

	private static Logger logger = LogManager.getLogger(LiquidityManagementParameterServiceImpl.class);

	@Autowired
	ProductAndServiceRepository ProductAndServiceRepository;

	@Autowired
	SecurityServiceFeignClient SecurityServiceFeignClient;

	@Autowired
	LiquidityManagementParameterRepository repository;

	@Autowired
	MessageSource messageSource;

	@Autowired
	Validator validator;

	@Value("${bankId}")
	String bankId;

	@SuppressWarnings("unchecked")
	@Override
	public JSONObject searchByLimit(String searchParam, int start, int pageSize) {
		JSONObject result = new JSONObject();
		try {
			Pageable pageable = PageRequest.of(start / pageSize, pageSize);
			Page<PshLqmPrm> headerList = repository.findAll(PshLqmPrmSpecs.getRecordsBySearchSpec(searchParam),
					pageable);

			JSONArray countByStatus = countByStatus();
			JSONArray array = new JSONArray();
			for (PshLqmPrm entity : headerList) {
				JSONObject obj = new JSONObject();
				obj.put("bankId", entity.getId().getBankId());
				obj.put("productType", entity.getId().getProductType());
				obj.put("openingLimit", entity.getOpeningLimit());
				obj.put("currencyCode", entity.getId().getCurrencyCode());
				obj.put("prefundLimit", entity.getPrefundLimit());
				obj.put("limitAccount", entity.getLimitAccount());
				obj.put("nostroAccount", entity.getNostroAccount());
				obj.put("vostroAccount", entity.getVostroAccount());
				obj.put("status", entity.getStatus());
				obj.put("alertPercentage", entity.getAlertPercentage());
				obj.put("alertEmail", entity.getAlertEmail());
				obj.put("alertSms", entity.getAlertSms());
				obj.put("alertPercentagePayHold", entity.getAlertPercentagePayHold());
				obj.put("payHoldStatus", entity.getPayHoldStatus());
				array.add(obj);
			}

			result.put("aaData", array);
			result.put("iTotalDisplayRecords", headerList.getTotalElements());
			result.put("iTotalRecords", headerList.getTotalElements());
			result.put("countByStatus", countByStatus);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			result.put("aaData", new JSONArray());
			result.put("iTotalDisplayRecords", 0);
			result.put("iTotalRecords", 0);
			result.put("countByStatus", 0);
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	private JSONArray countByStatus() {
		JSONArray array = new JSONArray();
		try {
			List<PshLqmPrm> headerList = repository.findAll();
			Map<String, Long> countByStatus = headerList.stream()
					.collect(Collectors.groupingBy(PshLqmPrm::getStatus, Collectors.counting()));
			for (String status : countByStatus.keySet()) {
				JSONObject obj = new JSONObject();
				obj.put("name", status);
				obj.put("count", countByStatus.get(status));
				array.add(obj);
			}
		} catch (Exception e) {
			logger.error("Error:" + e.getMessage(), e);
		}
		return array;
	}

	@Override
	public List<String> getServiceIds(Principal principal) {
		try {
//			String bankId = SecurityServiceFeignClient.getBankId(principal.getName());
			return ProductAndServiceRepository.getServiceIds();
		} catch (Exception e) {
			logger.error("Error : " + e.getMessage(), e);
			return new ArrayList<String>();
		}
	}

//	@SuppressWarnings("unchecked")
//	private JSONObject createValidation(Map<Integer, PshLqmPrm> dataObj) {
//		JSONObject validationMessages = new JSONObject();
//		// Field Validation
//		for (Map.Entry<Integer, PshLqmPrm> entry : dataObj.entrySet()) {
//		    Set<ConstraintViolation<PshLqmPrm>> violations = validator.validate(entry.getValue());
//		    if (!violations.isEmpty()) {
//		    	JSONObject errorData = new JSONObject();
//				for (ConstraintViolation<PshLqmPrm> violation : violations) {
//					String key = violation.getPropertyPath().toString();
//					if (key.contains("id.")) {
//						key = key.split("\\.")[1];
//					}
//					errorData.put(key, messageSource.getMessage(violation.getMessage(), null, LocaleContextHolder.getLocale()));
//				}
//				validationMessages.put(entry.getKey(), errorData);
//		    } 
//		}
//		return validationMessages;
//	}

//	@SuppressWarnings("unchecked")
//	private JSONObject duplicateKeyValidation(Map<Integer, PshLqmPrm> dataObj) {
//		JSONObject validationMessages = new JSONObject();
//		// Composite keys duplicate check
//		Map<String, ArrayList<Integer>> duplicateMap = new HashMap<>();
//		ArrayList<Integer> tempList;
//        for (Map.Entry<Integer, PshLqmPrm> entry : dataObj.entrySet()) {
//        	String id = entry.getValue().getId().toString();
//            if (duplicateMap.containsKey(id)) {
//            	tempList = duplicateMap.get(id);
//            	tempList.add(entry.getKey());
//                duplicateMap.put(id, tempList);
//            } else {
//            	tempList = new ArrayList<Integer>();
//            	tempList.add(entry.getKey());
//                duplicateMap.put(id, tempList);
//            }
//        }
//        for (Map.Entry<String, ArrayList<Integer>> entry : duplicateMap.entrySet()) {
//            ArrayList<Integer> value = entry.getValue();
//            if (value.size() > 1) {
//            	Boolean flag = true;
//            	for (Integer index : value) {
//            		if (flag) {
//            			flag = false;
//            			continue;
//            		}
//            		JSONObject errorData = new JSONObject();
//            		errorData.put("compositeKeyError", messageSource.getMessage("liqdtyMangParam.details.psh.0017", null, LocaleContextHolder.getLocale()));
//            		validationMessages.put(index, errorData);
//            	}
//            }
//        }
//		return validationMessages;
//	}

//	@SuppressWarnings("unchecked")
//	private JSONObject dataBaseValidation(Map<Integer, PshLqmPrm> dataObj) {
//		JSONObject validationMessages = new JSONObject();
//		// Data with same key already exist
//		for (Map.Entry<Integer, PshLqmPrm> entry : dataObj.entrySet()) {
//			PshLqmPrmPK entityPk = entry.getValue().getId();
//			entityPk.setBankId(bankId);
//			Optional<PshLqmPrm> result = repository.findById(entityPk);
//			if (result.isPresent()) {
//				JSONObject errorData = new JSONObject();
//        		errorData.put("compositeKeyError", messageSource.getMessage("liqdtyMangParam.details.psh.0012", null, LocaleContextHolder.getLocale()));
//        		validationMessages.put(entry.getKey(), errorData);
//			}
//		}
//		return validationMessages;
//	}

	@Override
	public ServiceResponse create(PshLqmPrm entity, Principal principal) throws RecordCreateException {
		try {

			entity.getId().setBankId(bankId);
			Optional<PshLqmPrm> result = repository.findById(entity.getId());
			if (result.isPresent()) {
				throw new RecordCreateException("liqdtyMangParam.details.psh.0012");
			}
			entity.setStatus(Constants.MESSAGE_STATUS.PROCESSED);
			entity.setCreatedBy(getUserName());
			entity.setCreatedTime(new Date());
			entity.setModifiedBy(getUserName());
			entity.setModifiedTime(new Date());
			repository.save(entity);
			return new ServiceResponse(Constants.MESSAGE_STATUS.SUCCESS,
					messageSource.getMessage("liqdtyMangParam.details.psh.0001", null, LocaleContextHolder.getLocale()),
					null);
		} catch (RecordCreateException e) {
			throw e;
		} catch (Exception e) {
			logger.error("Error:" + e.getMessage(), e);
			return new ServiceResponse(Constants.MESSAGE_STATUS.FAILED,
					messageSource.getMessage("liqdtyMangParam.details.psh.0002", null, LocaleContextHolder.getLocale()),
					null);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public JSONObject getDataById(String bankId, String productType, String currencyCode)
			throws RecordNotFoundException {
		JSONObject response = new JSONObject();
		try {

			PshLqmPrmPK objPK = new PshLqmPrmPK();
			objPK.setBankId(bankId);
			objPK.setProductType(productType);
			objPK.setCurrencyCode(currencyCode);
			Optional<PshLqmPrm> result = repository.findById(objPK);
			if (result.isPresent()) {
				PshLqmPrm entity = result.get();
				response.put("id", objPK);
				response.put("prefundLimit", entity.getPrefundLimit());
				response.put("openingLimit", entity.getOpeningLimit());
				response.put("limitAccount", entity.getLimitAccount());
				response.put("nostroAccount", entity.getNostroAccount());
				response.put("vostroAccount", entity.getVostroAccount());
				response.put("alertPercentage", entity.getAlertPercentage());
				response.put("alertEmail", entity.getAlertEmail());
				response.put("alertSms", entity.getAlertSms());
				response.put("status", entity.getStatus());
				response.put("alertPercentagePayHold", entity.getAlertPercentagePayHold());
				response.put("payHoldStatus", entity.getPayHoldStatus());
				return response;
			} else {
				throw new RecordNotFoundException("liqdtyMangParam.details.psh.0009");
			}
		} catch (Exception e) {
			logger.error("Error : " + e.getMessage(), e);
		}
		return response;
	}

	@Override
	public ServiceResponse update(PshLqmPrm entity, Principal principal)
			throws RecordUpdateException, RecordNotFoundException {
		try {
			Optional<PshLqmPrm> result = repository.findById(entity.getId());
			if (!result.isPresent()) {
				throw new RecordNotFoundException("liqdtyMangParam.details.psh.0009");
			}
			PshLqmPrm newEntity = result.get();
			BeanUtils.copyProperties(entity, newEntity);
			newEntity.setStatus(Constants.MESSAGE_STATUS.PROCESSED);
			newEntity.setModifiedBy(getUserName());
			newEntity.setModifiedTime(new Date());
			repository.save(newEntity);
			return new ServiceResponse(Constants.MESSAGE_STATUS.SUCCESS,
					messageSource.getMessage("liqdtyMangParam.details.psh.0003", null, LocaleContextHolder.getLocale()),
					null);
		} catch (RecordNotFoundException e) {
			throw e;
		} catch (Exception e) {
			logger.error("Error:" + e.getMessage(), e);
			return new ServiceResponse(Constants.MESSAGE_STATUS.FAILED,
					messageSource.getMessage("liqdtyMangParam.details.psh.0004", null, LocaleContextHolder.getLocale()),
					null);
		}
	}

	@Override
	public ServiceResponse verify(PshLqmPrmPK entityPk, Principal principal) throws RecordNotFoundException {
		try {
			Optional<PshLqmPrm> result = repository.findById(entityPk);
			if (!result.isPresent()) {
				throw new RecordNotFoundException("liqdtyMangParam.details.psh.0009");
			}
			PshLqmPrm entity = result.get();
			if (entity.getStatus().contentEquals(Constants.MESSAGE_STATUS.VERIFIED)) {
				return new ServiceResponse(Constants.MESSAGE_STATUS.FAILED, messageSource
						.getMessage("liqdtyMangParam.details.psh.0013", null, LocaleContextHolder.getLocale()), null);
			}
			if (entity.getStatus().contentEquals(Constants.MESSAGE_STATUS.DELETED)) {
				return new ServiceResponse(Constants.MESSAGE_STATUS.FAILED, messageSource
						.getMessage("liqdtyMangParam.details.psh.0014", null, LocaleContextHolder.getLocale()), null);
			}
			if (entity.getModifiedBy().equalsIgnoreCase(getUserName())) {
				return new ServiceResponse(Constants.MESSAGE_STATUS.FAILED, messageSource
						.getMessage("liqdtyMangParam.details.psh.0008", null, LocaleContextHolder.getLocale()), null);
			}
			entity.setStatus(Constants.MESSAGE_STATUS.VERIFIED);
			entity.setVerifiedBy(getUserName());
			entity.setVerifiedTime(new Date());
			repository.save(entity);
			return new ServiceResponse(Constants.MESSAGE_STATUS.SUCCESS,
					messageSource.getMessage("liqdtyMangParam.details.psh.0007", null, LocaleContextHolder.getLocale()),
					null);
		} catch (RecordNotFoundException e) {
			throw e;
		} catch (Exception e) {
			logger.error("Error:" + e.getMessage(), e);
			return new ServiceResponse(Constants.MESSAGE_STATUS.FAILED,
					messageSource.getMessage("liqdtyMangParam.details.psh.0015", null, LocaleContextHolder.getLocale()),
					null);
		}
	}

	@Override
	public ServiceResponse delete(PshLqmPrmPK entityPk, Principal principal) throws RecordNotFoundException {
		try {
			Optional<PshLqmPrm> result = repository.findById(entityPk);
			if (!result.isPresent()) {
				throw new RecordNotFoundException("liqdtyMangParam.details.psh.0009");
			}
			PshLqmPrm entity = result.get();
			if (entity.getStatus().contentEquals(Constants.MESSAGE_STATUS.DELETED)) {
				return new ServiceResponse(Constants.MESSAGE_STATUS.FAILED, messageSource
						.getMessage("liqdtyMangParam.details.psh.0016", null, LocaleContextHolder.getLocale()), null);
			}
			entity.setStatus(Constants.MESSAGE_STATUS.DELETED);
			repository.save(entity);
			return new ServiceResponse(Constants.MESSAGE_STATUS.SUCCESS,
					messageSource.getMessage("liqdtyMangParam.details.psh.0005", null, LocaleContextHolder.getLocale()),
					null);
		} catch (Exception e) {
			logger.error("Error:" + e.getMessage(), e);
			return new ServiceResponse(Constants.MESSAGE_STATUS.FAILED,
					messageSource.getMessage("liqdtyMangParam.details.psh.0006", null, LocaleContextHolder.getLocale()),
					null);
		}
	}

	private String getUserName() {
		Jwt p = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Map<String, Object> claims = p.getClaims();
		return (String) claims.get("preferred_username");
	}

}
