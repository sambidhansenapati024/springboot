package com.alfaris.ipsh.subscription.service;

import org.apache.poi.ss.usermodel.Sheet;
import org.json.simple.JSONObject;

import com.alfaris.ipsh.subscription.dto.ActionLogDto;
import com.alfaris.ipsh.subscription.entity.ActionLog;
import com.itextpdf.text.pdf.PdfPTable;

public interface ActionService {
	void saveUserLog(String userName, String loginStatus);
	JSONObject getUserLog(String searchParam, String iDisplayStart, String iDisplayLength);

	PdfPTable getUserLogTableData(ActionLogDto usersearch, PdfPTable table);
	Sheet getUserLogTableExcelSheet(ActionLogDto usersearch, Sheet sheet);



}
