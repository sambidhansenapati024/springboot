package com.alfaris.ipsh.subscription.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.StringUtils;
import com.alfaris.ipsh.subscription.dto.ActionLogDto;
import com.alfaris.ipsh.subscription.entity.ActionLog;
import com.alfaris.ipsh.subscription.entity.ActionLogPk;
import com.alfaris.ipsh.subscription.repo.ActionLogRepo;
import com.alfaris.ipsh.subscription.repo.spec.ActionLogSpec;
import com.alfaris.ipsh.subscription.utils.Constants;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Service
@Slf4j
public class ActionServiceImpl implements ActionService {

	private final ActionLogRepo actionLogRepo;

	@Override
	public void saveUserLog(String userName, String loginStatus) {
	    try {
	        if (!StringUtils.isEmpty(userName)) {
	        	System.out.println(userName);
	            ActionLog actionLog = createUserLog(userName, loginStatus);
	            actionLogRepo.save(actionLog);
	        }
	    } catch (Exception e) {
	        log.error("Error occurred: {}", e.getMessage());
	    }
	}

	private ActionLog createUserLog(String userName, String loginStatus) {
	    ActionLog actionLog = new ActionLog();
	    ActionLogPk pk = new ActionLogPk();

	    Date date = new Date();
	    pk.setUserName(userName);
	    pk.setActiontime(date);
	    pk.setId(generateAlphanumericId(3));
	    actionLog.setPk(pk);

	    switch (loginStatus.toUpperCase()) {
	        case Constants.ACTION_DTLS.CS:
	            setActionLogDetails(actionLog, "Created Successfully", Constants.ACTION_DTLS.CREATE, Constants.ACTION_DTLS.SUCCESS);
	            break;
	        case Constants.ACTION_DTLS.CF:
	            setActionLogDetails(actionLog, "Creation Failed", Constants.ACTION_DTLS.CREATE,Constants.ACTION_DTLS.FAILED);
	            break;
	        case Constants.ACTION_DTLS.US:
	            setActionLogDetails(actionLog, "Update Success", Constants.ACTION_DTLS.UPDATE, Constants.ACTION_DTLS.SUCCESS);
	            break;
	        case Constants.ACTION_DTLS.UF:
	            setActionLogDetails(actionLog, "Update Failed", Constants.ACTION_DTLS.UPDATE, Constants.ACTION_DTLS.FAILED);
	            break;
	        case Constants.ACTION_DTLS.VS:
	            setActionLogDetails(actionLog, "Verify Success", Constants.ACTION_DTLS.VERIFY, Constants.ACTION_DTLS.SUCCESS);
	            break;
	        case Constants.ACTION_DTLS.VF:
	            setActionLogDetails(actionLog, "Verify Failed", Constants.ACTION_DTLS.VERIFY, Constants.ACTION_DTLS.FAILED);
	            break;
	        case Constants.ACTION_DTLS.DS:
	            setActionLogDetails(actionLog, "Delete Success", Constants.ACTION_DTLS.DELETE, Constants.ACTION_DTLS.SUCCESS);
	            break;
	        case Constants.ACTION_DTLS.DF:
	            setActionLogDetails(actionLog, "Delete Failed",Constants.ACTION_DTLS.DELETE, Constants.ACTION_DTLS.FAILED);
	            break;
	        case Constants.ACTION_DTLS.VAL_F:
	            setActionLogDetails(actionLog, "Validation Failed", Constants.ACTION_DTLS.VALIDATION, Constants.ACTION_DTLS.FAILED);
	            break;
	        default:
	            setActionLogDetails(actionLog, "Unknown Status",Constants.ACTION_DTLS.UNKNOWN, Constants.ACTION_DTLS.FAILED);
	            break;
	    }

	    Timestamp ts = new Timestamp(date.getTime());
	    actionLog.setSystime(ts);
	    return actionLog;
	}

	private void setActionLogDetails(ActionLog actionLog, String reason, String action, String status) {
	    actionLog.setReason(reason);
	    actionLog.setAction(action);
	    actionLog.setStatus(status);
	}

