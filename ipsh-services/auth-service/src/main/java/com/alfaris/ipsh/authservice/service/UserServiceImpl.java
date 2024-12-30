package com.alfaris.ipsh.authservice.service;

import java.io.File;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import com.alfaris.ipsh.authservice.entity.*;
import com.alfaris.ipsh.authservice.repository.*;
import jakarta.servlet.http.HttpServletRequest;
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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import com.alfaris.ipsh.authservice.dto.AuditKeyDTO;
import com.alfaris.ipsh.authservice.dto.ServiceResponse;
import com.alfaris.ipsh.authservice.exception.RecordCreateException;
import com.alfaris.ipsh.authservice.exception.RecordNotFoundException;
import com.alfaris.ipsh.authservice.exception.RecordUpdateException;
import com.alfaris.ipsh.authservice.repository.specification.CaseAuditSpecification;
import com.alfaris.ipsh.authservice.repository.specification.UserSpecs;
import com.alfaris.ipsh.authservice.util.Constants;
import org.springframework.util.StringUtils;

@Service
public class UserServiceImpl implements UserService {

	private static Logger logger = LogManager.getLogger(UserServiceImpl.class);

	@Autowired
	UserRepository userRepository;

	@Autowired  
	ScreenRepository screenRepository;

	@Autowired
	GroupScreenRepository groupScreenRepository;

	@Autowired
	GroupRepository groupRepository;

	@Autowired
	UserThemeConfigRepository userThemeConfigRepository;

	@Autowired
	MessageSource messageSource;

	@Autowired
	AuditLogRepository auditLogRepository;

	@Autowired
	UserLogRepository userLogRepository;

	@Autowired
	DataTableColumnConfigRepository dataTableColumnConfigRepository;

	@Value("${menu}")
	String menu;

