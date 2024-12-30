package com.alfaris.ipsh.security.service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import com.alfaris.ipsh.security.client.AuditLogUtil;
import com.alfaris.ipsh.security.dto.ServiceResponse;
import com.alfaris.ipsh.security.dto.UsersDto;
import com.alfaris.ipsh.security.entity.PshUsrDep;
import com.alfaris.ipsh.security.entity.Screen;
import com.alfaris.ipsh.security.entity.Users;
import com.alfaris.ipsh.security.exception.RecordCreateException;
import com.alfaris.ipsh.security.exception.RecordNotFoundException;
import com.alfaris.ipsh.security.exception.RecordUpdateException;
import com.alfaris.ipsh.security.repository.DepartmentRepository;
import com.alfaris.ipsh.security.repository.GroupRepository;
import com.alfaris.ipsh.security.repository.GroupScreenRepository;
import com.alfaris.ipsh.security.repository.ScreenRepository;
import com.alfaris.ipsh.security.repository.UserRepository;
import com.alfaris.ipsh.security.repository.specification.SecurityUserSpec;
import com.alfaris.ipsh.security.util.Constants;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.alfaris.ipsh.security.encrypt.EncryptionUtil;

@Service
public class SecurityUserServiceImpl implements SecurityUserService {
	
	@Autowired
	UserRepository userRepository;

	@Autowired
	ScreenRepository screenRepository;

	@Autowired
	GroupScreenRepository groupScreenRepository;

	@Autowired
	GroupRepository groupRepository;
	
	@Autowired
	MessageSource messageSource;
	
	@Autowired
	AuditLogUtil auditLogUtil;
	
	@Autowired
	DepartmentRepository departmentRepository;
	
	@Value("${bankId}")
	String bankId;
	
	
	private static Logger logger = LogManager.getLogger(SecurityUserServiceImpl.class);

	@Override
	public List<Users> getAllUsers() {
		List<Users> userList = userRepository.findAll();
		if (!userList.isEmpty()) {
			return userList;
		} else {
			return new ArrayList<>();
		}
	}