	private String generateAlphanumericId(int length) {
		String alphanumericCharacters = Constants.ALF_NUM_VALUE;
		StringBuilder id = new StringBuilder("ACT");

		for (int i = 0; i < length; i++) {
			int randomIndex = ThreadLocalRandom.current().nextInt(alphanumericCharacters.length());
			id.append(alphanumericCharacters.charAt(randomIndex));
		}
		return id.toString();
	}
	@SuppressWarnings("unchecked")
	@Override
	public JSONObject getUserLog(String searchParam, String iDisplayStart, String iDisplayLength) {
		JSONObject res = new JSONObject();
		JSONArray logArray = new JSONArray();
		SimpleDateFormat logTimeFormat = new SimpleDateFormat("dd-MM-yy hh.mm.ss.SS a");
		SimpleDateFormat sysDateFormat = new SimpleDateFormat("dd-MM-yyyy");
		try {
			Pageable pageable = PageRequest.of(Integer.parseInt(iDisplayStart) / Integer.parseInt(iDisplayLength),
					Integer.parseInt(iDisplayLength));
			Specification<ActionLog> spec = ActionLogSpec.getUserSpec(searchParam);
			Page<ActionLog> logList = actionLogRepo.findAll(ActionLogSpec.getUserSpec(searchParam), pageable);
			if (logList != null) {
				for (ActionLog logEntity : logList) {
					JSONObject log = new JSONObject();
					log.put("userName", logEntity.getPk().getUserName());
					log.put(Constants.USER_LOG_LOG_TIME, logTimeFormat.format(logEntity.getPk().getActiontime()));
					log.put(Constants.USER_LOG_REASON, logEntity.getReason());
					log.put(Constants.USER_LOG_STATUS, logEntity.getStatus());
					log.put(Constants.USER_LOG_SYSTEM_DATE, sysDateFormat.format(logEntity.getSystime()));
					log.put("action", logEntity.getAction());
					logArray.add(log);
				}
				res.put(Constants.AA_DATA, logArray);
				res.put(Constants.TOTAL_DISPLAY_RECORD, actionLogRepo.findAll(spec).size());
				res.put(Constants.TOTAL_RECORD, actionLogRepo.findAll(spec).size());
			}
		} catch (Exception e) {
			log.error("errot :" + e.getMessage(), e);
			res.put(Constants.AA_DATA, new JSONArray());
			res.put(Constants.TOTAL_DISPLAY_RECORD, 0);
			res.put(Constants.TOTAL_RECORD, 0);
		}
		return res;
	}

