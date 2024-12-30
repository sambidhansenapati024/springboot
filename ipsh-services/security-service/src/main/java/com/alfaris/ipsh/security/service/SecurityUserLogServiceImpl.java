/****************************************************************************
 *                              COPYRIGHT NOTICE
*
*                      Copyright(@2006) by Interland Technology Services PVT. LTD **
*
*      This program is used to monitor the stream control and Stop/Start
*      the streams. The program and related materials are confidential and
*      proprietary of Interland Technology Services PVT. LTD and no part of these materials
*      should be reproduced, published in any forms without the written
*      approval of INTERLAND
*
** Project Name         : iPSH
** Program description  : SecurityUserLogServiceImpl
** Version No           : 1.0.0
** Author               : Harichand
** Date Created         : 06-April-2020
** Modification Log     :   
CRId/ProjectId	Date Modified      	User		         Description		
Prod_1.0.0	               			 
*****************************************************************************/
package com.alfaris.ipsh.security.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.alfaris.ipsh.security.dto.SearchUserLog;
import com.alfaris.ipsh.security.entity.LogDetails;
import com.alfaris.ipsh.security.repository.UserLogRepository;
import com.alfaris.ipsh.security.repository.specification.SecurityUserLogSpec;
import com.alfaris.ipsh.security.util.Constants;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;

@Service
public class SecurityUserLogServiceImpl implements SecurityUserLogService{
	
	@Autowired
	UserLogRepository userLogRepository;
	
	private static Logger logger = LogManager.getLogger(SecurityUserLogServiceImpl.class);

	
	@Override
	public JSONObject getUserLog(String searchParam, String iDisplayStart, String iDisplayLength, String strUserId) {

		JSONObject res = new JSONObject();
		JSONArray logArray = new JSONArray();
		SimpleDateFormat logTimeFormat = new SimpleDateFormat("dd-MM-yy hh.mm.ss.SS a");
		SimpleDateFormat sysDateFormat = new SimpleDateFormat("dd-MM-yyyy");
		try {
			Pageable pageable = PageRequest.of(Integer.parseInt(iDisplayStart) / Integer.parseInt(iDisplayLength),
					Integer.parseInt(iDisplayLength));
			Page<LogDetails> logList = userLogRepository.findAll(SecurityUserLogSpec.getHeaderBySearchSpec(searchParam),
					pageable);
			if (logList != null) {
				for (LogDetails logEntity : logList) {
					String status = logEntity.getLogStatus();
					String loginStatus = Constants.USER_LOG_FAIL;
					if (status.trim().equals(Constants.USER_LOG_S)) {
						loginStatus = Constants.USER_LOG_SUCCESS;
					}
					JSONObject log = new JSONObject();
					log.put(Constants.USER_LOG_USER_ID, logEntity.getPk().getUserId());
					log.put(Constants.USER_LOG_LOG_TIME, logTimeFormat.format(logEntity.getPk().getLogTime()));
					log.put(Constants.USER_LOG_REASON, logEntity.getReason());
					log.put(Constants.USER_LOG_STATUS, loginStatus);
					log.put(Constants.USER_LOG_SYSTEM_DATE, sysDateFormat.format(logEntity.getSysDate()));
					log.put(Constants.USER_LOG_IP_ADDRESS, logEntity.getIpAddress());
					logArray.add(log);
				}
				res.put(Constants.AA_DATA, logArray);
				res.put(Constants.TOTAL_DISPLAY_RECORD,
						userLogRepository.findAll(SecurityUserLogSpec.getHeaderBySearchSpec(searchParam)).size());
				res.put(Constants.TOTAL_RECORD,
						userLogRepository.findAll(SecurityUserLogSpec.getHeaderBySearchSpec(searchParam)).size());
			}
		} catch (Exception e) {
			logger.error("errot :"+ e.getMessage(),e);
			res.put(Constants.AA_DATA, new JSONArray());
			res.put(Constants.TOTAL_DISPLAY_RECORD, 0);
			res.put(Constants.TOTAL_RECORD, 0);
		}
		return res;
	}

	@Override
	public Sheet getUserLogTableExcelSheet(SearchUserLog usersearch, Sheet sheet) {
		String logStatus = "";
		try {
			List<LogDetails> logDetails = userLogRepository
					.findAll(SecurityUserLogSpec.getHeaderBySearchSpecReport(usersearch));
			for (int i = 0; i < logDetails.size(); i++) {
				Row row = sheet.createRow(i + 1);
//				row.setRowStyle(dataStyle);
				Date logTime = logDetails.get(i).getPk().getLogTime();
				SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yy hh.mm.ss.SS a");
				String formattedLogTime = df.format(logTime);
				Date sysDate = logDetails.get(i).getSysDate();
				df = new SimpleDateFormat("dd-MMM-yy");
				String formattedSysDate = df.format(sysDate);
				row.createCell(0).setCellValue(logDetails.get(i).getPk().getUserId());
				row.createCell(1).setCellValue(formattedLogTime);
				String status = logDetails.get(i).getLogStatus();
				if (status.equalsIgnoreCase(Constants.USER_LOG_S)) {
					logStatus = Constants.USER_LOG_SUCCESSFUL;
				} else {
					logStatus = Constants.USER_LOG_FAILURE;
				}
				row.createCell(2).setCellValue(logStatus);
				row.createCell(3).setCellValue(logDetails.get(i).getReason());
				row.createCell(4).setCellValue(logDetails.get(i).getIpAddress());
				row.createCell(5).setCellValue(formattedSysDate);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return sheet;
	}

	@Override
	public PdfPTable getUserLogTableData(SearchUserLog usersearch, PdfPTable table) {
		Font font = new Font(Font.FontFamily.TIMES_ROMAN, 9);
		PdfPCell dataCell = null;
		String logStatus = "";
		try {
			List<LogDetails> logDetails = userLogRepository
					.findAll(SecurityUserLogSpec.getHeaderBySearchSpecReport(usersearch));
			for (LogDetails log : logDetails) {
				String userId = log.getPk().getUserId();
				dataCell = new PdfPCell(new Paragraph(userId, font));
				table.addCell(dataCell);
				Date logTime = log.getPk().getLogTime();
				SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yy hh.mm.ss.SS a");
				dataCell = new PdfPCell(new Paragraph(df.format(logTime), font));
				table.addCell(dataCell);
				String status = log.getLogStatus();
				if (status.equalsIgnoreCase(Constants.USER_LOG_S)) {
					logStatus = Constants.USER_LOG_SUCCESSFUL;
				} else {
					logStatus = Constants.USER_LOG_FAILURE;
				}
				dataCell = new PdfPCell(new Paragraph(logStatus, font));
				table.addCell(dataCell);
				String reason = log.getReason();
				dataCell = new PdfPCell(new Paragraph(reason, font));
				table.addCell(dataCell);
				String ipAddress = log.getIpAddress();
				dataCell = new PdfPCell(new Paragraph(ipAddress, font));
				table.addCell(dataCell);
				Date sysDate = log.getSysDate();
				df = new SimpleDateFormat("dd-MMM-yyyy");
				dataCell = new PdfPCell(new Paragraph(df.format(logTime), font));
				table.addCell(dataCell);
			}
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		return table;
	}


}
