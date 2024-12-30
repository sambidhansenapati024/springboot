package com.alfaris.ipsh.liquidity.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alfaris.ipsh.liquidity.entity.EftSinData;
import com.alfaris.ipsh.liquidity.entity.PshLmsDtr;
import com.alfaris.ipsh.liquidity.entity.PshLqmPrm;
import com.alfaris.ipsh.liquidity.entity.PshLqmPrmPK;
import com.alfaris.ipsh.liquidity.entity.PshRtgsSdi;
import com.alfaris.ipsh.liquidity.repository.EftSinDataRepository;
import com.alfaris.ipsh.liquidity.repository.LiquidityDetailRepository;
import com.alfaris.ipsh.liquidity.repository.LiquidityManagementParameterRepository;
import com.alfaris.ipsh.liquidity.repository.PshItrDetRepository;
import com.alfaris.ipsh.liquidity.repository.PshOtrDetRepository;
import com.alfaris.ipsh.liquidity.repository.PshRtgsSdiRepository;

@Service
public class DashBoardServiceImpl implements DashBoardService {

	@Autowired
	DateFormat dateFormatWithSpace;

	@Autowired
	DateFormat dateFormatReverse;

	@Autowired
	LiquidityDetailRepository liquidityDetailRepository;

	@Autowired
	LiquidityManagementParameterRepository liquidityManagementParameterRepository;

	@Autowired
	EftSinDataRepository eftSinDataRepository;
	
	@Autowired
	PshRtgsSdiRepository pshRtgsSdiRepository;

	@Autowired
	PshItrDetRepository pshItrDetRepository;

	@Autowired
	PshOtrDetRepository pshOtrDetRepository;

	@Autowired
	PshLqmPrmPK pshLqmPrmPk;

	private static Logger logger = LogManager.getLogger(DashBoardServiceImpl.class);

	@Override
	public JSONArray getGraphData() {
		JSONArray graphArray = new JSONArray();
		
		try {
			System.out.println(dateFormatReverse.format(new Date()));
			Date today = dateFormatWithSpace.parse(dateFormatReverse.format(new Date()) + " 00:00:00.000");
			Optional<List<PshLmsDtr>> data = liquidityDetailRepository.findTodaysGraphData(today);
			if (data.isPresent()) {
				System.out.println("data from db: " + data.get());
				List<PshLmsDtr> graphData = data.get();
				for (PshLmsDtr row : graphData) {
					JSONObject graphJson = new JSONObject();
					graphJson.put("x", dateFormatWithSpace.format(row.getTimeStamp()));
					graphJson.put("y", row.getNetAmt().setScale(2, RoundingMode.FLOOR));
					graphArray.add(graphJson);

				}

			} else {
				JSONObject graphJson1 = new JSONObject();

				graphJson1.put("x", dateFormatReverse.format(new Date()) + " 09:00:00.000");
				graphJson1.put("y", BigDecimal.ZERO.setScale(2, RoundingMode.FLOOR));
				graphArray.add(graphJson1);

				JSONObject graphJson2 = new JSONObject();
				graphJson2.put("x", dateFormatReverse.format(new Date()) + " 11:00:00.000");
				graphJson2.put("y", BigDecimal.ZERO.setScale(2, RoundingMode.FLOOR));
				graphArray.add(graphJson2);

				JSONObject graphJson3 = new JSONObject();
				graphJson3.put("x", dateFormatReverse.format(new Date()) + " 13:00:00.000");
				graphJson3.put("y", BigDecimal.ZERO.setScale(2, RoundingMode.FLOOR));
				graphArray.add(graphJson3);

				JSONObject graphJson4 = new JSONObject();
				graphJson4.put("x", dateFormatReverse.format(new Date()) + " 15:00:00.000");
				graphJson4.put("y", BigDecimal.ZERO.setScale(2, RoundingMode.FLOOR));
				graphArray.add(graphJson4);
				JSONObject graphJson5 = new JSONObject();
				graphJson5.put("x", dateFormatReverse.format(new Date()) + " 17:00:00.000");
				graphJson5.put("y", BigDecimal.ZERO.setScale(2, RoundingMode.FLOOR));
				graphArray.add(graphJson5);

			}
		} catch (Exception e) {
			logger.error("error: " + e.getMessage());
		}
		System.out.println(graphArray);
		return graphArray;
	}

