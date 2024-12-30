package com.alfaris.ipsh.security.thread.notification;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.alfaris.ipsh.security.client.NotificationServiceFeignClient;
import com.alfaris.ipsh.security.dto.NotificationDTO;
import com.alfaris.ipsh.security.dto.ServiceResponse;
import com.alfaris.ipsh.security.entity.PshUsrNty;
import com.alfaris.ipsh.security.service.SecurityPermissionService;
import com.alfaris.ipsh.security.util.Constants;




@Component
public class NotificationServiceThread implements Runnable {
	private static Logger logger = LogManager.getLogger(NotificationServiceThread.class);

	private NotificationDTO notificationDto;

	@Autowired
	SecurityPermissionService securityPermissionServicet;

	@Autowired
	NotificationServiceFeignClient notificationServiceFeignClient;

	@Override
	public void run() {
		if (notificationDto.getMethod().equals("INITIATE")) {
			intiateNotification();
		} else {
			defferedNotification();
		}
	}

	private void intiateNotification() {
		try {
			String notifyGroupRes = getNotifyGroup(notificationDto.getNotifyGroup(),notificationDto.getScreenId());
			if (StringUtils.isEmpty(notifyGroupRes)) {
				logger.warn("No group configure to send notification for "+notificationDto.getServiceType()+" verification.");
				return;
			}
			PshUsrNty notification = new PshUsrNty();
			notification.setNotificationId(notificationDto.getNotificationId());
			notification.setCreatedBy(notificationDto.getCreatedBy());
			notification.setCreatedOn(new Date());
			notification.setDescription("Notification initiating.");
			notification.setNotificationMessage(notificationDto.getNotificationMessage());
			notification.setNotificationType(Constants.NOTIFICATION_TYPE.TASK);
			notification.setNotifyGroup(notifyGroupRes);
			notification.setServiceType(notificationDto.getServiceType());
			notification.setScreenId(notificationDto.getScreenId());
			notification.setStatus(Constants.MESSAGE_STATUS.INITIATED);
			ServiceResponse response = notificationServiceFeignClient.save(notification);
			logger.info("Response from notification : " + response);
		} catch (Exception e) {
			logger.error("Error : " + e.getMessage(), e);
		}
	}

	private void defferedNotification() {
		try {
			PshUsrNty notification = notificationServiceFeignClient.getById(notificationDto.getNotificationId());
			notification.setActionBy(notificationDto.getCreatedBy());
			notification.setActionOn(new Date());
			notification.setStatus(Constants.MESSAGE_STATUS.COMPLETED);
			notification.setDescription("Action on this notification is being taken.");
			ServiceResponse response = notificationServiceFeignClient.update(notification);
			logger.info("Response from notification : " + response);
		} catch (Exception e) {
			logger.error("Notification error : " + e.getMessage(), e);
		}

	}

	public void setNotificationDto(NotificationDTO notificationDto) {
		this.notificationDto = notificationDto;
	}

	private String getNotifyGroup(String type, String screenId) {
		List<String> list = securityPermissionServicet.getGroupListForNotification(type, screenId);
		if (list == null) {
			return "";
		}
		String groups = "";
		for (String group : list) {
			if (groups.length() == 0) {
				groups = group;
			} else {
				groups = groups + "," + group;
			}
		}
		return groups;
	}
}
