package com.alfaris.ipsh.liquidity.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.alfaris.ipsh.liquidity.entity.PshItrDet;
import com.alfaris.ipsh.liquidity.entity.PshOtrDet;
import com.alfaris.ipsh.liquidity.repository.PshItrDetRepository;
import com.alfaris.ipsh.liquidity.repository.PshOtrDetRepository;
import com.alfaris.ipsh.liquidity.repository.specification.PshItrDetSpecs;
import com.alfaris.ipsh.liquidity.repository.specification.PshOtrDetSpecs;

@Service
public class TransactionDetailsServiceImpl implements TransactionDetailsService {

	
	@Autowired
	PshItrDetRepository pshItrDetRepository;
	
	@Autowired
	PshOtrDetRepository pshOtrDetRepository;
	
	public static final Logger logger = LogManager.getLogger(TransactionDetailsServiceImpl.class.getName());

	
	@Override
	public JSONObject getIncomingTransaction(String searchParam, int start, int pageSize) {

		
		
		JSONObject result = new JSONObject();
		try {
			SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
			DateFormat dateTimeFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
			Pageable pageable = PageRequest.of(start / pageSize, pageSize);

			
			Page<PshItrDet> incomingList = pshItrDetRepository.findAll(PshItrDetSpecs.getRecordsBySearchSpec(searchParam),pageable);
			// long totalCount =
			// repository.count(PshOtrDetSpecs.getRecordsBySearchSpec(searchParam));
//			JSONArray countByStatus = countByStatusForIncoming();
			JSONArray incomingtTr = new JSONArray();
			for (PshItrDet entity : incomingList) {
				
				
				JSONObject obj = new JSONObject();
				obj.put("bankId", entity.getId().getBankId());
				obj.put("channelId", entity.getId().getChannelId());
				obj.put("pshReference", entity.getId().getPshReference());
				obj.put("externalRef", entity.getExternalRef());
				obj.put("customerReference", entity.getCustomerReference());
				obj.put("valueDate", formatter.format(entity.getValueDate()));
				obj.put("beneficiaryBankCode", entity.getBeneficiaryBankCode());
				obj.put("transactionAmount", entity.getTransactionAmount());
				obj.put("serviceId", entity.getServiceId());
				obj.put("status", entity.getStatus());
				obj.put("statusDcr", entity.getStatusDcr());
				obj.put("fromBank",entity.getOrderingInstitutionBic());
				obj.put("orderingCustomerAccount",entity.getOrderingCustomerAccount());
				obj.put("beneficiaryCustomerAccount",entity.getBeneficiaryCustomerAccount());
				obj.put("createdOn", dateTimeFormat.format(entity.getCreatedDate()));
				
				incomingtTr.add(obj);
			}
			
			result.put("aaData", incomingtTr);
			result.put("iTotalDisplayRecords", incomingList.getTotalElements());
			result.put("iTotalRecords", incomingList.getTotalElements());
			result.put("countByStatus", null);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			result.put("aaData", new JSONArray());
			result.put("iTotalDisplayRecords", 0);
			result.put("iTotalRecords", 0);
			result.put("countByStatus", 0);
		}
		
		
		return result;
		
	}
	
	
	@Override
	public JSONObject getOutgoingTransaction(String searchParam, int start, int pageSize) {

		
		
		JSONObject result = new JSONObject();
		try {
			SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
			DateFormat dateTimeFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
			Pageable pageable = PageRequest.of(start / pageSize, pageSize);
			Page<PshOtrDet> outgoingList = pshOtrDetRepository.findAll(PshOtrDetSpecs.getRecordsBySearchSpec(searchParam),pageable);

//			JSONArray countByStatus = countByStatusForOutgoing();
		
			JSONArray outgoingTr = new JSONArray();
			for (PshOtrDet entity : outgoingList) {
				JSONObject obj = new JSONObject();
				obj.put("bankId", entity.getId().getBankId());
				obj.put("channelId", entity.getId().getChannelId());
				obj.put("pshReference", entity.getId().getPshReference());
				obj.put("externalRef", entity.getExternalRef());
				obj.put("customerReference", entity.getCustomerReference());
				obj.put("valueDate", formatter.format(entity.getValueDate()));
				obj.put("beneficiaryBankCode", entity.getBeneficiaryBankCode());
				obj.put("transactionAmount", entity.getTransactionAmount());
				obj.put("serviceId", entity.getServiceId());
				obj.put("status", entity.getStatus());
				obj.put("statusDcr", entity.getStatusDcr());
				obj.put("relatedReference", entity.getRelatedReference());
				obj.put("transactionReference", entity.getEndToEndId());
				obj.put("createdOn", dateTimeFormat.format(entity.getCreDate()));
				
				outgoingTr.add(obj);
			}
	
			result.put("aaData", outgoingTr);
			result.put("iTotalDisplayRecords", outgoingList.getTotalElements());
			result.put("iTotalRecords", outgoingList.getTotalElements());
			result.put("countByStatus", null);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			result.put("aaData", new JSONArray());
			result.put("iTotalDisplayRecords", 0);
			result.put("iTotalRecords", 0);
			result.put("countByStatus", 0);
		}
		
		return result;
		
	}
	
	private JSONArray countByStatusForIncoming() {
		JSONArray array = new JSONArray();
		try {
			List<PshItrDet> headerList = pshItrDetRepository.findAll();
			Map<String, Long> countByStatus = headerList.stream()
					.collect(Collectors.groupingBy(PshItrDet::getStatus, Collectors.counting()));
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
	
	private JSONArray countByStatusForOutgoing() {
		JSONArray array = new JSONArray();
		try {
			List<PshOtrDet> headerList = pshOtrDetRepository.findAll();
			Map<String, Long> countByStatus = headerList.stream()
					.collect(Collectors.groupingBy(PshOtrDet::getStatus, Collectors.counting()));
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

}
