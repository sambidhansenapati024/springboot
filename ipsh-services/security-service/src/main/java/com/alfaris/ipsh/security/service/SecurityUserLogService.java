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
** Program description  : SecurityUserLogService
** Version No           : 1.0.0
** Author               : Harichand
** Date Created         : 06-April-2020
** Modification Log     :   
CRId/ProjectId	Date Modified      	User		         Description		
Prod_1.0.0	               			 
*****************************************************************************/
package com.alfaris.ipsh.security.service;

import org.apache.poi.ss.usermodel.Sheet;
import org.json.simple.JSONObject;

import com.alfaris.ipsh.security.dto.SearchUserLog;
import com.itextpdf.text.pdf.PdfPTable;

public interface SecurityUserLogService {

	JSONObject getUserLog(String searchParam, String iDisplayStart, String iDisplayLength, String strUserId);

	Sheet getUserLogTableExcelSheet(SearchUserLog usersearch, Sheet sheet);

	PdfPTable getUserLogTableData(SearchUserLog usersearch, PdfPTable table);

}
