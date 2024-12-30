package com.alfaris.ipsh.authservice.service;

import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.alfaris.ipsh.authservice.dto.AuditKeyDTO;
import com.alfaris.ipsh.authservice.dto.ServiceResponse;
import com.alfaris.ipsh.authservice.entity.PshCasHry;
import com.alfaris.ipsh.authservice.entity.PshColCnf;
import com.alfaris.ipsh.authservice.entity.Users;
import com.alfaris.ipsh.authservice.exception.RecordCreateException;
import com.alfaris.ipsh.authservice.exception.RecordNotFoundException;
import com.alfaris.ipsh.authservice.exception.RecordUpdateException;

public interface UserService {
	public List<Users> getAllUsers();

	public Users getUserById(String id) throws RecordNotFoundException;

	public ServiceResponse createUser(Users entity) throws RecordCreateException;

	public ServiceResponse updateUser(Users entity) throws RecordUpdateException, RecordNotFoundException;

	public ServiceResponse deleteUserById(String id) throws RecordNotFoundException, RecordUpdateException;

	JSONObject searchByLimit(String searchParam, int start, int pageSize);

	public JSONArray accessScreenList(String name);

	public ServiceResponse saveTheme(JSONObject jsonData)
			throws RecordCreateException, RecordNotFoundException;

	public ServiceResponse updateTheme(JSONObject entity)
			throws RecordNotFoundException, RecordUpdateException;

	String getUserThemeById(String id) throws RecordNotFoundException;

	public ServiceResponse saveAuditData(PshCasHry audit);

	public PshCasHry getAuditData(AuditKeyDTO auditKey) throws RecordNotFoundException;

	public JSONObject searchAuditData(String searchParam, int parseInt, int parseInt2);

	public JSONObject getApplicationLog();

	public JSONObject getUserInfo(String name);

	PshColCnf getTableConfig(String userId, String screenId) throws RecordNotFoundException;

	ServiceResponse saveOrUpdateTableConfig(PshColCnf config);

	public void logOn(String logId);

	void saveUserLog(String userId, String loginStatus, HttpServletRequest request);
}