	@Override
	public Users getUserById(String id) throws RecordNotFoundException {
		Optional<Users> user = userRepository.findById(id);
		if (user.isPresent()) {
			try {
				String secret =  EncryptionUtil.decryptPassword(user.get().getPassword());
				user.get().setPassword(secret);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return user.get();
		} else {
			throw new RecordNotFoundException("user.details.psh.VAL0001");
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public ServiceResponse createUser(UsersDto userDto, Principal principal) throws RecordCreateException {
		String newAuditLogData = null;
		String oldAuditLogData = null;
		Users entity = new Users();
		List<JSONObject> customMessage = new ArrayList();
		JSONObject msgDet = new JSONObject();
		entity.setUserId(userDto.getUserId());
		entity.setUserName(userDto.getUserName());
		entity.setBankId(userDto.getBankId());
		entity.setCellNo(userDto.getCellNo());
		entity.setEmailId(userDto.getEmailId());
		entity.setOrgDept(userDto.getOrgDept());
		entity.setGroup(userDto.getGroup());
		entity.setUserType(userDto.getUserType());

		try {
//			String secret =  EncryptionUtil.encryptPassword(userDto.getPassword());
			if(entity.getGroup().getGroupId()==null) {
				msgDet.put("group.groupId", "Group ID cant be empty");
				customMessage.add(msgDet);
				return new ServiceResponse(Constants.USER.VALID_CODE,Constants.USER.VALID_FAILED,customMessage);
				
			}
			//{"message":"Validation Failed","code":"VALERRCOD","details":[{"cellNo":"Mobile Number length must be between 5 and 36"},{"cellNo":"Invalid Mobile Number. Only Numerics Allowed"}]}
			Optional<Users> user = userRepository.findById(entity.getUserId());
			if (user.isPresent()) {
				oldAuditLogData = convertToJson(user.get());
				throw new RecordCreateException("user.details.psh.VAL0002");
			}
//			if(!userDto.getPassword().equals(userDto.getConfirmPassword())) {
//				msgDet.put("confirmPassword", "Password and Confirm Password must be same.");
//				customMessage.add(msgDet);
//				return new ServiceResponse(Constants.USER.VALID_CODE,Constants.USER.VALID_FAILED,customMessage);
//
//			}
			entity.setCreatedBy(getUserName());
			entity.setCreatedTime(new Date());
			entity.setStatus(Constants.MESSAGE_STATUS.PROCESSED);
			entity.setModifiedBy(getUserName());
			entity.setModifiedTime(new Date());
			entity.setUserCategory(1);
			entity.setPassword("oVh25BqtyGin7R8OuYBTOw==");//enc of D360@123
//			entity.setPassword(secret);
			userRepository.save(entity);
			newAuditLogData = convertToJson(entity);

			return new ServiceResponse(Constants.MESSAGE_STATUS.SUCCESS,
					messageSource.getMessage("user.details.psh.VAL0003", null, LocaleContextHolder.getLocale()), null);
		} catch (RecordCreateException e) {
			throw e;
		} catch (Exception e) {
			logger.error("Error:" + e.getMessage(), e);
			return new ServiceResponse("user.details.psh.VAL0004",
					messageSource.getMessage("user.details.psh.VAL0004", null, LocaleContextHolder.getLocale()), null);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public ServiceResponse updateUser(UsersDto entity, Principal principal)
			throws RecordUpdateException, RecordNotFoundException {
		List<JSONObject> customMessage = new ArrayList();
		JSONObject msgDet = new JSONObject();
		try {
			Optional<Users> user = userRepository.findById(entity.getUserId());
			if (!user.isPresent()) {
				throw new RecordNotFoundException("user.details.psh.VAL0001");
			}
			Users newEntity = user.get();
//			if(!entity.getPassword().equals(entity.getConfirmPassword())) {
//				msgDet.put("confirmPassword", "Password and Confirm Password must be same.");
//				customMessage.add(msgDet);
//				return new ServiceResponse(Constants.USER.VALID_CODE,Constants.USER.VALID_FAILED,customMessage);
//
//			}
			
//			String secret =  EncryptionUtil.encryptPassword(entity.getPassword());
			
			BeanUtils.copyProperties(entity, newEntity);
			entity.setPassword("oVh25BqtyGin7R8OuYBTOw==");//enc of D360@123
//			newEntity.setPassword(secret);
			newEntity.setStatus(Constants.MESSAGE_STATUS.PROCESSED);
			newEntity.setModifiedBy(getUserName());
			newEntity.setModifiedTime(new Date());
			userRepository.save(newEntity);
			return new ServiceResponse(Constants.MESSAGE_STATUS.SUCCESS,
					messageSource.getMessage("user.details.psh.VAL0005", null, LocaleContextHolder.getLocale()), null);
		} catch (RecordNotFoundException e) {
			throw e;
		} catch (Exception e) {
			logger.error("Error:" + e.getMessage(), e);
			return new ServiceResponse("user.details.psh.VAL0006",
					messageSource.getMessage("user.details.psh.VAL0006", null, LocaleContextHolder.getLocale()), null);
		}

	}

	@Override
	public ServiceResponse deleteUserById(String id, Principal principal)
			throws RecordNotFoundException, RecordUpdateException {

		try {
			Optional<Users> user = userRepository.findById(id);
			if (!user.isPresent()) {
				throw new RecordNotFoundException("user.details.psh.VAL0001");
			}
			Users existingUsers = user.get();
			
			if (existingUsers.getStatus().contentEquals(Constants.MESSAGE_STATUS.DELETED)) {
				return new ServiceResponse(Constants.MESSAGE_STATUS.FAILED, messageSource
						.getMessage("user.details.psh.VAL0019", null, LocaleContextHolder.getLocale()), null);
			}
			
			existingUsers.setStatus(Constants.MESSAGE_STATUS.DELETE);
			existingUsers.setModifiedBy(getUserName());
			existingUsers.setModifiedTime(new Date());
			userRepository.save(existingUsers);
			
			return new ServiceResponse(Constants.MESSAGE_STATUS.DELETE,
					messageSource.getMessage("user.details.psh.VAL0015", null, LocaleContextHolder.getLocale()), null);
		} catch (Exception e) {
			logger.error("Error:" + e.getMessage(), e);
			return new ServiceResponse(Constants.MESSAGE_STATUS.FAILED,
					messageSource.getMessage("user.details.psh.VAL0016", null, LocaleContextHolder.getLocale()), null);
		}

	}



	@Override
	public ServiceResponse verifyUser(String userId, Principal principal) throws RecordNotFoundException{
		try {
			userId = userId.replaceAll("\"", "");
			Optional<Users> user = userRepository.findById(userId);
			if (!user.isPresent()) {
				throw new RecordNotFoundException("user.details.psh.VAL0001");
			}
			Users existingUsers = user.get();
			
			if (existingUsers.getStatus().contentEquals(Constants.MESSAGE_STATUS.VERIFIED)) {
				return new ServiceResponse(Constants.MESSAGE_STATUS.FAILED, messageSource
						.getMessage("user.details.psh.VAL0017", null, LocaleContextHolder.getLocale()), null);
			}
			if (existingUsers.getStatus().contentEquals(Constants.MESSAGE_STATUS.DELETED)) {
				return new ServiceResponse(Constants.MESSAGE_STATUS.FAILED, messageSource
						.getMessage("user.details.psh.VAL0018", null, LocaleContextHolder.getLocale()), null);
			}
			
			if(existingUsers.getModifiedBy().equalsIgnoreCase(getUserName())) {
				return new ServiceResponse(Constants.MESSAGE_STATUS.FAILED,
						messageSource.getMessage("user.details.psh.VAL0009", null, LocaleContextHolder.getLocale()), null);
			}
			
			if(existingUsers.getStatus().equals(Constants.MESSAGE_STATUS.DELETE)) {
				existingUsers.setStatus(Constants.MESSAGE_STATUS.DELETED);
				existingUsers.setModifiedBy(getUserName());
				existingUsers.setModifiedTime(new Date());
				userRepository.save(existingUsers);
				
				return new ServiceResponse(Constants.MESSAGE_STATUS.SUCCESS,
						messageSource.getMessage("user.details.psh.VAL0007", null, LocaleContextHolder.getLocale()), null);
			
			}
			
			
			existingUsers.setStatus(Constants.MESSAGE_STATUS.VERIFIED);
			existingUsers.setModifiedBy(getUserName());
			existingUsers.setModifiedTime(new Date());
			
			userRepository.save(existingUsers);
			return new ServiceResponse(Constants.MESSAGE_STATUS.SUCCESS,
					messageSource.getMessage("user.details.psh.VAL0007", null, LocaleContextHolder.getLocale()), null);
		} 
		catch(RecordNotFoundException e) {
			throw e;
			
		}
		catch (Exception e) {
			logger.error("Error:" + e.getMessage(), e);
			return new ServiceResponse("user.details.psh.VAL0008",
					messageSource.getMessage("user.details.psh.VAL0008", null, LocaleContextHolder.getLocale()), null);
		}
	}
	
	@SuppressWarnings("unchecked")
	private JSONArray countByStatus(Specification<Users> spec) {
		JSONArray array = new JSONArray();
		try {
			List<Users> headerList = userRepository.findAll(spec);
			Map<String, Long> countByStatus = headerList.stream()
					.collect(Collectors.groupingBy(Users::getStatus, Collectors.counting()));
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
	
	@SuppressWarnings("unchecked")
	@Override
	public JSONObject searchByLimit(String searchParam, int start, int pageSize) {
		JSONObject result = new JSONObject();
		try {
			Pageable pageable = PageRequest.of(start / pageSize, pageSize);
			Specification<Users> spec = SecurityUserSpec.getUserSpec(searchParam);
			Page<Users> usersList = userRepository.findAll(spec, pageable);
			JSONArray array = new JSONArray();
			JSONArray countByStatus = countByStatus(spec);
			for (Users users : usersList) {
				JSONObject obj = new JSONObject();
				obj.put("userId", users.getUserId());
				obj.put("groupId", users.getGroup().getGroupId());
				obj.put("groupName", users.getGroup().getGroupName());
				obj.put("userName", users.getUserName());
				obj.put("status", users.getStatus());
				obj.put("emailId", users.getEmailId());
				obj.put("cellPhone", users.getCellNo());
				array.add(obj);
			}
			result.put("aaData", array);
			result.put("iTotalDisplayRecords", userRepository.findAll(spec).size());
			result.put("iTotalRecords", userRepository.findAll(spec).size());
			result.put("countByStatus", countByStatus);
		} catch (Exception e) {
			logger.error("Error : " + e.getMessage(), e);
		}
		return result;
	}
	String convertToJson(Users entity) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			return mapper.writeValueAsString(entity);
		} catch (Exception e) {
			logger.error("Error : " + e.getMessage(), e);
		}
		return "";
	}

	@SuppressWarnings("unchecked")
	@Override
	public JSONObject getServiceIdDrop() {
		JSONObject res = new JSONObject();
		try {
			List<String> groupId = userRepository.findAll(Constants.MESSAGE_STATUS.VERIFIED);
			res.put(Constants.GROUP_ID, groupId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}

	@SuppressWarnings("unchecked")
	@Override
	public JSONObject getBankIdDropDown() {
		JSONObject res = new JSONObject();
		try {
			res.put(Constants.BANK_ID, bankId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}

	@Override
	public String getBankId(String userId) {
		Optional<Users> result = userRepository.findById(userId);
		try {
			if (result.isPresent()) {
				return result.get().getBankId();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";

	}

	@SuppressWarnings("unchecked")
	@Override
	public JSONArray getDepartmentDropDown() {
		JSONArray result = new JSONArray();
		String status = Constants.VERIFIED;
		try {
			List<PshUsrDep> dataList = departmentRepository.getDepDropdown(status);
			for (PshUsrDep data : dataList) {
				JSONObject obj = new JSONObject();
				obj.put("value", data.getId().getDep());
				obj.put("display", data.getDepartmentName());
				result.add(obj);
			}
			return result;
		} catch (Exception e) {
			logger.error("Error : " + e.getMessage(), e);
			return new JSONArray();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public JSONArray getHomeScreenUrlDropDown() {
		JSONArray result = new JSONArray();
		try {
			List<Screen> screenList = screenRepository.findAll();
			for(Screen screen : screenList) {
				JSONObject obj = new JSONObject();
				obj.put("value", screen.getLinkScr());
				obj.put("display", screen.getLinkScr());
				result.add(obj);
			}
		} catch (Exception e) {
			logger.error("Error : " + e.getMessage(), e);
			return new JSONArray();
		}
		return result;
	}
	private String getUserName() {
		Jwt p = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Map<String, Object> claims = p.getClaims();
		return (String) claims.get("preferred_username");
	
	}
}
