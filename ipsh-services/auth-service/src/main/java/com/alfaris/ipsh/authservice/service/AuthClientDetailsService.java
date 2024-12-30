package com.alfaris.ipsh.authservice.service;




import org.springframework.stereotype.Service;


import com.alfaris.ipsh.authservice.repository.AuthClientRepository;

@Service
public class AuthClientDetailsService
//implements ClientDetailsService
{
	private final AuthClientRepository authClientRepository;

	public AuthClientDetailsService(AuthClientRepository authClientRepository) {
		this.authClientRepository = authClientRepository;
	}

//	@Override
//	public ClientDetails loadClientByClientId(String clientId) {
//		Optional<TestClient> client = Optional
//				.ofNullable(authClientRepository.findById(clientId).orElseThrow(IllegalArgumentException::new));
//		if (client.isPresent()) {
//			TestClient data = client.get();
//			BaseClientDetails clientDetails = new BaseClientDetails();
//			clientDetails.setClientId(clientId);
//			clientDetails.setClientSecret(data.getCLIENT_SECRET());
//			String[] scopeArray = data.getSCOPE().split(",");
//			List<String> scopes = Arrays.asList(scopeArray);
//			// scopes.add("server");
//			// scopes.add("read");
//			clientDetails.setScope(scopes);
//			String[] authoritiesArray = data.getAUTHORIZED_GRANT_TYPES().split(",");
//			Collection<String> authorities = Arrays.asList(authoritiesArray);
//			// authorities.add("refresh_token");
//			// authorities.add("client_credentials");
//			// authorities.add("password");
//			clientDetails.setAuthorizedGrantTypes(authorities);
//			clientDetails.setRefreshTokenValiditySeconds((int) data.getREFRESH_TOKEN_VALIDITY());
//			clientDetails.setAccessTokenValiditySeconds((int) data.getACCESS_TOKEN_VALIDITY());
//			return clientDetails;
//		} else {
//			return null;
//		}
//
//	}
}
