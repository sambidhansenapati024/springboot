package com.alfaris.ipsh.security.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.alfaris.ipsh.security.client.NotificationServiceFeignClient;
import com.alfaris.ipsh.security.dto.NotificationDTO;
import com.alfaris.ipsh.security.entity.Group;
import com.alfaris.ipsh.security.thread.notification.NotificationServiceThread;
import com.alfaris.ipsh.security.util.Constants;




@Service
public class NotficationServiceImpl implements NotificationService {
	private static Logger logger = LogManager.getLogger(NotficationServiceImpl.class);

	@Autowired
	private TaskExecutor taskExecutor;

	@Autowired
	private ApplicationContext applicationContext;

	@Autowired
	NotificationServiceFeignClient notificationServiceFeignClient;

	
	@Override
	public void sendNotificationSecGroup(Group group, String action, String notifyGroup) {
		try {
			String message = "";
			String createdBy = "";
			if ("C".equals(action)) {
				message = "A Group has been created for the Group ID : " + group.getGroupId()
						+ " ,Please verify.";
				createdBy = group.getCreatedBy();
			} else if ("M".equals(action)) {
				message = "A Group for the Group ID : " + group.getGroupId()
						+ " has been modified,Please verify.";
				createdBy = group.getModifiedBy();
			} /*
				 * else if ("D".equals(action)) { message = "Group for the Group ID : " +
				 * group.getGroupId() + " has been deleted, Please verify."; createdBy =
				 * group.getModifiedBy(); }
				 */
			ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
			RequestContextHolder.setRequestAttributes(sra, true);
			NotificationServiceThread myThread = applicationContext.getBean(NotificationServiceThread.class);
			NotificationDTO notificationDTO = new NotificationDTO();
			notificationDTO.setNotificationId(group.getNotificationId());
			notificationDTO.setNotificationMessage(message);
			notificationDTO.setCreatedBy(createdBy);
			notificationDTO.setMethod("INITIATE");
			notificationDTO.setScreenId(Constants.SCREEN_ID.SEC_GRP);
			notificationDTO.setServiceType(Constants.SEC_GRP_SCREEN_NAME);
			notificationDTO.setNotifyGroup(notifyGroup);
			myThread.setNotificationDto(notificationDTO);
			taskExecutor.execute(myThread);
		} catch (Exception e) {
			logger.error("Notification error : " + e.getMessage(), e);
		}
	}
	
	@Override
	public void defferedNotificationSecGroup(Group group) {
		try {
			ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
			RequestContextHolder.setRequestAttributes(sra, true);
			NotificationServiceThread myThread = applicationContext.getBean(NotificationServiceThread.class);
			NotificationDTO notificationDTO = new NotificationDTO();
			notificationDTO.setNotificationId(group.getNotificationId());
			notificationDTO.setCreatedBy(group.getModifiedBy());
			notificationDTO.setMethod("DEFER");
			myThread.setNotificationDto(notificationDTO);
			taskExecutor.execute(myThread);
		} catch (Exception e) {
			logger.error("Notification error : " + e.getMessage(), e);
		}

	}
}
