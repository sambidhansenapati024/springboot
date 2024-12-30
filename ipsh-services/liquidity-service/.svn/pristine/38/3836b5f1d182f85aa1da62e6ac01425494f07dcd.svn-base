package com.alfaris.ipsh.liquidity.service;

import java.io.Console;
import java.security.Principal;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
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

import com.alfaris.ipsh.liquidity.client.AuditLogUtil;
import com.alfaris.ipsh.liquidity.dto.PshLmsColDto;
import com.alfaris.ipsh.liquidity.dto.PshLmsColPkVerifyDto;
import com.alfaris.ipsh.liquidity.dto.ServiceResponse;
import com.alfaris.ipsh.liquidity.entity.PshLmsCol;
import com.alfaris.ipsh.liquidity.entity.PshLmsColPK;
import com.alfaris.ipsh.liquidity.exception.RecordCreateException;
import com.alfaris.ipsh.liquidity.exception.RecordNotFoundException;
import com.alfaris.ipsh.liquidity.repository.SamaCollateralRepository;
import com.alfaris.ipsh.liquidity.repository.specification.SamaCollateralSpecs;
import com.alfaris.ipsh.liquidity.util.AuditFuctions;
import com.alfaris.ipsh.liquidity.util.Constants;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@Service
public class SamaCollateralServiceImpl implements SamaCollateralService {
	public static final Logger logger = LogManager.getLogger(SamaCollateralServiceImpl.class.getName());

	@Autowired
	SamaCollateralRepository repository;

	@Autowired
	MessageSource messageSource;

	@Autowired
	AuditLogUtil auditLogUtil;

	@Value("${productIDs}")
	String productIDs;

