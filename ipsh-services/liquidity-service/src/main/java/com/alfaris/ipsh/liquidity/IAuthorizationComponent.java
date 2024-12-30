package com.alfaris.ipsh.liquidity;

import java.security.Principal;

public interface IAuthorizationComponent {
	boolean hasAccess(String screenId, String accessType);
}