	@SuppressWarnings("unchecked")
	@Override
	public JSONObject getDashBoardData() {
		JSONObject object = new JSONObject();
		try {
			String dateFormat = "dd-MM-yyyy";
			SimpleDateFormat day = new SimpleDateFormat(dateFormat);
			String sinData;
			String tag60F;
			BigDecimal openingBal;
			String tag65;
			BigDecimal forwardBal;
			String statusApplied = "APPLIED";
			BigDecimal totalIncoming;
			BigDecimal totalOutcoming;

			Date now = new Date();

			Calendar cal = Calendar.getInstance();
			cal.setTime(now);
			cal.set(Calendar.HOUR_OF_DAY, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			cal.set(Calendar.MILLISECOND, 0);
			Date todayDate = cal.getTime();
			String today = day.format(todayDate);

			String status = "PROCESSD";

			List<EftSinData> listOfSdi = eftSinDataRepository.findSdiByTodayandStatus(todayDate, status);
			
			Optional<PshRtgsSdi> balanceToday = pshRtgsSdiRepository.findByValueDate(todayDate);
			
			if( !balanceToday.isEmpty())
			{
				openingBal = balanceToday.get().getOpeningBalance();
				
				object.put("netAmount",openingBal);
				
				forwardBal = balanceToday.get().getForwardPosition();
				
				object.put("forwardBalance",forwardBal);

				
			}	
			else {
				object.put("netAmount", 0);
			}
				
			

//			if (!listOfSdi.isEmpty()) {
//				sinData = listOfSdi.get(0).getData();
//				tag60F = get60F(sinData);
//
//				openingBal = getAmountFromTag60F(tag60F);
//
//				openingBal = openingBal.replaceAll(",", ".");
//
//				object.put("netAmount", new BigDecimal(openingBal));
//
//			} else {
//				object.put("netAmount", 0);
//			}

//			if (!listOfSdi.isEmpty()) {
//				sinData = listOfSdi.get(0).getData();
//
//				tag65 = get65(sinData);
//
//				forwardBal = getAmountFromTag60F(tag65);
//
//				forwardBal = forwardBal.replaceAll(",", ".");
//
//				object.put("forwardBalance", new BigDecimal(forwardBal));
//
//			} else {
//				object.put("forwardBalance", 0);
//			}

			totalIncoming = pshItrDetRepository.getTotalIncoming(day.parse(today), statusApplied);
			logger.info("totalIncoming****" + totalIncoming);

			if (totalIncoming != null) {
				object.put("totalIncoming", totalIncoming);

			} else {
				object.put("totalIncoming", 0);
			}

			totalOutcoming = pshOtrDetRepository.getTotalOutcoming(day.parse(today), statusApplied);

			if (totalOutcoming != null) {
				object.put("totalOutcoming", totalOutcoming);

			} else {
				object.put("totalOutcoming", 0);
			}

			Optional<PshLqmPrm> pshLqmPrm = liquidityManagementParameterRepository.findById(pshLqmPrmPk);
			if (pshLqmPrm.isPresent()) {
				object.put("liquidity",
						pshLqmPrm.get().getPrefundLimit() != null ? pshLqmPrm.get().getPrefundLimit() : 0);
			} else {
				object.put("liquidity", 0);
			}

//			Optional<PshLmsDtr> pshLmsDtr = liquidityDetailRepository.findTopByOrderByTimeStampDesc();
//			Optional<List<BigDecimal>> listOut = liquidityDetailRepository.findLatestOutgoingAmount();
//			Optional<List<BigDecimal>> listInc = liquidityDetailRepository.findLatestIncomingAmount();
//			if(listOut.isPresent()) {
//				object.put("outgoingAmount", listOut.get().get(0));
//			}else {
//				object.put("outgoingAmount", 0);
//			}
//			if(listInc.isPresent()) {
//				object.put("incomingAmount", listInc.get().get(0));
//			}else {
//				object.put("incomingAmount", 0);
//			}

//			if(pshLmsDtr.isPresent()) {
//			
//				
//				object.put("netAmount", pshLmsDtr.get().getNetAmt()!=null ? pshLmsDtr.get().getNetAmt():0);
//			}else {
//				
//				
//				object.put("netAmount", 0);
//			}

		} catch (Exception e) {
			logger.error("error occured: " + e.getMessage());
		}
		return object;
	}

	private String get60F(String sinData) {
		int firstIndex = sinData.indexOf(":60F:");
		if (firstIndex != -1) {
			if (sinData.indexOf("\r\n") != -1) {
				int lastIndex = sinData.indexOf("\r\n", firstIndex);
				return sinData.substring(firstIndex + 4, lastIndex);
			} else if (sinData.indexOf("\n") != -1) {
				int lastIndex = sinData.indexOf("\n", firstIndex);
				return sinData.substring(firstIndex + 4, lastIndex);
			} else if (sinData.indexOf("\r") != -1) {
				int lastIndex = sinData.indexOf("\r", firstIndex);
				return sinData.substring(firstIndex + 4, lastIndex);
			} else {
				return "";
			}

		} else
			return "";
	}

	private String get65(String sinData) {
		int firstIndex = sinData.indexOf(":65:");
		if (firstIndex != -1) {
			if (sinData.indexOf("\r\n") != -1) {
				int lastIndex = sinData.indexOf("\r\n", firstIndex);
				return sinData.substring(firstIndex + 4, lastIndex);
			} else if (sinData.indexOf("\n") != -1) {
				int lastIndex = sinData.indexOf("\n", firstIndex);
				return sinData.substring(firstIndex + 4, lastIndex);
			} else if (sinData.indexOf("\r") != -1) {
				int lastIndex = sinData.indexOf("\r", firstIndex);
				return sinData.substring(firstIndex + 4, lastIndex);
			} else {
				return "";
			}

		} else
			return "";
	}

	private String getAmountFromTag60F(String tag60F) {
		int firstIndex = tag60F.indexOf("SAR");
		if (firstIndex != -1) {
			return tag60F.substring(firstIndex + 3, tag60F.length());
		} else
			return "";
	}

}
