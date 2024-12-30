package com.alfaris.ipsh.security.service;

import com.alfaris.ipsh.security.entity.Group;

public interface NotificationService {

	void sendNotificationSecGroup(Group group, String action, String notifyGroup);

	void defferedNotificationSecGroup(Group Group);


}