	@Override
	public JSONObject searchByLimit(String searchParam, int start, int pageSize) {
		JSONObject result = new JSONObject();
		try {
			SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
			SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSSSS");

			DateFormat dateTimeFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
			Pageable pageable = PageRequest.of(start / pageSize, pageSize);
			Page<PshLmsCol> headerList = repository.findAll(SamaCollateralSpecs.getRecordsBySearchSpec(searchParam),
					pageable);
			JSONArray countByStatus = countByStatus();
			JSONArray array = new JSONArray();
			int index = start;
			for (PshLmsCol entity : headerList) {
				JSONObject obj = new JSONObject();
				obj.put("bankidId", entity.getId().getBankidId());
				obj.put("productId", entity.getId().getProductId());
				obj.put("valueDate", entity.getId().getValueDate());
				obj.put("status", entity.getStatus());
				obj.put("samaReserve", entity.getSamaReserve());
				obj.put("colPldgSama", entity.getColPldgSama());
				obj.put("colPldsAnci", entity.getColPldsAnci());
				obj.put("unLiquidAssets", entity.getUnLiquidAssets());
				obj.put("securedCrline", entity.getSecuredCrline());
				obj.put("committedCrline", entity.getCommittedCrline());
				obj.put("others", entity.getOthers());
				obj.put("total", entity.getTotal());

				obj.put("timeStamp", formatter1.format(entity.getId().getTimeStamp()));

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

	private JSONArray countByStatus() {
		JSONArray array = new JSONArray();
		try {
			List<PshLmsCol> headerList = repository.findAll();
			Map<String, Long> countByStatus = headerList.stream()
					.collect(Collectors.groupingBy(PshLmsCol::getStatus, Collectors.counting()));
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
	public ServiceResponse create(PshLmsColDto pshLmsColDto, Principal principal) throws RecordCreateException {
		PshLmsColPK pshLmsColPK = new PshLmsColPK();
		DateFormat timeFormat2 = new SimpleDateFormat("dd-MM-yyyy");
		PshLmsCol entity = new PshLmsCol();
		String newAuditLogData = null;
		String oldAuditLogData = null;

		try {
//			String userDep = userRepository.getUserDept(getUserName());
			BeanUtils.copyProperties(pshLmsColDto, entity);
			pshLmsColPK.setBankidId("VBAKSARIXXX");
			pshLmsColPK.setProductId(pshLmsColDto.getProductId());
			pshLmsColPK.setTimeStamp(new Date());

			pshLmsColPK.setValueDate(timeFormat2.parse(pshLmsColDto.getValueDate()));

			entity.setId(pshLmsColPK);
			Optional<PshLmsCol> pshLmsColData = repository.findById(entity.getId());
			if (pshLmsColData.isPresent()) {
				return new ServiceResponse(Constants.FAILED, messageSource.getMessage("samaCollateral.details.dba.0020",
						null, LocaleContextHolder.getLocale()), null);
			} else {
				entity.setSamaReserve(pshLmsColDto.getSamaReserve());
				entity.setColPldgSama(pshLmsColDto.getColPldgSama());
				entity.setColPldsAnci(pshLmsColDto.getColPldsAnci());
				entity.setUnLiquidAssets(pshLmsColDto.getUnLiquidAssets());
				entity.setSecuredCrline(pshLmsColDto.getSecuredCrline());
				entity.setCommittedCrline(pshLmsColDto.getCommittedCrline());
				entity.setBalWithOthrBnks(pshLmsColDto.getBalWithOthrBnks());
				entity.setTotal(pshLmsColDto.getColPldgSama()
								.add(pshLmsColDto.getColPldsAnci()
								.add(pshLmsColDto.getCommittedCrline()
								.add(pshLmsColDto.getOthers()
								.add(pshLmsColDto.getSamaReserve()
								.add(pshLmsColDto.getUnLiquidAssets()
								.add(pshLmsColDto.getSecuredCrline()
								.add(pshLmsColDto.getBalWithOthrBnks())
								)))))));
				entity.setTotalCrline(pshLmsColDto.getCommittedCrline()
								.add(pshLmsColDto.getSecuredCrline()));
				entity.setStatus("PROCESSD");

				entity.setOthers(pshLmsColDto.getOthers());

				entity.setModifiedBy(getUserName());
				entity.setModifiedTime(new Date());

				entity = repository.save(entity);
//			notificationService.sendNotification(entity,"C","V",userDep);

				newAuditLogData = convertToJson(entity);
				newAuditLogData = setAuditData(newAuditLogData);

				if (entity != null) {
					auditLogUtil.creatAudit(oldAuditLogData, newAuditLogData, AuditFuctions.CREATE, "ALMSYS",
							Constants.MESSAGE_STATUS.SUCCESS,
							messageSource.getMessage("samaCollateral.details.psh.VAL0003", null, Locale.getDefault()),
							principal);
					return new ServiceResponse(Constants.SUCCESS, messageSource.getMessage(
							"samaCollateral.details.psh.VAL0003", null, LocaleContextHolder.getLocale()), null);
				} else {
					auditLogUtil.creatAudit(oldAuditLogData, newAuditLogData, AuditFuctions.MODIFY, "ALMSYS",
							"currency.spread.psh.0004",
							messageSource.getMessage("samaCollateral.details.psh.VAL0004", null, Locale.getDefault()),
							principal);
					return new ServiceResponse(Constants.FAILED, messageSource.getMessage(
							"samaCollateral.details.psh.VAL0004", null, LocaleContextHolder.getLocale()), null);
				}
			}
		} catch (Exception e) {
			logger.error("Error:" + e.getMessage(), e);
			return new ServiceResponse(Constants.FAILED,
					messageSource.getMessage("samaCollateral.details.dba.0012", null, LocaleContextHolder.getLocale()),
					null);
		}
	}

	String convertToJson(PshLmsCol entity) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			return mapper.writeValueAsString(entity);
		} catch (Exception e) {
			logger.error("Error : " + e.getMessage(), e);
		}
		return "";
	}

	public String setAuditData(String newData) {

		JsonObject obj1 = new JsonParser().parse(newData).getAsJsonObject();
		Timestamp timestamp1 = new Timestamp(Long.parseLong(obj1.get("modifiedTime").toString()));
		Date date1 = new Date(timestamp1.getTime());
		obj1.remove("modifiedTime");
		obj1.addProperty("modifiedTime", date1.toString());
		return obj1.toString();
	}

	@Override
	public ServiceResponse updatePayment(PshLmsColDto dto, Principal principal) throws RecordNotFoundException {
		PshLmsColPK pshLmsColPK = new PshLmsColPK();
		PshLmsCol entity = new PshLmsCol();
		DateFormat timeFormat2 = new SimpleDateFormat("yyyy-MM-dd");
		DateFormat timeFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSSSS");

		try {
			Date timestamptoDB = timeFormat1.parse(dto.getTimeStamp());
			Date valueDate = timeFormat2.parse(dto.getValueDate());
//			Date valueDate = dto.getValueDate();

			logger.info(">.>.>.>.>.>" + timestamptoDB);
			logger.info(">>>>>>" + valueDate);
			PshLmsColPK pk = new PshLmsColPK(dto.getBankidId(), dto.getProductId(), valueDate, timestamptoDB);
			Optional<PshLmsCol> pshLmsColData = repository.findById(pk);
			if (!pshLmsColData.isPresent()) {
				throw new RecordNotFoundException("samaCollateral.details.dba.0001");
			} else {
//			String userDep = userRepository.getUserDept(getUserName());
				PshLmsCol result = pshLmsColData.get();

				result.setSamaReserve(dto.getSamaReserve());
				result.setColPldgSama(dto.getColPldgSama());
				result.setColPldsAnci(dto.getColPldsAnci());
				result.setUnLiquidAssets(dto.getUnLiquidAssets());
				result.setSecuredCrline(dto.getSecuredCrline());
				result.setCommittedCrline(dto.getCommittedCrline());
				result.setOthers(dto.getOthers());
				result.setBalWithOthrBnks(dto.getBalWithOthrBnks());
				result.setTotal(
						dto.getColPldgSama()
						.add(dto.getColPldsAnci()
						.add(dto.getCommittedCrline()
						.add(dto.getOthers()
						.add(dto.getSamaReserve()
						.add(dto.getUnLiquidAssets()
						.add(dto.getSecuredCrline()
						.add(dto.getBalWithOthrBnks())
								)))))));
				result.setTotalCrline(dto.getCommittedCrline().add(dto.getSecuredCrline()));

				result.setModifiedBy(getUserName());
				result.setModifiedTime(new Date());

				repository.save(result);
//			notificationService.sendNotification(entity, "M", "V",userDep);

			}

			return new ServiceResponse(Constants.SUCCESS, messageSource.getMessage("samaCollateral.details.psh.VAL0088",
					null, LocaleContextHolder.getLocale()), null);

		} catch (RecordNotFoundException e) {
			return new ServiceResponse(Constants.FAILED,
					messageSource.getMessage("samaCollateral.details.dba.0001", null, LocaleContextHolder.getLocale()),
					null);
		} catch (Exception e) {
			logger.error("Error:" + e.getMessage(), e);
			return new ServiceResponse(Constants.FAILED,
					messageSource.getMessage("samaCollateral.details.dba.0012", null, LocaleContextHolder.getLocale()),
					null);
		}
	}

	@Override
	public ServiceResponse verifyRecord(PshLmsColPkVerifyDto pk, Principal principle) throws RecordNotFoundException {

		DateFormat timeFormat2 = new SimpleDateFormat("yyyy-MM-dd");
		DateFormat timeFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		try {
			Date timestamptoDB = timeFormat1.parse(pk.getTimeStamp());
			Date valueDate = timeFormat2.parse(pk.getValueDate());

			logger.info(">>>" + timestamptoDB);
			logger.info("..." + valueDate);

			Optional<PshLmsCol> entity = repository
					.findById(new PshLmsColPK(pk.getBankidId(), pk.getProductId(), valueDate, timestamptoDB));
			if (!entity.isPresent()) {
				throw new RecordNotFoundException("samaCollateral.details.dba.0001");
			}
			PshLmsCol details = entity.get();
			if (getUserName().equals(details.getModifiedBy())) {
				return new ServiceResponse("samaCollateral.details.dba.0010", messageSource
						.getMessage("samaCollateral.details.dba.0010", null, LocaleContextHolder.getLocale()), null);
			}
			if (details.getStatus().equalsIgnoreCase("VERIFIED")) {
				return new ServiceResponse(Constants.FAILED,
						messageSource.getMessage("samaCollateral.psh.008", null, LocaleContextHolder.getLocale()),
						null);
			}
			if (details.getStatus().equalsIgnoreCase("DELETED")) {
				return new ServiceResponse(Constants.FAILED,
						messageSource.getMessage("samaCollateral.psh.009", null, LocaleContextHolder.getLocale()),
						null);
			}

			else {

				details.setStatus(Constants.MESSAGE_STATUS.VERIFIED);
			}
			details.setModifiedBy(getUserName());
			details.setModifiedTime(new Date());
			repository.save(details);
//			notificationService.defferedNotification(details);
			// notificationService.defferedNotification(details);
			return new ServiceResponse(Constants.MESSAGE_STATUS.SUCCESS,
					messageSource.getMessage("samaCollateral.details.dba.0008", null, LocaleContextHolder.getLocale()),
					null);
		} catch (RecordNotFoundException e) {
			throw e;
		} catch (Exception e) {
			logger.error("Error:" + e.getMessage(), e);
			return new ServiceResponse("samaCollateral.details.dba.0012",
					messageSource.getMessage("samaCollateral.details.dba.0012", null, LocaleContextHolder.getLocale()),
					null);
		}
	}

	@Override
	public ServiceResponse delete(PshLmsColPkVerifyDto pk, Principal principle) throws RecordNotFoundException {
		DateFormat timeFormat2 = new SimpleDateFormat("yyyy-MM-dd");
		DateFormat timeFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		try {
			Date timestamptoDB = timeFormat1.parse(pk.getTimeStamp());
			Date valueDate = timeFormat2.parse(pk.getValueDate());

			Optional<PshLmsCol> acc = repository
					.findById(new PshLmsColPK(pk.getBankidId(), pk.getProductId(), valueDate, timestamptoDB));

			if (!acc.isPresent()) {
				throw new RecordNotFoundException("samaCollateral.psh.007");
			}
			PshLmsCol account = acc.get();
			if (account.getStatus().equals("DELETE") || account.getStatus().equals("DELETED")) {
				return new ServiceResponse(Constants.FAILED,
						messageSource.getMessage("samaCollateral.psh.016", null, LocaleContextHolder.getLocale()),
						null);
			}
//			notificationService.defferedNotification(account);
			account.setModifiedBy(getUserName());
			account.setModifiedTime(new Date());
			account.setStatus("DELETED");
//			account.setNotificationId(UUID.randomUUID().toString());
			repository.save(account);
//			notificationService.sendNotification(account, "D", "V", account.getDep());
//			notificationService.registerActivityInterBankPay(account, account.getDep(),
//					"interbankPay.activity.msg.005", 0,"M");
			return new ServiceResponse(Constants.SUCCESS,
					messageSource.getMessage("samaCollateral.psh.017", null, LocaleContextHolder.getLocale()), null);
		} catch (RecordNotFoundException e) {
			throw e;
		} catch (Exception e) {
			logger.error("Error" + e.getMessage(), e);
			return new ServiceResponse("samaCollateral.psh.005",
					messageSource.getMessage("bankBulk.psh.005", null, LocaleContextHolder.getLocale()), null);
		}
	}

	@Override
	public PshLmsColDto getSamaCollateral(String bankidId, String productId, String valueDate, String timeStamp,
			Principal principle) {
		PshLmsCol result = new PshLmsCol();
		PshLmsColDto result2 = new PshLmsColDto();
		DateFormat timeFormat2 = new SimpleDateFormat("yyyy-MM-dd");

		DateFormat timeFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSSSS");

		try {
			Date timeStampToDb = timeFormat1.parse(timeStamp);
			Date valueDateToDb = timeFormat2.parse(valueDate);
			Optional<PshLmsCol> data = repository.findById(new PshLmsColPK(bankidId, productId, valueDateToDb, timeStampToDb));
			if (data.isPresent()) {
				result = data.get();
				result2.setBankidId(result.getId().getBankidId());
				result2.setProductId(result.getId().getProductId());
				String valuedate = timeFormat2.format(result.getId().getValueDate());
				result2.setValueDate(valuedate);
				String timeStampst = timeFormat1.format(result.getId().getTimeStamp());
				result2.setTimeStamp(timeStampst);

				result2.setSamaReserve(result.getSamaReserve());
				result2.setColPldgSama(result.getColPldgSama());
				result2.setColPldsAnci(result.getColPldsAnci());
				result2.setUnLiquidAssets(result.getUnLiquidAssets());
				result2.setSecuredCrline(result.getSecuredCrline());
				result2.setCommittedCrline(result.getCommittedCrline());
				result2.setOthers(result.getOthers());
				result2.setBalWithOthrBnks(result.getBalWithOthrBnks());
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return result2;
	}

	@Override
	public List<String> getProductIds() {
		List<String> result = new ArrayList<String>();
		try {
			String[] dataList = productIDs.split(",");
			for (String data : dataList) {
				result.add(data);
			}
			return result;
		} catch (Exception e) {
			logger.error("Error : " + e.getMessage(), e);
			return new ArrayList<String>();
		}
	}
	
	private String getUserName() {
		Jwt p = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Map<String, Object> claims = p.getClaims();
		return (String) claims.get("preferred_username");
	}

}
