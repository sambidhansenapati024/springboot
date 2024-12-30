package com.alfaris.ipsh.security.client;

import java.security.Principal;
import java.util.Date;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import com.alfaris.ipsh.security.entity.PshCasHry;
import com.alfaris.ipsh.security.entity.PshCasHryPK;
import com.alfaris.ipsh.security.util.AuditFuctions;

@Component
public class AuditLogUtil {

	private static Logger logger = LogManager.getLogger(AuditLogUtil.class);

	private final AuthServiceFeignClient authServiceFeignClient;

	public AuditLogUtil(AuthServiceFeignClient authServiceFeignClient) {
		this.authServiceFeignClient = authServiceFeignClient;
	}

	public void creatAudit(String oldEntity, String newEntity, AuditFuctions auditFunctions, String screenId,
			String status, String message, Principal principle) {
		try {
			PshCasHryPK id = new PshCasHryPK();
			id.setUserId(getUserName());
			id.setDateTime(new Date());
			id.setFunctionId(auditFunctions.getFunction());
			id.setScreenId(screenId);
			PshCasHry audit = new PshCasHry();
			audit.setActionDescription(message);
			audit.setActionStatus(status);
			audit.setId(id);
			audit.setCurData(newEntity);
			audit.setPrevData(oldEntity);
			authServiceFeignClient.createAuditLog(audit);
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
