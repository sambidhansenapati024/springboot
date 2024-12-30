package com.alfaris.ipsh.authservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="OAUTH_CLIENT_DETAILS", schema = "IPSH")
public class TestClient {
	@Id
	private String CLIENT_ID;

	public String getCLIENT_ID() {
		return this.CLIENT_ID;
	}

	public void setCLIENT_ID(String value) {
		this.CLIENT_ID = value;
	}

	private String RESOURCE_IDS;

	public String getRESOURCE_IDS() {
		return this.RESOURCE_IDS;
	}

	public void setRESOURCE_IDS(String value) {
		this.RESOURCE_IDS = value;
	}

	private String CLIENT_SECRET;

	public String getCLIENT_SECRET() {
		return this.CLIENT_SECRET;
	}

	public void setCLIENT_SECRET(String value) {
		this.CLIENT_SECRET = value;
	}

	private String SCOPE;

	public String getSCOPE() {
		return this.SCOPE;
	}

	public void setSCOPE(String value) {
		this.SCOPE = value;
	}

	private String AUTHORIZED_GRANT_TYPES;

	public String getAUTHORIZED_GRANT_TYPES() {
		return this.AUTHORIZED_GRANT_TYPES;
	}

	public void setAUTHORIZED_GRANT_TYPES(String value) {
		this.AUTHORIZED_GRANT_TYPES = value;
	}

	private String WEB_SERVER_REDIRECT_URI;

	public String getWEB_SERVER_REDIRECT_URI() {
		return this.WEB_SERVER_REDIRECT_URI;
	}

	public void setWEB_SERVER_REDIRECT_URI(String value) {
		this.WEB_SERVER_REDIRECT_URI = value;
	}

	private String AUTHORITIES;

	public String getAUTHORITIES() {
		return this.AUTHORITIES;
	}

	public void setAUTHORITIES(String value) {
		this.AUTHORITIES = value;
	}

	private float ACCESS_TOKEN_VALIDITY;

	public float getACCESS_TOKEN_VALIDITY() {
		return this.ACCESS_TOKEN_VALIDITY;
	}

	public void setACCESS_TOKEN_VALIDITY(float value) {
		this.ACCESS_TOKEN_VALIDITY = value;
	}

	private float REFRESH_TOKEN_VALIDITY;

	public float getREFRESH_TOKEN_VALIDITY() {
		return this.REFRESH_TOKEN_VALIDITY;
	}

	public void setREFRESH_TOKEN_VALIDITY(float value) {
		this.REFRESH_TOKEN_VALIDITY = value;
	}

	private String ADDITIONAL_INFORMATION;

	public String getADDITIONAL_INFORMATION() {
		return this.ADDITIONAL_INFORMATION;
	}

	public void setADDITIONAL_INFORMATION(String value) {
		this.ADDITIONAL_INFORMATION = value;
	}

	private String AUTOAPPROVE;

	public String getAUTOAPPROVE() {
		return this.AUTOAPPROVE;
	}

	public void setAUTOAPPROVE(String value) {
		this.AUTOAPPROVE = value;
	}

	public TestClient(String CLIENT_ID_, String RESOURCE_IDS_, String CLIENT_SECRET_, String SCOPE_,
			String AUTHORIZED_GRANT_TYPES_, String WEB_SERVER_REDIRECT_URI_, String AUTHORITIES_,
			float ACCESS_TOKEN_VALIDITY_, float REFRESH_TOKEN_VALIDITY_, String ADDITIONAL_INFORMATION_,
			String AUTOAPPROVE_) {
		this.CLIENT_ID = CLIENT_ID_;
		this.RESOURCE_IDS = RESOURCE_IDS_;
		this.CLIENT_SECRET = CLIENT_SECRET_;
		this.SCOPE = SCOPE_;
		this.AUTHORIZED_GRANT_TYPES = AUTHORIZED_GRANT_TYPES_;
		this.WEB_SERVER_REDIRECT_URI = WEB_SERVER_REDIRECT_URI_;
		this.AUTHORITIES = AUTHORITIES_;
		this.ACCESS_TOKEN_VALIDITY = ACCESS_TOKEN_VALIDITY_;
		this.REFRESH_TOKEN_VALIDITY = REFRESH_TOKEN_VALIDITY_;
		this.ADDITIONAL_INFORMATION = ADDITIONAL_INFORMATION_;
		this.AUTOAPPROVE = AUTOAPPROVE_;
	}

	public TestClient() {
		super();
	}
}