	@Override
	public Sheet getUserLogTableExcelSheet(ActionLogDto usersearch, Sheet sheet) {

		try {
			List<ActionLog> logDetails = actionLogRepo.findAll(ActionLogSpec.getHeaderBySearchSpecReport(usersearch));
			for (int i = 0; i < logDetails.size(); i++) {
				Row row = sheet.createRow(i + 1);
//				row.setRowStyle(dataStyle);
				Date logTime = logDetails.get(i).getPk().getActiontime();
				SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yy hh.mm.ss.SS a");
				String formattedLogTime = df.format(logTime);
				Date sysDate = logDetails.get(i).getSystime();
				df = new SimpleDateFormat("dd-MMM-yy");
				String formattedSysDate = df.format(sysDate);
				row.createCell(0).setCellValue(logDetails.get(i).getPk().getUserName());
				row.createCell(1).setCellValue(formattedLogTime);
				String status = logDetails.get(i).getStatus();
				row.createCell(2).setCellValue(status);
				row.createCell(3).setCellValue(logDetails.get(i).getReason());
				row.createCell(4).setCellValue(logDetails.get(i).getAction());
				row.createCell(5).setCellValue(formattedSysDate);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return sheet;
	}

	@Override
	public PdfPTable getUserLogTableData(ActionLogDto usersearch, PdfPTable table) {
		System.out.println(usersearch);
		Font font = new Font(Font.FontFamily.TIMES_ROMAN, 9);
		PdfPCell dataCell = null;
		String logStatus = "";
		try {
			List<ActionLog> logDetails = actionLogRepo.findAll(ActionLogSpec.getHeaderBySearchSpecReport(usersearch));

			for (ActionLog log : logDetails) {
				String userId = log.getPk().getUserName();
				dataCell = new PdfPCell(new Paragraph(userId, font));
				table.addCell(dataCell);
				Date logTime = log.getPk().getActiontime();
				SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yy hh.mm.ss.SS a");
				dataCell = new PdfPCell(new Paragraph(df.format(logTime), font));
				table.addCell(dataCell);
				String status = log.getStatus();
				dataCell = new PdfPCell(new Paragraph(status, font));
				table.addCell(dataCell);
				String reason = log.getReason();
				dataCell = new PdfPCell(new Paragraph(reason, font));
				table.addCell(dataCell);
				String action = log.getAction();
				dataCell = new PdfPCell(new Paragraph(action, font));
				table.addCell(dataCell);
				Date sysDate = log.getSystime();
				df = new SimpleDateFormat("dd-MMM-yyyy");
				dataCell = new PdfPCell(new Paragraph(df.format(sysDate), font));
				table.addCell(dataCell);
			}
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		return table;
	}
	
	
	@SuppressWarnings("unchecked")
	public JSONObject generateErrorDataExcel(ActionLogDto usersearch) {
	    JSONObject obj = new JSONObject();
	    try {
	        Workbook workbook = new XSSFWorkbook();
	        Sheet sheet = workbook.createSheet("Login Details Report");

	        // Create Header Style
	        XSSFCellStyle headerStyle = (XSSFCellStyle) workbook.createCellStyle();
	        XSSFFont headerFont = (XSSFFont) workbook.createFont();
	        headerFont.setBold(true);
	        headerStyle.setFont(headerFont);

	        // Create Header Row
	        Row header = sheet.createRow(0);
	        header.createCell(0).setCellValue(Constants.USER_LOG_MSG_USER_ID);
	        header.getCell(0).setCellStyle(headerStyle);

	        header.createCell(1).setCellValue(Constants.USER_LOG_MSG_LOGIN_TIME);
	        header.getCell(1).setCellStyle(headerStyle);

	        header.createCell(2).setCellValue(Constants.USER_LOG_MSG_LOGIN_STATUS);
	        header.getCell(2).setCellStyle(headerStyle);

	        header.createCell(3).setCellValue(Constants.USER_LOG_MSG_REASON);
	        header.getCell(3).setCellStyle(headerStyle);

	        header.createCell(4).setCellValue(Constants.USERLOG.USER_LOG_ACTION);
	        header.getCell(4).setCellStyle(headerStyle);

	        header.createCell(5).setCellValue(Constants.USER_LOG_MSG_SYSTEM_DATE);
	        header.getCell(5).setCellStyle(headerStyle);

	        // Populate Data Rows
	        sheet =getUserLogTableExcelSheet(usersearch, sheet);

	        // Adjust Column Widths
	        for (int i = 0; i <= 5; i++) {
	            sheet.autoSizeColumn(i);
	        }

	        // Encode Workbook to Base64
	        ByteArrayOutputStream fileOutputStream = new ByteArrayOutputStream();
	        workbook.write(fileOutputStream);
	        workbook.close();

	        byte[] isr = fileOutputStream.toByteArray();
	        String encoded = Base64.getEncoder().encodeToString(isr);

	        // Prepare JSON Response
	        obj.put(Constants.DATA, encoded);
	        obj.put(Constants.FILE_NAME, Constants.USER_LOG_XLS_FILE_NAME);
	        obj.put(Constants.CONTENT_TYPE, Constants.EXCEL_CONTENT_TYPE);
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return obj;
	}
	

	@SuppressWarnings("unchecked")
	public JSONObject generatePdfData(ActionLogDto usersearch) {
	    JSONObject obj = new JSONObject();
	    Font catFont = new Font(Font.FontFamily.TIMES_ROMAN, 15, Font.BOLD);
	    try {
	        Resource resource = new ClassPathResource("logo.png");
	        InputStream inputStream = resource.getInputStream();

	        ByteArrayOutputStream fileOutputStream = new ByteArrayOutputStream();
	        Document document = new Document();
	        PdfWriter.getInstance(document, fileOutputStream);
	        document.open();

	        // Add Logo Image
	        byte[] bdata = FileCopyUtils.copyToByteArray(inputStream);
	        addImage(document, bdata);

	        // Add Title
	        Paragraph paragraph = new Paragraph(Constants.ACTION_LOG_PDF_NAME, catFont);
	        paragraph.setAlignment(Element.ALIGN_CENTER);
	        document.add(paragraph);
	        document.add(Chunk.NEWLINE);

	        // Add Table
	        PdfPTable table = new PdfPTable(6);
	        Font font = new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.BOLD);
	        table.setWidthPercentage(100);
	        float[] columnWidths = { 1.2f, 2.2f, 2f, 1.8f, 1.7f, 1.7f };
	        table.setWidths(columnWidths);

	        // Add Header Cells
	        table.addCell(new PdfPCell(new Paragraph(Constants.USER_LOG_MSG_USER_ID, font)));
	        table.addCell(new PdfPCell(new Paragraph(Constants.USER_LOG_MSG_LOGIN_TIME, font)));
	        table.addCell(new PdfPCell(new Paragraph(Constants.USER_LOG_MSG_LOGIN_STATUS, font)));
	        table.addCell(new PdfPCell(new Paragraph(Constants.USER_LOG_MSG_REASON, font)));
	        table.addCell(new PdfPCell(new Paragraph(Constants.USERLOG.USER_LOG_ACTION, font)));
	        table.addCell(new PdfPCell(new Paragraph(Constants.USER_LOG_MSG_SYSTEM_DATE, font)));
	        table.setHeaderRows(1);

	        // Add Data Rows
	        table = getUserLogTableData(usersearch, table);
	        document.add(table);

	        // Finalize Document
	        document.close();

	        // Encode PDF File to Base64
	        byte[] isr = fileOutputStream.toByteArray();
	        String encoded = Base64.getEncoder().encodeToString(isr);

	        // Prepare JSON Response
	        obj.put(Constants.DATA, encoded);
	        obj.put(Constants.FILE_NAME, Constants.USER_LOG_PDF_FILE_NAME);
	        obj.put(Constants.CONTENT_TYPE, Constants.PDF_CONTENT_TYPE);
	    } catch (Exception e) {
	        log.error("Error generating PDF: {}", e.getMessage());
	    }
	    return obj;
	}
	public void addImage(Document document, byte[] imageArray) {
		try {
			Image logo = Image.getInstance(imageArray);
			logo.scaleAbsolute(80f, 100f);
			document.add(logo);
		} catch (DocumentException e) {
			log.error("UserController in method addImage() " + e.getMessage(), e);
		} catch (MalformedURLException e) {
			log.error("UserController in method addImage() " + e.getMessage(), e);
		} catch (IOException e) {
			log.error("UserController in method addImage() " + e.getMessage(), e);
		}
	}

	
}
