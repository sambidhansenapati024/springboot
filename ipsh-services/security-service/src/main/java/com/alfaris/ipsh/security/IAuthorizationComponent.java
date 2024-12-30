package com.alfaris.ipsh.security;

import java.security.Principal;

public interface IAuthorizationComponent {
	boolean hasAccess(String screenId, String accessType);
}
