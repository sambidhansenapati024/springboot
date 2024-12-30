package com.alfaris.ipsh.liquidity.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.alfaris.ipsh.liquidity.dto.ServiceResponse;
import com.alfaris.ipsh.liquidity.entity.PshSamLiqRep;
import com.alfaris.ipsh.liquidity.exception.RecordCreateException;
import com.alfaris.ipsh.liquidity.exception.RecordNotFoundException;
import com.alfaris.ipsh.liquidity.repository.LiquidityDetailRepository;
import com.alfaris.ipsh.liquidity.repository.LiquidityReportRepository;
import com.alfaris.ipsh.liquidity.repository.PshCurColRepository;
import com.alfaris.ipsh.liquidity.repository.SamaCollateralRepository;
import com.alfaris.ipsh.liquidity.repository.SamaLiquidityReportRepository;
import com.alfaris.ipsh.liquidity.repository.specification.SamaLiquidityReportSpec;
import com.alfaris.ipsh.liquidity.util.Constants;

@Service
public class SamaLiquidityReportServiceImpl implements SamaLiquidityReportService{
	
	public static final Logger logger = LogManager.getLogger(SamaLiquidityReportServiceImpl.class.getName());
	@Autowired
	SamaLiquidityReportRepository samaLiquidityReportRepository;
	
	
	@Autowired
	LiquidityDetailRepository liquidityDetailRepository;

	@Autowired
	SamaCollateralRepository samaCollateralRepository;

	@Autowired
	PshCurColRepository pshCurColRepository;
	
	
	@Autowired
	LiquidityReportRepository  liquidityReportRepository;
	
	@Autowired
	MessageSource messageSource;
	