	@Value("${log.path}")
	String logFilePath;

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
			return user.get();
		} else {
			throw new RecordNotFoundException("user.VAL0001");
		}
	}

	@Override
	public ServiceResponse createUser(Users entity) throws RecordCreateException {
		try {
			Optional<Users> user = userRepository.findById(entity.getUserId());
			if (user.isPresent()) {
				throw new RecordCreateException("user.VAL0002");
			}
			entity.setCreatedBy(getUserName());
			entity.setCreatedTime(new Date());
			userRepository.save(entity);
			return new ServiceResponse(Constants.MESSAGE_STATUS.SUCCESS,
					messageSource.getMessage("user.VAL0003", null, LocaleContextHolder.getLocale()), null);
		} catch (RecordCreateException e) {
			throw e;
		} catch (Exception e) {
			logger.error("Error:" + e.getMessage(), e);
			return new ServiceResponse("user.VAL0004",
					messageSource.getMessage("user.VAL0004", null, LocaleContextHolder.getLocale()), null);
		}
	}

	@Override
	public ServiceResponse updateUser(Users entity) throws RecordUpdateException, RecordNotFoundException {
		try {
			Optional<Users> user = userRepository.findById(entity.getUserId());
			if (!user.isPresent()) {
				throw new RecordNotFoundException("user.VAL0001");
			}
			Users newEntity = user.get();
			BeanUtils.copyProperties(entity, newEntity);
			newEntity.setModifiedBy(getUserName());
			newEntity.setModifiedTime(new Date());
			userRepository.save(newEntity);
			return new ServiceResponse(Constants.MESSAGE_STATUS.SUCCESS,
					messageSource.getMessage("user.VAL0005", null, LocaleContextHolder.getLocale()), null);
		} catch (RecordNotFoundException e) {
			throw e;
		} catch (Exception e) {
			logger.error("Error:" + e.getMessage(), e);
			return new ServiceResponse("user.VAL0006",
					messageSource.getMessage("user.VAL0006", null, LocaleContextHolder.getLocale()), null);
		}

	}

	@Override
	public ServiceResponse deleteUserById(String id) throws RecordNotFoundException, RecordUpdateException {

		try {
			Optional<Users> user = userRepository.findById(id);
			if (!user.isPresent()) {
				throw new RecordNotFoundException("user.VAL0001");
			}
			Users existingUsers = user.get();
			existingUsers.setStatus(Constants.MESSAGE_STATUS.DELETED);
			return updateUser(existingUsers);
		} catch (Exception e) {
			logger.error("Error:" + e.getMessage(), e);
			return new ServiceResponse("user.VAL0006",
					messageSource.getMessage("user.VAL0006", null, LocaleContextHolder.getLocale()), null);
		}

	}

	@Override
	public JSONObject searchByLimit(String searchParam, int start, int pageSize) {
		JSONObject result = new JSONObject();
		try {
			Pageable pageable = PageRequest.of(start / pageSize, pageSize);
			Page<Users> usersList = userRepository.findAll(UserSpecs.getEmployeesByNameSpec(searchParam), pageable);
			JSONArray array = new JSONArray();
			for (Users users : usersList) {
				JSONObject obj = new JSONObject();
				obj.put("userId", users.getUserId());
				obj.put("groupId", users.getGroup().getGroupId());
				obj.put("groupName", users.getGroup().getGroupName());
				obj.put("userName", users.getUserName());
				obj.put("status", users.getStatus());
				obj.put("emailId", users.getEmaild());
				obj.put("cellPhone", users.getCellNo());
				array.add(obj);
			}
			result.put("aaData", array);
			result.put("iTotalDisplayRecords", userRepository.findAll().size());
			result.put("iTotalRecords", userRepository.findAll().size());
		} catch (Exception e) {
			logger.error("Error : " + e.getMessage(), e);
		}
		return result;
	}

	@Override
	public JSONArray accessScreenList(String userId) {
		JSONArray arr = new JSONArray();
		try {
			Optional<Users> user = userRepository.findById(userId);
			if (!user.isPresent()) {
				return null;// not found
			}

			String[] menuItemsFromProperty = menu.split(";");
			Map<String, String> propMenuMap = new HashMap<>();
			for (int i = 0; i < menuItemsFromProperty.length; i++) {
				String[] propMenuItems = menuItemsFromProperty[i].split("->");
				List<String> screenList = getList(propMenuItems[1]);
				for (String screen : screenList) {
					propMenuMap.put(screen, propMenuItems[0]);
				}

			}

			List<String> screenIdListByUser = getScreenList(user);
			List<Screen> accssibleScreenList = screenRepository.findAllById(screenIdListByUser);
			Map<String, JSONArray> finalResultMap = new HashMap<>();
			for (Screen screen : accssibleScreenList) {
				if (!propMenuMap.containsKey(screen.getScreenId())) {
					continue;
				}
				String parentMenu = propMenuMap.get(screen.getScreenId());
				if (finalResultMap.containsKey(parentMenu)) {
					JSONArray childrens = finalResultMap.get(parentMenu);
					JSONObject children = new JSONObject();
					children.put("name", screen.getScreenName());
					children.put("url", screen.getLinkScr());
					children.put("css", screen.getLinkScrAr());
					children.put("id", screen.getScreenId());
					childrens.add(children);
					finalResultMap.put(parentMenu, childrens); 
				} else {
					JSONArray childrens = new JSONArray();
					JSONObject children = new JSONObject();
					children.put("name", screen.getScreenName());
					children.put("url", screen.getLinkScr());
					children.put("css", screen.getLinkScrAr());
					children.put("id", screen.getScreenId());
					childrens.add(children);
					finalResultMap.put(parentMenu, childrens);
				}

			}
			Set<String> parentKeySet = finalResultMap.keySet();
			for (String parent : parentKeySet) {
				JSONObject finalObj = new JSONObject();
				finalObj.put("name", parent);
				if ("Security".equals(parent)) {
					finalObj.put("index", 7);
					finalObj.put("css", "fas fa-user-shield");
				} else if ("Parameter".equals(parent)) {
					finalObj.put("index", 6);
					finalObj.put("css", "fas fa-tools");

				} else if ("Liquidity".equals(parent)) {
					finalObj.put("index", 11);
					finalObj.put("css", "fas fa-coins");

				} else if ("PSH Studio".equals(parent)) {
					finalObj.put("index", 1);
					finalObj.put("css", "fas fa-sitemap");
				} else if ("SARIE(IPS)".equals(parent)) {
					finalObj.put("index", 3);
					finalObj.put("css", "fas fa-credit-card");
				} else if ("Stand-In Process".equals(parent)) {
					finalObj.put("index", 8);
					finalObj.put("css", "fas fa-tasks");
				} else if ("WorkflowSystem".equals(parent)) {
					finalObj.put("index", 9);
					finalObj.put("css", "fas fa-project-diagram");
				} else if ("Utility Bill".equals(parent)) {
					finalObj.put("index", 5);
					finalObj.put("css", "fas fa-money-bill");
				} else if ("RTGS".equals(parent)) {
					finalObj.put("index", 4);
					finalObj.put("css", "fas fa-hand-holding-usd");
				} else if ("PSH Core".equals(parent)) {
					finalObj.put("index", 2);
					finalObj.put("css", "fas fa-list");
				} else if ("RTP".equals(parent)) {
					finalObj.put("index", 10);
					finalObj.put("css", "fas fa-money-check-alt");
				} else if ("Template Definition".equals(parent)) {
					finalObj.put("index", 12);
					finalObj.put("css", "fas fa-list-alt");
				}else if ("Notification".equals(parent)) {
					finalObj.put("index", 13);
					finalObj.put("css", "fas fa-bell");
				}else if ("Subscription".equals(parent)) {
					finalObj.put("index", 14);
					finalObj.put("css", "fas fa-money-check-alt");
				}  else {
					finalObj.put("index", 10);
					finalObj.put("css", "fa-cog");
				}

				finalObj.put("id", java.util.UUID.randomUUID());
				finalObj.put("childMenu", finalResultMap.get(parent));
				arr.add(finalObj);
			}
		} catch (Exception e) {
			logger.error("Error : " + e.getMessage(), e);
		}
		return arr;
	}

	private List<String> getList(String screenList) {
		String[] result = screenList.split(",");
		return Arrays.asList(result);
	}

	private List<String> getScreenList(Optional<Users> user) {
		List<String> newList = new ArrayList<>();
		List<GroupScreen> screenList = groupScreenRepository.findAllByIdGroupId(user.get().getGroup().getGroupId());
		for (GroupScreen groupScreen : screenList) {
			if ("Y".equals(groupScreen.getDspFl())) {
				newList.add(groupScreen.getId().getScreenId());
			}
		}
		return newList;
	}

	@Override
	public ServiceResponse saveTheme(JSONObject jsonData) throws RecordCreateException {
		try {
			String message = null;
			Optional<UserThemeConfig> user = userThemeConfigRepository.findById(getUserName());
			if (user.isPresent()) {
				throw new RecordCreateException("user.VAL0007");
			}
			UserThemeConfig entity = new UserThemeConfig();
			entity.setUserId(getUserName());
			entity.setConfig(jsonData.toJSONString().getBytes());
			entity.setCreatedBy(getUserName());
			entity.setCreatedOn(new Date());
			userThemeConfigRepository.save(entity);
			return new ServiceResponse(Constants.MESSAGE_STATUS.SUCCESS,
					messageSource.getMessage("user.VAL0008", null, LocaleContextHolder.getLocale()), null);
		} catch (RecordCreateException e) {
			throw e;
		} catch (Exception e) {
			logger.error("Error:" + e.getMessage(), e);
			return new ServiceResponse("user.VAL0009",
					messageSource.getMessage("user.VAL0009", null, LocaleContextHolder.getLocale()), null);
		}
	}

	@Override
	public ServiceResponse updateTheme(JSONObject jsonData) throws RecordNotFoundException, RecordUpdateException {
		try {
			UserThemeConfig entity = new UserThemeConfig();
			entity.setUserId(getUserName());
			entity.setConfig(jsonData.toJSONString().getBytes());
			String message = null;
			Optional<UserThemeConfig> user = userThemeConfigRepository.findById(entity.getUserId());
			if (!user.isPresent()) {
				throw new RecordNotFoundException("user.VAL0010");
			}
			UserThemeConfig newEntity = user.get();
			BeanUtils.copyProperties(entity, newEntity);
			newEntity.setModifiedBy(getUserName());
			newEntity.setModifiedOn(new Date());
			userThemeConfigRepository.save(newEntity);
			return new ServiceResponse(Constants.MESSAGE_STATUS.SUCCESS,
					messageSource.getMessage("user.VAL0011", null, LocaleContextHolder.getLocale()), null);
		} catch (RecordNotFoundException e) {
			throw e;
		} catch (Exception e) {
			logger.error("Error:" + e.getMessage(), e);
			return new ServiceResponse("user.VAL0012",
					messageSource.getMessage("user.VAL0012", null, LocaleContextHolder.getLocale()), null);
		}

	}

	@Override
	public String getUserThemeById(String id) throws RecordNotFoundException {
		Optional<UserThemeConfig> userTheme = userThemeConfigRepository.findById(id);
		if (userTheme.isPresent()) {
			return new String(userTheme.get().getConfig());
		} else {
			throw new RecordNotFoundException("user.VAL0010");
		}
	}

	@Override
	public ServiceResponse saveAuditData(PshCasHry audit) {
		try {
			auditLogRepository.save(audit);
			return new ServiceResponse(Constants.MESSAGE_STATUS.SUCCESS,
					messageSource.getMessage("user.VAL0015", null, LocaleContextHolder.getLocale()), null);
		} catch (Exception e) {
			logger.error("Error:" + e.getMessage(), e);
			return new ServiceResponse("user.VAL0014",
					messageSource.getMessage("user.VAL0014", null, LocaleContextHolder.getLocale()), null);
		}
	}

	@Override
	public PshCasHry getAuditData(AuditKeyDTO auditDto) throws RecordNotFoundException {
		try {
			Date date = new Date(Long.parseLong(auditDto.getDateTime()));
			PshCasHryPK auditKey = new PshCasHryPK();
			auditKey.setDateTime(date);
			auditKey.setFunctionId(auditDto.getFunctionId());
			auditKey.setScreenId(auditDto.getScreenId());
			auditKey.setUserId(auditDto.getUserId());
			Optional<PshCasHry> entity = auditLogRepository.findById(auditKey);
			if (!entity.isPresent()) {
				throw new RecordNotFoundException("user.VAL0016");
			}
			return entity.get();
		} catch (Exception e) {
			logger.error("Error:" + e.getMessage(), e);
			throw new RecordNotFoundException("user.VAL0016");
		}

	}

	@Override
	public JSONObject searchAuditData(String searchParam, int start, int pageSize) {
		JSONObject result = new JSONObject();
		try {
			Pageable pageable = PageRequest.of(start / pageSize, pageSize);
			Page<PshCasHry> logList = auditLogRepository
					.findAll(CaseAuditSpecification.getSearchSpecification(searchParam), pageable);
			JSONArray array = new JSONArray();
			for (PshCasHry log : logList) {
				JSONObject obj = new JSONObject();
				obj.put("userId", log.getId().getUserId());
				obj.put("time", log.getId().getDateTime().toString());
				obj.put("longTime", log.getId().getDateTime().getTime());
				obj.put("functionId", log.getId().getFunctionId());
				obj.put("screenId", log.getId().getScreenId());
				obj.put("status", log.getActionStatus());
				obj.put("description", log.getActionDescription());
				array.add(obj);
			}
			result.put("aaData", array);
			result.put("iTotalDisplayRecords",
					auditLogRepository.findAll(CaseAuditSpecification.getSearchSpecification(searchParam)).size());
			result.put("iTotalRecords",
					auditLogRepository.findAll(CaseAuditSpecification.getSearchSpecification(searchParam)).size());
		} catch (Exception e) {
			logger.error("Error : " + e.getMessage(), e);
		}
		return result;

	}

	@Override
	public JSONObject getApplicationLog() {
		return listFiles(logFilePath, new JSONObject());
	}

	private static JSONObject listFiles(String path, JSONObject obj) {
		File folder = new File(path);

		File[] files = folder.listFiles();
		obj.putIfAbsent("name", folder.getName());
		obj.putIfAbsent("id", UUID.randomUUID().toString());
		JSONArray children = new JSONArray();
		for (File file : files) {
			if (file.isFile()) {
				JSONObject childrenObj = new JSONObject();
				childrenObj.putIfAbsent("name", file.getName());
				childrenObj.putIfAbsent("id", UUID.randomUUID().toString());
				children.add(childrenObj);
			} else if (file.isDirectory()) {
				children.add(listFiles(file.getAbsolutePath(), new JSONObject()));
			}
		}
		obj.put("children", children);
		return obj;
	}

	@Override
	public JSONObject getUserInfo(String name) {
		logger.info("Getting user info for : " + name);
		JSONObject result = new JSONObject();
		DateFormat dateFormatter = new SimpleDateFormat("dd MMM yyyy HH:mm:ss");
		try {
			Optional<Users> userObj = userRepository.findById(name);
			if (userObj.isPresent()) {
				if (userObj.get().getStatus().equals(Constants.MESSAGE_STATUS.VERIFIED)) {

					Users user = userObj.get();
					result.put("userId", user.getUserId());
					result.put("groupId", user.getGroup().getGroupId());
					result.put("userName", user.getUserName());
					result.put("department", user.getOrgDept());
					result.put("userType", user.getUserType());
					if (user.getLogTime() != null) {
						result.put("logOn", dateFormatter.format(user.getLogTime()));

					} else {
						result.put("logOn", "NIL");
					}
					result.put("homeScreenUrl", user.getGroup().getHomeScreenUrl());

				} else {
					result.put("userId", name);
					result.put("message", name + "Not Authorized!");

				}
			} else {
				result.put("userId", name);
				result.put("error", "No user found with username: " + name);
			}
		} catch (Exception e) {
			logger.error("Error : " + e.getMessage(), e);
		}
		return result;
	}

	@Override
	public ServiceResponse saveOrUpdateTableConfig(PshColCnf config) {
		try {
			dataTableColumnConfigRepository.save(config);
			return new ServiceResponse(Constants.MESSAGE_STATUS.SUCCESS,
					messageSource.getMessage("user.VAL0017", null, LocaleContextHolder.getLocale()), null);
		} catch (Exception e) {
			logger.error("Error:" + e.getMessage(), e);
			return new ServiceResponse("user.VAL0014",
					messageSource.getMessage("user.VAL0014", null, LocaleContextHolder.getLocale()), null);
		}
	}

	@Override
	public PshColCnf getTableConfig(String userId, String screenId) throws RecordNotFoundException {
		try {
			Optional<PshColCnf> result = dataTableColumnConfigRepository.findById(new PshColCnfPK(userId, screenId));
			if (result.isPresent()) {
				return result.get();
			}
		} catch (Exception e) {
			logger.error("Error:" + e.getMessage(), e);
		}
		throw new RecordNotFoundException("user.VAL0018");
	}

	@Override
	public void logOn(String logId) {

		try {
			Optional<Users> userObj = userRepository.findById(logId);
			if (userObj.isPresent()) {
				Users user = userObj.get();
				Date date = new Date();
				System.out.println(date);
				user.setLogTime(date);
				userRepository.save(user);
			}
		} catch (Exception e) {
			logger.error("Error : " + e.getMessage(), e);
		}

	}

	@Override
	public void saveUserLog(String userId, String loginStatus, HttpServletRequest request) {
		Eftsecadt userLog = new Eftsecadt();
		EftsecadtPK userLogPk = new EftsecadtPK();

		try {
			String ipAddress = request.getHeader("X-FORWARDED-FOR");
			if (StringUtils.isEmpty(ipAddress)) {
				ipAddress = request.getRemoteAddr();
			}

			if (!StringUtils.isEmpty(userId)) {
				userLogPk.setUserId(userId);
				Date date = new Date();
				userLogPk.setLogTime(date);
				userLog.setId(userLogPk);
				Optional<Users> user = userRepository.findById(userId);
				if (loginStatus.equalsIgnoreCase("S")) {
					userLog.setReason("Logged Out Successfully");
					userLog.setLoginSts(loginStatus);
					if (user.isPresent()) {
						user.get().setLoginStatus(0);
					}
				} else if (loginStatus.equalsIgnoreCase("Success")) {
					userLog.setReason("Logged In Successfully");
					userLog.setLoginSts("S");
					if (user.isPresent()) {
						user.get().setLogTime(userLogPk.getLogTime());
						user.get().setLoginStatus(1);
					}
				} else if (loginStatus.equalsIgnoreCase("FI")) {
					userLog.setReason("User ID is not verified");
					userLog.setLoginSts("F");
				} else {
					userLog.setReason("Invalid User ID Or Password");
					userLog.setLoginSts("F");
				}

				Timestamp ts = new Timestamp(date.getTime());
				userLog.setSysTime(ts);
				if (user.isPresent()) {
//					if (user.get().getOrgDept().equalsIgnoreCase("CRO")) {
//						userLog.setLoginType("DB Login");
//					} else {
//						userLog.setLoginType("LDap Login");
//					}

					userLog.setLoginType("Login");

					userRepository.save(user.get());
				} else {
					userLog.setLoginType("DB Login");
				}
				userLog.setIpid(ipAddress);
				// userLog.setUserType(0);
				userLogRepository.save(userLog);
			}

		} catch (Exception e) {
			logger.error("Error:" + e.getMessage(), e);
		}

	}

	private String getUserName() {
		Jwt p = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Map<String, Object> claims = p.getClaims();
		return (String) claims.get("preferred_username");

	}
}
