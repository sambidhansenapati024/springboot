package com.alfaris.ipsh.security.service;

import java.security.Principal;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.alfaris.ipsh.security.dto.ServiceResponse;
import com.alfaris.ipsh.security.dto.UsersDto;
import com.alfaris.ipsh.security.entity.Users;
import com.alfaris.ipsh.security.exception.RecordCreateException;
import com.alfaris.ipsh.security.exception.RecordNotFoundException;
import com.alfaris.ipsh.security.exception.RecordUpdateException;

public interface SecurityUserService {

	JSONObject searchByLimit(String searchParam, int parseInt, int parseInt2);

	Users getUserById(String id) throws RecordNotFoundException;

	List<Users> getAllUsers();

	ServiceResponse createUser(UsersDto user, Principal principal) throws RecordCreateException;

	ServiceResponse updateUser(UsersDto user, Principal principal) throws RecordUpdateException, RecordNotFoundException;

	ServiceResponse deleteUserById(String id, Principal principal)
			throws RecordNotFoundException, RecordUpdateException;

	ServiceResponse verifyUser(String userId, Principal principal) throws RecordNotFoundException;

	JSONObject getServiceIdDrop();

	JSONObject getBankIdDropDown();

	String getBankId(String user);

	JSONArray getDepartmentDropDown();

	JSONArray getHomeScreenUrlDropDown();

}
