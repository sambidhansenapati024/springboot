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
** Program description  : SecurityController
** Version No           : 1.0.0
** Author               : Adarsh,Harichand,Yadhu
** Date Created         : 06-April-2020
** Modification Log     :   
CRId/ProjectId	Date Modified      	User		         Description		
Prod_1.0.0	               			 
*****************************************************************************/
package com.alfaris.ipsh.security.controller;

import org.json.simple.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alfaris.ipsh.security.service.SecurityUserService;

@RestController
@RequestMapping(value = "/securityGroup")
public class SecurityGroupController {

	@Autowired
	SecurityUserService securityUserService;

	@GetMapping("/getHomeScreenUrlDropDown")
	public ResponseEntity<JSONArray> getHomeScreenUrlDropDown() {
		JSONArray homeScreenUrl = securityUserService.getHomeScreenUrlDropDown();

		return new ResponseEntity<>(homeScreenUrl, new HttpHeaders(), HttpStatus.OK);
	}

}
