package com.alfaris.ipsh.subscription;

import java.security.Principal;

public interface IAuthorizationComponent {
	boolean hasAccess(String screenId, String accessType);
}