	public JSONObject searchByLimit(String searchParam, int start, int pageSize) {
		JSONObject result = new JSONObject();
		try {
			SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
			SimpleDateFormat monthYear = new SimpleDateFormat("MMMMMM, yyyy");
			Pageable pageable = PageRequest.of(start / pageSize, pageSize);
			Page<PshSamLiqRep> headerList = samaLiquidityReportRepository.findAll(SamaLiquidityReportSpec.getRecordsBySearchSpec(searchParam),pageable);
			JSONArray array = new JSONArray();
			for (PshSamLiqRep entity : headerList) {
				JSONObject obj = new JSONObject();
				obj.put(Constants.SAMA_LIQUIDITY_REPORT.REPORT_ID, entity.getReportId());
				obj.put(Constants.SAMA_LIQUIDITY_REPORT.FILE_NAME, entity.getFileName());
				obj.put(Constants.SAMA_LIQUIDITY_REPORT.REPORT_GENERATED_DATE, formatter.format( entity.getReportGeneratedDate()));
				obj.put(Constants.SAMA_LIQUIDITY_REPORT.REPDOWN,entity.getReportId());
				obj.put(Constants.SAMA_LIQUIDITY_REPORT.REPORT_GENERATED_MONTH,monthYear.format( entity.getReportGeneratedDate()));
				obj.put(Constants.SAMA_LIQUIDITY_REPORT.REPORT_TYPE, entity.getReportType());
				array.add(obj);
			}

			result.put(Constants.AA_DATA, array);
			result.put(Constants.TOTAL_DISPLAY_RECORD, headerList.getTotalElements());
			result.put(Constants.TOTAL_RECORD, headerList.getTotalElements());

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			result.put(Constants.AA_DATA, new JSONArray());
			result.put(Constants.TOTAL_DISPLAY_RECORD, 0);
			result.put(Constants.TOTAL_RECORD, 0);
		}
		return result;
	}
	@Override
	public ResponseEntity<Object> getFile(int reportId) throws RecordNotFoundException{

		PshSamLiqRep salire = new PshSamLiqRep();
		Object obj = new Object();
		try {
			if(reportId == (int)reportId) {
				Optional<PshSamLiqRep>documentDetails = samaLiquidityReportRepository.findById(reportId);
				if(!documentDetails.isPresent()) {
//					throw new RecordNotFoundException("file Not Found");
					logger.info("Info : No sama liquidity report found for report ID : "+ reportId);
					return new ResponseEntity<Object>(obj, new HttpHeaders(), HttpStatus.NOT_FOUND );
				}
				salire = documentDetails.get();
				int firstIndex = salire.getFileName().indexOf(".");
				int lastIndex = salire.getFileName().length();
				String fileType = salire.getFileName().substring(firstIndex, lastIndex);
				
				byte[] bytes = salire.getFileContent();
				InputStreamResource resource = new InputStreamResource(new ByteArrayInputStream(bytes));
				HttpHeaders headers = new HttpHeaders();
				headers.add("Content-Disposition",String.format("attachment; filename=%s",salire.getFileName()));
				headers.add("Cache-Control","no-cache, no-store,must-revalidate");
				headers.add("Pragma","no-cache");
				headers.add("Expires","0");
				headers.add("Access-Control-Expose-Headers","Content-Disposition");
				ResponseEntity<Object> responseEntity  = ResponseEntity.ok()
						.headers(headers)
						.contentLength(bytes.length)
						.body(resource);	
				return responseEntity;
				
			} else {
//				throw new RecordNotFoundException("file Not Found");
				logger.info("Info : Empty Sama liquidity report ID in request");
				return new ResponseEntity<Object>(obj, new HttpHeaders(), HttpStatus.NOT_FOUND );
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return new ResponseEntity<Object>(obj, new HttpHeaders(), HttpStatus.NOT_FOUND );
		}
	}
	
	
	
	@Override
	public ServiceResponse doReport(String dateRange ) {
		
		try {
			
			Date endDate = new Date();
			Date startDate = new Date();
			Date endDateTemp = new Date();
			Date now = new Date();
			Calendar cal = Calendar.getInstance();
			cal.setTime(now);
			
			int todayDate = cal.get(Calendar.DAY_OF_MONTH);
			int month = cal.get(Calendar.MONTH)+1;
			int year = cal.get(Calendar.YEAR);
			
			//report cannot be generated on first day of month
//			if(todayDate==1) {
//				
//				return new ServiceResponse("FIRSTDAY",
//						messageSource.getMessage("samaLiquidityReport.details.psh.0003", null, LocaleContextHolder.getLocale()),
//						null);
//			}
			
			if(StringUtils.hasLength(dateRange)) {
				logger.info("=====>Date range received : "+dateRange);
				String start = dateRange.split("-")[0];
				String end = dateRange.split("-")[1];
				
				String startDay =start.split("/")[0];
				String startMonth =start.split("/")[1];
				String startYear =start.split("/")[2];
				
				String endDay =end.split("/")[0];
				String endMonth =end.split("/")[1];
				String endYear =end.split("/")[2];
				
//				cal.set(Calendar.DAY_OF_MONTH, Integer.parseInt(start));
//				cal.set(Calendar.HOUR_OF_DAY, 0);
//				cal.set(Calendar.MINUTE, 0);
//				cal.set(Calendar.SECOND, 0);
//				cal.set(Calendar.MILLISECOND, 0);
//				startDate = cal.getTime();
//				
//				cal.set(Calendar.DAY_OF_MONTH, Integer.parseInt(end)+1);
//				endDate = cal.getTime();
				
				/* Add validation for choosing start date and end date as day of same month */
				if(!startMonth.equalsIgnoreCase(endMonth)) {
					return new ServiceResponse("FIRSTDAY",
							messageSource.getMessage("samaLiquidityReport.details.psh.0004", null, LocaleContextHolder.getLocale()),
							null);
				}
				
				cal.set(Calendar.DAY_OF_MONTH, Integer.parseInt(startDay));
				cal.set(Calendar.MONTH, Integer.parseInt(startMonth)-1);
				cal.set(Calendar.YEAR, Integer.parseInt(startYear));
				cal.set(Calendar.HOUR_OF_DAY, 0);
				cal.set(Calendar.MINUTE, 0);
				cal.set(Calendar.SECOND, 0);
				cal.set(Calendar.MILLISECOND, 0);
				startDate = cal.getTime();
				
				cal.set(Calendar.DAY_OF_MONTH, Integer.parseInt(endDay));
				cal.set(Calendar.MONTH, Integer.parseInt(endMonth)-1);
				cal.set(Calendar.YEAR, Integer.parseInt(endYear));
				endDateTemp = cal.getTime();
//				endDate = cal.getTime();
				
				LocalDate localDate = endDateTemp.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		        LocalDate nextDay = localDate.plusDays(1);
		        endDate = Date.from(nextDay.atStartOfDay(ZoneId.systemDefault()).toInstant());
				
			}else {
				logger.info("=====> NO date range, generating for current month");
				cal.set(Calendar.DAY_OF_MONTH, 1);
				cal.set(Calendar.HOUR_OF_DAY, 0);
				cal.set(Calendar.MINUTE, 0);
				cal.set(Calendar.SECOND, 0);
				cal.set(Calendar.MILLISECOND, 0);
				startDate = cal.getTime();
				
				cal.set(Calendar.DAY_OF_MONTH, todayDate);
				endDate = cal.getTime();
				
			}
			logger.info("=====>Start date : "+startDate);
			logger.info("=====>End date : "+endDate);
//			long timeDifference = endDate.getTime() - startDate.getTime();
//			long noOfDays = timeDifference / (1000 * 60 * 60 * 24);
//			long totalNoOfDays = 1;
//			if(noOfDays>1) {
//				totalNoOfDays = (int) noOfDays;
//			}
			BigDecimal totalNoOfDays = BigDecimal.ONE;
			totalNoOfDays = pshCurColRepository.getNoOfDays(startDate, endDateTemp);
			/*if totalNoOfDays=0, set it as 1*/
			totalNoOfDays = totalNoOfDays.signum()<=0 ? BigDecimal.ONE : totalNoOfDays;
			logger.info("=====>total number of days : "+totalNoOfDays);
			
			XSSFWorkbook workbook = new XSSFWorkbook();
			
			Sheet sheet;
			if (month < 10) {

				sheet = workbook.createSheet(
						"IDLM-SAMA-Report-A-SARIE-" + Integer.toString(year) + "0" + Integer.toString(month));
			} else {
				sheet = workbook
						.createSheet("IDLM-SAMA-Report-A-SARIE-" + Integer.toString(year) + Integer.toString(month));
			}
			sheet.setColumnWidth(0, 54 * 256 + 200);
			sheet.setColumnWidth(1, 18 * 256 + 200);
			sheet.setColumnWidth(2, 18 * 256 + 200);
			sheet.setColumnWidth(3, 18 * 256 + 200);
			sheet.setColumnWidth(4, 23 * 256 + 200);

			Row directParticipants = sheet.createRow(0);
			Cell cellDp1 = directParticipants.createCell(0);
			cellDp1.setCellValue("Direct Participants");
			Cell cellDp2 = directParticipants.createCell(1);
			cellDp2.setCellValue("Table A");

			Row reportingMonth = sheet.createRow(1);
			Cell cellRm1 = reportingMonth.createCell(0);
			cellRm1.setCellValue("Reporting Month");
			Cell cellRm2 = reportingMonth.createCell(1);
			cellRm2.setCellValue(getReportingMonth(endDateTemp));

			Row sarie = sheet.createRow(2);
			Cell cellS1 = sarie.createCell(0);
			cellS1.setCellValue("Name of the large value payment system  ");
			Cell cellS2 = sarie.createCell(1);
			cellS2.setCellValue("SARIE");

			Row emptyrow1 = sheet.createRow(4);

			Row row5 = sheet.createRow(5);
			row5.createCell(0).setCellValue("A(i) Daily maximum intraday liquidity usage");
			row5.createCell(1).setCellValue("Max");
			row5.createCell(2).setCellValue("2nd Max");
			row5.createCell(3).setCellValue("3rd Max");
			row5.createCell(4).setCellValue("Avg");

			Row row6 = sheet.createRow(6);
			row6.createCell(0).setCellValue("1. Largest positive net cumulative position");
//			List<BigDecimal> netAmounts = getNetAmounts(startDate, endDate);
//			
//			List<BigDecimal> distinctNetAmtList = netAmounts.stream() 
//                    .distinct() 
//                    .collect(Collectors.toList());
			
			
			List<BigDecimal> positiveNetCumilativeList = getPositiveNetCumilativeList(startDate, endDate);
			Collections.sort(positiveNetCumilativeList, Collections.reverseOrder()); 
			
			

//			int size = netAmounts.size();
			int size = positiveNetCumilativeList.size();
			BigDecimal max1 = BigDecimal.ZERO;
			BigDecimal max2 = BigDecimal.ZERO;
			BigDecimal max3 = BigDecimal.ZERO;
			if (0 <= size - 1) {
//				max1 = netAmounts.get(0).compareTo(BigDecimal.ZERO) > 0 ? netAmounts.get(0) : BigDecimal.ZERO;
				max1 = positiveNetCumilativeList.get(0).compareTo(BigDecimal.ZERO) > 0 ? positiveNetCumilativeList.get(0) : BigDecimal.ZERO;
			}
			if (1 <= size - 1) {

//				max2 = netAmounts.get(1).compareTo(BigDecimal.ZERO) > 0 ? netAmounts.get(1) : BigDecimal.ZERO;
				max2 = positiveNetCumilativeList.get(1).compareTo(BigDecimal.ZERO) > 0 ? positiveNetCumilativeList.get(1) : BigDecimal.ZERO;
			}
			if (2 <= size - 1) {

//				max3 = netAmounts.get(2).compareTo(BigDecimal.ZERO) > 0 ? netAmounts.get(2) : BigDecimal.ZERO;
				max3 = positiveNetCumilativeList.get(2).compareTo(BigDecimal.ZERO) > 0 ? positiveNetCumilativeList.get(2) : BigDecimal.ZERO;
			}

//			BigDecimal avg = (max1.add(max2).add(max3)).divide(new BigDecimal("3"), 6, RoundingMode.HALF_UP);
			/* avg calculated as sum of all +ve net amounts / total no of days in the period */
			BigDecimal sumOfNetPositive = BigDecimal.ZERO;
			List<BigDecimal> positiveAmtList = new ArrayList<>();
			
//			positiveAmtList = netAmounts.stream()
//	                .filter(amt -> amt.compareTo(BigDecimal.ZERO) >= 0)
//	                .collect(Collectors.toList()); //saves +ve numbers to the list
			
			for(BigDecimal amount : positiveNetCumilativeList) {
				sumOfNetPositive = sumOfNetPositive.add(amount);
			}
			BigDecimal avg = (sumOfNetPositive).divide(totalNoOfDays, 2, RoundingMode.HALF_UP);
			row6.createCell(1).setCellValue(max1.doubleValue());
			row6.createCell(2).setCellValue(max2.doubleValue());
			row6.createCell(3).setCellValue(max3.doubleValue());
			row6.createCell(4).setCellValue(avg.doubleValue());

			Row row7 = sheet.createRow(7);
			row7.createCell(0).setCellValue("2. Largest negative net cumulative position");
			List<BigDecimal> negetiveNetCumilativeList = getNegetiveNetCumilativeList(startDate, endDate);
			Collections.sort(negetiveNetCumilativeList, Collections.reverseOrder());

			BigDecimal nMax1 = BigDecimal.ZERO;
			BigDecimal nMax2 = BigDecimal.ZERO;
			BigDecimal nMax3 = BigDecimal.ZERO;
			int nSize = negetiveNetCumilativeList.size();
			if (nSize - 1 >= 0) {
//				nMax1 = netAmounts.get(size - 1).compareTo(BigDecimal.ZERO) < 0 ? netAmounts.get(size - 1)
//						: BigDecimal.ZERO;
				nMax1 = negetiveNetCumilativeList.get(nSize - 1).compareTo(BigDecimal.ZERO) < 0 ? negetiveNetCumilativeList.get(nSize - 1)
						: BigDecimal.ZERO;
			}
			if (nSize - 2 >= 0) {

//				nMax2 = netAmounts.get(size - 2).compareTo(BigDecimal.ZERO) < 0 ? netAmounts.get(size - 2)
//						: BigDecimal.ZERO;
				nMax2 = negetiveNetCumilativeList.get(nSize - 2).compareTo(BigDecimal.ZERO) < 0 ? negetiveNetCumilativeList.get(nSize - 2)
						: BigDecimal.ZERO;
			}
			if (nSize - 3 >= 0) {

//				nMax3 = netAmounts.get(size - 3).compareTo(BigDecimal.ZERO) < 0 ? netAmounts.get(size - 3)
//						: BigDecimal.ZERO;
				nMax3 = negetiveNetCumilativeList.get(nSize - 3).compareTo(BigDecimal.ZERO) < 0 ? negetiveNetCumilativeList.get(nSize - 3)
						: BigDecimal.ZERO;

			}

//			BigDecimal nAvg = (nMax1.add(nMax2).add(nMax3)).divide(new BigDecimal("3"), 6, RoundingMode.HALF_UP);
			/* avg calculated as sum of all -ve net amounts / total no of days in the period */
			BigDecimal sumOfNetNegetive = BigDecimal.ZERO;
//			List<BigDecimal> negetiveAmtList = new ArrayList<>();
//			
//			negetiveAmtList = netAmounts.stream()
//	                .filter(amt -> amt.compareTo(BigDecimal.ZERO) < 0)
//	                .collect(Collectors.toList()); //saves -ve numbers to the list
			
//			negetiveAmtList = (List<BigDecimal>) netAmounts.stream().filter(amt -> amt.compareTo(BigDecimal.ZERO)==-1);//saves -ve numbers to the list
			
			for(BigDecimal amount : negetiveNetCumilativeList) {
				sumOfNetNegetive = sumOfNetNegetive.add(amount);
			}
			BigDecimal nAvg = (sumOfNetNegetive).divide(totalNoOfDays, 2, RoundingMode.HALF_UP);
			row7.createCell(1).setCellValue(nMax1.doubleValue());
			row7.createCell(2).setCellValue(nMax2.doubleValue());
			row7.createCell(3).setCellValue(nMax3.doubleValue());
			row7.createCell(4).setCellValue(nAvg.doubleValue());

			Row emptyrow2 = sheet.createRow(8);

			Row row9 = sheet.createRow(9);
			row9.createCell(0).setCellValue("A(ii) Available intraday liquidity at the start of the business day");
			row9.createCell(1).setCellValue("Min");
			row9.createCell(2).setCellValue("2nd Min");
			row9.createCell(3).setCellValue("3rd Min");
			row9.createCell(4).setCellValue("Avg");

			Row row10 = sheet.createRow(10);
			row10.createCell(0).setCellValue("total");
			List<BigDecimal> total = getListOfTotal(startDate, endDate);

			int sizeTotal = total.size();
			BigDecimal minTotal1 = BigDecimal.ZERO;
			BigDecimal mintotal2 = BigDecimal.ZERO;
			BigDecimal mintotal3 = BigDecimal.ZERO;
			if (0 <= sizeTotal - 1) {
				minTotal1 = total.get(0);
			}
			if (1 <= sizeTotal - 1) {

				mintotal2 = total.get(1);
			}
			if (2 <= sizeTotal - 1) {

				mintotal3 = total.get(2);
			}

			BigDecimal avgTotal = (minTotal1.add(mintotal2).add(mintotal3)).divide(new BigDecimal("3"), 2,
					RoundingMode.HALF_UP);
			row10.createCell(1).setCellValue(minTotal1.doubleValue());
			row10.createCell(2).setCellValue(mintotal2.doubleValue());
			row10.createCell(3).setCellValue(mintotal3.doubleValue());
			row10.createCell(4).setCellValue(avgTotal.doubleValue());

			Row row11 = sheet.createRow(11);
			row11.createCell(0).setCellValue("Of which:");

			Row row12 = sheet.createRow(12);
			row12.createCell(0).setCellValue("1.Central bank reserves");
			List<BigDecimal> centralBankReserves = getListOfSamaReserve(startDate, endDate);


			int sizeCbr = centralBankReserves.size();
			BigDecimal minCbr1 = BigDecimal.ZERO;
			BigDecimal minCbr2 = BigDecimal.ZERO;
			BigDecimal minCbr3 = BigDecimal.ZERO;
			if (0 <= sizeCbr - 1) {
				minCbr1 = centralBankReserves.get(0);
			}
			if (1 <= sizeCbr - 1) {

				minCbr2 = centralBankReserves.get(1);
			}
			if (2 <= sizeCbr - 1) {

				minCbr3 = centralBankReserves.get(2);
			}

			BigDecimal avgCbr = (minCbr1.add(minCbr2).add(minCbr3)).divide(new BigDecimal("3"), 2,
					RoundingMode.HALF_UP);
			row12.createCell(1).setCellValue(minCbr1.doubleValue());
			row12.createCell(2).setCellValue(minCbr2.doubleValue());
			row12.createCell(3).setCellValue(minCbr3.doubleValue());
			row12.createCell(4).setCellValue(avgCbr.doubleValue());

			Row row13 = sheet.createRow(13);
			row13.createCell(0).setCellValue("2.Collateral pledged at the central bank");
			List<BigDecimal> colPldgCb = getListOfColPldgCb(startDate, endDate);

			int sizeCpc = colPldgCb.size();
			BigDecimal minCpc1 = BigDecimal.ZERO;
			BigDecimal minCpc2 = BigDecimal.ZERO;
			BigDecimal minCpc3 = BigDecimal.ZERO;
			if (0 <= sizeCpc - 1) {
				minCpc1 = colPldgCb.get(0);
			}
			if (1 <= sizeCpc - 1) {

				minCpc2 = colPldgCb.get(1);
			}
			if (2 <= sizeCpc - 1) {

				minCpc3 = colPldgCb.get(2);
			}

			BigDecimal avgCpc = (minCpc1.add(minCpc2).add(minCpc3)).divide(new BigDecimal("3"), 2,
					RoundingMode.HALF_UP);
			row13.createCell(1).setCellValue(minCpc1.doubleValue());
			row13.createCell(2).setCellValue(minCpc2.doubleValue());
			row13.createCell(3).setCellValue(minCpc3.doubleValue());
			row13.createCell(4).setCellValue(avgCpc.doubleValue());

			Row row14 = sheet.createRow(14);
			row14.createCell(0).setCellValue("3.Collateral pledged at ancillary systems");
			List<BigDecimal> colPldgAs = getListOfColPldgAs(startDate, endDate);

			int sizeCpa = colPldgAs.size();
			BigDecimal minCpa1 = BigDecimal.ZERO;
			BigDecimal minCpa2 = BigDecimal.ZERO;
			BigDecimal minCpa3 = BigDecimal.ZERO;
			if (0 <= sizeCpa - 1) {
				minCpa1 = colPldgAs.get(0);
			}
			if (1 <= sizeCpa - 1) {

				minCpa2 = colPldgAs.get(1);
			}
			if (2 <= sizeCpa - 1) {

				minCpa3 = colPldgAs.get(2);
			}

			BigDecimal avgCpa = (minCpa1.add(minCpa2).add(minCpa3)).divide(new BigDecimal("3"), 2,
					RoundingMode.HALF_UP);
			row14.createCell(1).setCellValue(minCpa1.doubleValue());
			row14.createCell(2).setCellValue(minCpa2.doubleValue());
			row14.createCell(3).setCellValue(minCpa3.doubleValue());
			row14.createCell(4).setCellValue(avgCpa.doubleValue());

			Row row15 = sheet.createRow(15);
			row15.createCell(0).setCellValue("4.Unencumbered liquid assets on a bank’s balance sheet");
			List<BigDecimal> unLiqAssets = getListOfUnLiquidAssets(startDate, endDate);

			int sizeUla = unLiqAssets.size();
			BigDecimal minUla1 = BigDecimal.ZERO;
			BigDecimal minUla2 = BigDecimal.ZERO;
			BigDecimal minUla3 = BigDecimal.ZERO;
			if (0 <= sizeUla - 1) {
				minUla1 = unLiqAssets.get(0);
			}
			if (1 <= sizeUla - 1) {

				minUla2 = unLiqAssets.get(1);
			}
			if (2 <= sizeUla - 1) {

				minUla3 = unLiqAssets.get(2);
			}

			BigDecimal avgUla = (minUla1.add(minUla2).add(minUla3)).divide(new BigDecimal("3"), 2,
					RoundingMode.HALF_UP);
			row15.createCell(1).setCellValue(minUla1.doubleValue());
			row15.createCell(2).setCellValue(minUla2.doubleValue());
			row15.createCell(3).setCellValue(minUla3.doubleValue());
			row15.createCell(4).setCellValue(avgUla.doubleValue());

			Row row16 = sheet.createRow(16);
			row16.createCell(0).setCellValue("5.Total credit lines available");
			List<BigDecimal> totalCredLines = getListOfTotalCrline(startDate, endDate);

			int sizeTcl = totalCredLines.size();
			BigDecimal minTcl1 = BigDecimal.ZERO;
			BigDecimal minTcl2 = BigDecimal.ZERO;
			BigDecimal minTcl3 = BigDecimal.ZERO;
			if (0 <= sizeTcl - 1) {
				minTcl1 = totalCredLines.get(0);
			}
			if (1 <= sizeTcl - 1) {

				minTcl2 = totalCredLines.get(1);
			}
			if (2 <= sizeTcl - 1) {

				minTcl3 = totalCredLines.get(2);
			}

			BigDecimal avgTcl = (minTcl1.add(minTcl2).add(minTcl3)).divide(new BigDecimal("3"), 2,
					RoundingMode.HALF_UP);
			row16.createCell(1).setCellValue(minTcl1.doubleValue());
			row16.createCell(2).setCellValue(minTcl2.doubleValue());
			row16.createCell(3).setCellValue(minTcl3.doubleValue());
			row16.createCell(4).setCellValue(avgTcl.doubleValue());

			Row row17 = sheet.createRow(17);
			row17.createCell(0).setCellValue("5a.Of which secured");
			List<BigDecimal> secCredLines = getListOfSecCrdLine(startDate, endDate);

			int sizeScl = secCredLines.size();
			BigDecimal minScl1 = BigDecimal.ZERO;
			BigDecimal minScl2 = BigDecimal.ZERO;
			BigDecimal minScl3 = BigDecimal.ZERO;
			if (0 <= sizeScl - 1) {
				minScl1 = secCredLines.get(0);
			}
			if (1 <= sizeScl - 1) {

				minScl2 = secCredLines.get(1);
			}
			if (2 <= sizeScl - 1) {

				minScl3 = secCredLines.get(2);
			}

			BigDecimal avgScl = (minScl1.add(minScl2).add(minScl3)).divide(new BigDecimal("3"), 2,
					RoundingMode.HALF_UP);
			row17.createCell(1).setCellValue(minScl1.doubleValue());
			row17.createCell(2).setCellValue(minScl2.doubleValue());
			row17.createCell(3).setCellValue(minScl3.doubleValue());
			row17.createCell(4).setCellValue(avgScl.doubleValue());

			Row row18 = sheet.createRow(18);
			row18.createCell(0).setCellValue("5a.Of which committed");
			List<BigDecimal> cmtdCredLines = getListOfcmtdCrdLine(startDate, endDate);

			int sizeCcl = cmtdCredLines.size();
			BigDecimal minCcl1 = BigDecimal.ZERO;
			BigDecimal minCcl2 = BigDecimal.ZERO;
			BigDecimal minCcl3 = BigDecimal.ZERO;
			if (0 <= sizeCcl - 1) {
				minCcl1 = cmtdCredLines.get(0);
			}
			if (1 <= sizeCcl - 1) {

				minCcl2 = cmtdCredLines.get(1);
			}
			if (2 <= sizeCcl - 1) {

				minCcl3 = cmtdCredLines.get(2);
			}

			BigDecimal avgCcl = (minCcl1.add(minCcl2).add(minCcl3)).divide(new BigDecimal("3"), 2,
					RoundingMode.HALF_UP);
			row18.createCell(1).setCellValue(minCcl1.doubleValue());
			row18.createCell(2).setCellValue(minCcl2.doubleValue());
			row18.createCell(3).setCellValue(minCcl3.doubleValue());
			row18.createCell(4).setCellValue(avgCcl.doubleValue());

			Row row19 = sheet.createRow(19);
			row19.createCell(0).setCellValue("6. Balances with other banks");
			List<BigDecimal> balWithOthrBnks = getListOfBalWithOthrBnks(startDate, endDate);

			int sizeBwob = balWithOthrBnks.size();
			BigDecimal minBwob1 = BigDecimal.ZERO;
			BigDecimal minBwob2 = BigDecimal.ZERO;
			BigDecimal minBwob3 = BigDecimal.ZERO;
			if (0 <= sizeBwob - 1) {
				minBwob1 = balWithOthrBnks.get(0);
			}
			if (1 <= sizeBwob - 1) {

				minBwob2 = balWithOthrBnks.get(1);
			}
			if (2 <= sizeBwob - 1) {

				minBwob3 = balWithOthrBnks.get(2);
			}

			BigDecimal avgBwob = (minBwob1.add(minBwob2).add(minBwob3)).divide(new BigDecimal("3"), 2,
					RoundingMode.HALF_UP);
			row19.createCell(1).setCellValue(minBwob1.doubleValue());
			row19.createCell(2).setCellValue(minBwob2.doubleValue());
			row19.createCell(3).setCellValue(minBwob3.doubleValue());
			row19.createCell(4).setCellValue(avgBwob.doubleValue());

			Row row20 = sheet.createRow(20);
			row20.createCell(0).setCellValue("Other");
			List<BigDecimal> others = getListOfOthers(startDate, endDate);

			int sizeOthers = others.size();
			BigDecimal minOtr1 = BigDecimal.ZERO;
			BigDecimal minOtr2 = BigDecimal.ZERO;
			BigDecimal minOtr3 = BigDecimal.ZERO;
			if (0 <= sizeOthers - 1) {
				minOtr1 = others.get(0);
			}
			if (1 <= sizeOthers - 1) {

				minOtr2 = others.get(1);
			}
			if (2 <= sizeOthers - 1) {

				minOtr3 = others.get(2);
			}

			BigDecimal avgOtr = (minOtr1.add(minOtr2).add(minOtr3)).divide(new BigDecimal("3"), 2,
					RoundingMode.HALF_UP);
			row20.createCell(1).setCellValue(minOtr1.doubleValue());
			row20.createCell(2).setCellValue(minOtr2.doubleValue());
			row20.createCell(3).setCellValue(minOtr3.doubleValue());
			row20.createCell(4).setCellValue(avgOtr.doubleValue());

			Row row21 = sheet.createRow(21);

			Row row22 = sheet.createRow(22);
			row22.createCell(0).setCellValue("A(iii) Total payments");
			row22.createCell(1).setCellValue("Max");
			row22.createCell(2).setCellValue("2nd Max");
			row22.createCell(3).setCellValue("3rd Max");
			row22.createCell(4).setCellValue("Avg");

			Row row23 = sheet.createRow(23);
			List<BigDecimal> grossPaySent = getGrossPaySent(startDate, endDate);

			int sizeGps = grossPaySent.size();
			BigDecimal maxGps1 = BigDecimal.ZERO;
			BigDecimal maxGps2 = BigDecimal.ZERO;
			BigDecimal maxGps3 = BigDecimal.ZERO;
			if (0 <= sizeGps - 1) {
				maxGps1 = grossPaySent.get(0);
			}
			if (1 <= sizeGps - 1) {

				maxGps2 = grossPaySent.get(1);
			}
			if (2 <= sizeGps - 1) {

				maxGps3 = grossPaySent.get(2);
			}

//			BigDecimal avgGps = (maxGps1.add(maxGps2).add(maxGps3)).divide(new BigDecimal("3"), 6,
//					RoundingMode.HALF_UP);
			/* avg is calculated by sum of all gross payment sent/total no of days in the period */
			BigDecimal sumOfGrossPaymentSent = BigDecimal.ZERO;
			for(BigDecimal amount : grossPaySent) {
				sumOfGrossPaymentSent = sumOfGrossPaymentSent.add(amount);
			}
			BigDecimal avgGps = (sumOfGrossPaymentSent).divide(totalNoOfDays, 2,
					RoundingMode.HALF_UP);

			row23.createCell(0).setCellValue("1. Gross payments sent");
			row23.createCell(1).setCellValue(maxGps1.doubleValue());
			row23.createCell(2).setCellValue(maxGps2.doubleValue());
			row23.createCell(3).setCellValue(maxGps3.doubleValue());
			row23.createCell(4).setCellValue(avgGps.doubleValue());

			Row row24 = sheet.createRow(24);
			List<BigDecimal> grossPayRecv = getGrossPayRecv(startDate, endDate);

			int sizeGpr = grossPayRecv.size();

			BigDecimal maxGpr1 = BigDecimal.ZERO;
			BigDecimal maxGpr2 = BigDecimal.ZERO;
			BigDecimal maxGpr3 = BigDecimal.ZERO;
			if (0 <= sizeGpr - 1) {
				maxGpr1 = grossPayRecv.get(0);
			}
			if (1 <= sizeGpr - 1) {

				maxGpr2 = grossPayRecv.get(1);
			}
			if (2 <= sizeGpr - 1) {

				maxGpr3 = grossPayRecv.get(2);
			}

//			BigDecimal avgGpr = (maxGpr1.add(maxGpr2).add(maxGpr3)).divide(new BigDecimal("3"), 6,
//					RoundingMode.HALF_UP);
			/* avg is calculated by sum of all gross payment received/total no of days in the period */
			BigDecimal sumOfGrossPaymentReceived = BigDecimal.ZERO;
			for(BigDecimal amount : grossPayRecv) {
				sumOfGrossPaymentReceived = sumOfGrossPaymentReceived.add(amount);
			}
			BigDecimal avgGpr = (sumOfGrossPaymentReceived).divide(totalNoOfDays, 2,
					RoundingMode.HALF_UP);

			row24.createCell(0).setCellValue("2. Gross payments received");
			row24.createCell(1).setCellValue(maxGpr1.doubleValue());
			row24.createCell(2).setCellValue(maxGpr2.doubleValue());
			row24.createCell(3).setCellValue(maxGpr3.doubleValue());
			row24.createCell(4).setCellValue(avgGpr.doubleValue());

			Row row25 = sheet.createRow(25);

			Row row26 = sheet.createRow(26);
			row26.createCell(0).setCellValue("A(iv) Time-specific obligations");
			row26.createCell(1).setCellValue("Max");
			row26.createCell(2).setCellValue("2nd Max");
			row26.createCell(3).setCellValue("3rd Max");
			row26.createCell(4).setCellValue("Avg");

			Row row27 = sheet.createRow(27);
			row27.createCell(0).setCellValue("1.Total value of time-specific obligations");

			Row row28 = sheet.createRow(28);

			Row row29 = sheet.createRow(29);
			row29.createCell(0).setCellValue("C(i) Intraday throughput (%)");
//			row29.createCell(1).setCellValue("Max");
//			row29.createCell(2).setCellValue("2nd Max");
//			row29.createCell(3).setCellValue("3rd Max");
//			row29.createCell(4).setCellValue("Avg");
			row29.createCell(1).setCellValue("Avg");

			prepareIntradayThroughPutSection(startDate, endDate, sheet);
			
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			try {
			    workbook.write(bos);
			} finally {
			    bos.close();
			    workbook.close();
			}
			byte[] bytes = bos.toByteArray();
			
			PshSamLiqRep report = new PshSamLiqRep();
			report.setFileContent(bytes);
			report.setFileName("LQM Report "+ getMonthForInt(month-1)+".xlsx");
			report.setReportGeneratedDate(now);
			report.setReportType(Constants.SAMA_LIQUIDITY_REPORT.REPORT_TYPE_PARTIAL);
			
			PshSamLiqRep reportSave = liquidityReportRepository.save(report);
			
			if(reportSave!=null) {
				return new ServiceResponse(Constants.SUCCESS,
						messageSource.getMessage("samaLiquidityReport.details.psh.0001", null, LocaleContextHolder.getLocale()),
						null);
			}
			else {
				throw new RecordCreateException("samaLiquidityReport.details.psh.0002");
			}

		} catch (Exception e) {
			logger.error("error occured: " + e.getMessage(),e);

			return new ServiceResponse(Constants.FAILED,
					messageSource.getMessage("samaLiquidityReport.details.psh.0002", null, LocaleContextHolder.getLocale()),
					null);		
		}
		
	}
	
	private String getMonthForInt(int num) {
	    String month = "wrong";
	    DateFormatSymbols dfs = new DateFormatSymbols();
	    String[] months = dfs.getMonths();
	    if (num >= 0 && num <= 11) {
	        month = months[num];
	    }
	    return month;
	}

	private void prepareIntradayThroughPutSection(Date startDate, Date endDate, Sheet sheet) {

		int time = 8;
		int rowNo = 30;
		BigDecimal noOfDaysWithGrossSent = BigDecimal.ONE;
		noOfDaysWithGrossSent = pshCurColRepository.noOfDaysWithGrossSent(startDate, endDate);
		noOfDaysWithGrossSent = noOfDaysWithGrossSent.signum()<=0 ? BigDecimal.ONE : noOfDaysWithGrossSent;
		logger.info("===> noOfDaysWithGrossSent : "+noOfDaysWithGrossSent);
		List<BigDecimal> grossPaymentSentList = pshCurColRepository.listOfGrossPaymentSent(startDate, endDate);
		BigDecimal sumOfGrossPaySent = BigDecimal.ONE;
		if(grossPaymentSentList.size()>0) {
			sumOfGrossPaySent = grossPaymentSentList.stream().reduce(BigDecimal.ZERO, BigDecimal::add);
		}
		logger.info("===> sumOfGrossPaySent : "+sumOfGrossPaySent);
		
		while (time <= 18) {
			logger.info("==================================> time : "+time);
			Row row = sheet.createRow(rowNo);
			row.createCell(0).setCellValue(Integer.toString(time-7)+". Throughput at " + Integer.toString(time) + ":00");
			
			BigDecimal throughputAvg = BigDecimal.ZERO;
			List<BigDecimal> percentageSentList = getThroughPutSentWeighted(time,startDate, endDate);
			if(percentageSentList.size()>0) {
				for(int i=0; i<percentageSentList.size(); i++) {
					BigDecimal grossByTotal = grossPaymentSentList.get(i).divide(sumOfGrossPaySent, 6,
							RoundingMode.HALF_UP);
					logger.info("===> percent sent at time : "+time+ " is :"+grossPaymentSentList.get(i));
					logger.info("===> grossPaySent / sumOfGrossSent : "+grossByTotal);
					BigDecimal throughput = percentageSentList.get(i).multiply(grossByTotal).setScale(6,
							RoundingMode.HALF_UP);
					logger.info("===> throughput => percentSentAtTime*grossByTotal : "+throughput);
//					throughputAvg = throughputAvg.add(throughput).setScale(2,
//							RoundingMode.HALF_UP);
					throughputAvg = throughputAvg.add(throughput);
					
				}
				
			}
			
			
//			List<BigDecimal> throughput = getThroughPutSentPerc(time,startDate, endDate);
//			int size = throughput.size();
//			if(size!=0) {
//				
//				BigDecimal avg = (throughput.stream().reduce(BigDecimal.ZERO, BigDecimal::add)).divide(noOfDaysWithGrossSent, 6,
//						RoundingMode.HALF_UP).multiply(new BigDecimal("100"));

			logger.info("===> Avn throughput : "+throughputAvg.setScale(2,
					RoundingMode.HALF_UP));
			row.createCell(1).setCellValue(throughputAvg.setScale(2,RoundingMode.HALF_UP)
					.doubleValue());
//				row.createCell(1).setCellValue(throughputAvg.doubleValue());
				time++;
				rowNo++;
//			}else {
//				row.createCell(1).setCellValue(0);
//				time++;
//				rowNo++;
//			}
			
		}

	}

	private String getReportingMonth(Date now) {
		SimpleDateFormat sdf = new SimpleDateFormat("MMM yyyy");

		return sdf.format(now);
	}
	
	private List<BigDecimal> getListOfTotalCrline(Date startDate, Date endDate) {
		return samaCollateralRepository.getListOfTotalCrline( startDate, endDate).orElse(new ArrayList<BigDecimal>());
	}
	
	private List<BigDecimal> getListOfTotal(Date startDate, Date endDate) {
		return samaCollateralRepository.getListOfTotal( startDate, endDate).orElse(new ArrayList<BigDecimal>());
	}
	
	private List<BigDecimal> getListOfBalWithOthrBnks(Date startDate, Date endDate) {
		return samaCollateralRepository.getListOfBalWithOthrBnks( startDate, endDate).orElse(new ArrayList<BigDecimal>());
	}

	private List<BigDecimal> getThroughPutSentPerc(int time,Date startDate, Date endDate) {

		return pshCurColRepository.getThroughPutSentPerc(time, startDate, endDate).orElse(new ArrayList<BigDecimal>());
	}
	
	private List<BigDecimal> getThroughPutSentWeighted(int time,Date startDate, Date endDate) {

		return pshCurColRepository.getPercentageSentAtTimeList(time, startDate, endDate).orElse(new ArrayList<BigDecimal>());
		
	}

	private List<BigDecimal> getGrossPaySent(Date startDate, Date endDate) {

		return pshCurColRepository.getGrossPaySent(startDate, endDate).orElse(new ArrayList<BigDecimal>());
	}

	private List<BigDecimal> getGrossPayRecv(Date startDate, Date endDate) {

		return pshCurColRepository.getGrossPayRecv(startDate, endDate).orElse(new ArrayList<BigDecimal>());
	}

	private List<BigDecimal> getNetAmounts(Date startDate, Date endDate) {
		return liquidityDetailRepository.getListOfNetAmount(startDate, endDate).orElse(new ArrayList<BigDecimal>());
	}
	
	private List<BigDecimal> getPositiveNetCumilativeList(Date startDate, Date endDate) {
		return liquidityDetailRepository.getPositiveNetCumilativeList(startDate, endDate).orElse(new ArrayList<BigDecimal>());
	}
	
	private List<BigDecimal> getNegetiveNetCumilativeList(Date startDate, Date endDate) {
		return liquidityDetailRepository.getNegetiveNetCumilativeList(startDate, endDate).orElse(new ArrayList<BigDecimal>());
	}

	private List<BigDecimal> getListOfSamaReserve(Date startDate, Date endDate) {
		return samaCollateralRepository.getListOfSamaReserve(startDate, endDate).orElse(new ArrayList<BigDecimal>());
	}

	private List<BigDecimal> getListOfColPldgCb(Date startDate, Date endDate) {
		return samaCollateralRepository.getListOfColPldgCb(startDate, endDate).orElse(new ArrayList<BigDecimal>());
	}

	private List<BigDecimal> getListOfColPldgAs(Date startDate, Date endDate) {
		return samaCollateralRepository.getListOfColPldgAs(startDate, endDate).orElse(new ArrayList<BigDecimal>());
	}

	private List<BigDecimal> getListOfUnLiquidAssets(Date startDate, Date endDate) {
		return samaCollateralRepository.getListOfUnLiquidAssets(startDate, endDate).orElse(new ArrayList<BigDecimal>());
	}

	private List<BigDecimal> getListOfSecCrdLine(Date startDate, Date endDate) {
		return samaCollateralRepository.getListOfSecCrdLine(startDate, endDate).orElse(new ArrayList<BigDecimal>());
	}

	private List<BigDecimal> getListOfcmtdCrdLine(Date startDate, Date endDate) {
		return samaCollateralRepository.getListOfcmtdCrdLine(startDate, endDate).orElse(new ArrayList<BigDecimal>());
	}

	private List<BigDecimal> getListOfOthers(Date startDate, Date endDate) {
		return samaCollateralRepository.getListOfOthers(startDate, endDate).orElse(new ArrayList<BigDecimal>());
	}

}
