package com.alfaris.ipsh.authservice.entity;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;



@Entity
@Table(name="UPS_CST_DET")
public class Client {
    @Id
    @Column(name="CLIENT_ID")
    private String clientId;    
    @Column(name = "PROFILE_ID")
	private String  profileId; 
    @Column(name="client_name")
    private String clientName;
    @Column(name="customer_base_no")
    private String customerBaseNumber;    
    @Column(name="category")
    private String category;   
    @Column(name="client_cpt")
    private String clientCpt;  
    @Column(name="PBK")
    @Lob
    private byte[] pbk;    
    @Column(name="PRK")
    @Lob
    private byte[] prk;
    @Column(name="PBK_FILE_NAME")
    private String pbkFileName;
    @Column(name="PRK_FILE_NAME")
    private String prkFileName;
    @Column(name="file_act")
    private int fileAct;    
    @Column(name="b2b")
    private int b2b;  
    @Column(name="fin")
    private int fin;  
    @Column(name="collection")
    private int collection;    
    @Column(name="primary_acc_no")
    private String primaryAccno;    
    @Column(name="accm_from_acc")
    private String accmFromAcc;  
    @Column(name="accm_to_acc")
    private String accmToAcc;
    private String bic;
    @Column(name="daily_limit")
    private double dailyLimit;
    private String status;
    @Column(name="rtl_crd_lmt")
    private double retailCreditLimit;
    @Column(name="fcy_lmt")
    private double fcyLimit;
    @Column(name="val_date_change")
    private int valueDateChange;
    @Column(name="internal_acc_no")
    private String internalAccountNumber;   
	@Column(name="user_id")
	private String createdBy;
	@Column(name="cre_date")
	@Temporal(TemporalType.TIMESTAMP) 
	private Date createdDate;
	@Column(name="ver_id")
	private String modifiedBy;
	@Column(name="ver_date")
	@Temporal(TemporalType.TIMESTAMP) 
	private Date modifiedDate;
	@Column(name="POS_CUST_ID")
	private String posId;
	@Column(name="EST_ID")
	private String establishmentId;
	@Column(name="MOL_EST_ID")
	private String molEstablishmentId;
	@Column(name="CLIENT_CODE")
	private String clientCode;
	@Column(name="REGION")
	private String region;
	@Column(name="EMAIL")
    private int email;
    @Column(name="CLIENT_IBAN_CODE")
    private String clientIbanCode;
    @Column(name="SCHEME_CODE")
    private Integer schemeCode;
    @Column(name="PROFILE_EXPIRY_DATE")
    @Temporal(TemporalType.DATE) 
    private Date profileExpiryDate;
    @Column(name="SEC_PROFILE_ID")
    private String secProfileId;
    @Column(name="CPT_EXP_DATE")
    @Temporal(TemporalType.DATE) 
    private Date certificateExpDate;
    
    @Column(name="ID_NUMBER")
    private String idNumber;    
    @Column(name = "ID_TYPE")
	private String  idType;
    
    @Column(name = "PAY_CRD_ID")
  	private String  payrollCardId; 
    
    @Column(name = "SCHEME_SEQ")
    private Integer  schemeSeq;
  
	public String getPayrollCardId() {
		return payrollCardId;
	}
	public void setPayrollCardId(String payrollCardId) {
		this.payrollCardId = payrollCardId;
	}
	public String getIdNumber() {
		return idNumber;
	}
	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}
	public String getIdType() {
		return idType;
	}
	public void setIdType(String idType) {
		this.idType = idType;
	}
	public String getProfileId() {
		return profileId;
	}
	public void setProfileId(String profileId) {
		this.profileId = profileId;
	}
	public Date getProfileExpiryDate() {
		return profileExpiryDate;
	}
	public void setProfileExpiryDate(Date profileExpiryDate) {
		this.profileExpiryDate = profileExpiryDate;
	}
	public String getSecProfileId() {
		return secProfileId;
	}
	public void setSecProfileId(String secProfileId) {
		this.secProfileId = secProfileId;
	}
	public Integer getSchemeCode() {
		return schemeCode;
	}
	public void setSchemeCode(Integer schemeCode) {
		this.schemeCode = schemeCode;
	}
	public String getClientIbanCode() {
		return clientIbanCode;
	}
	public void setClientIbanCode(String clientIbanCode) {
		this.clientIbanCode = clientIbanCode;
	}
	public int getEmail() {
		return email;
	}
	public void setEmail(int email) {
		this.email = email;
	}
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	public String getEstablishmentId() {
		return establishmentId;
	}
	public void setEstablishmentId(String establishmentId) {
		this.establishmentId = establishmentId;
	}
	public String getMolEstablishmentId() {
		return molEstablishmentId;
	}
	public void setMolEstablishmentId(String molEstablishmentId) {
		this.molEstablishmentId = molEstablishmentId;
	}
	public String getClientCode() {
		return clientCode;
	}
	public void setClientCode(String clientCode) {
		this.clientCode = clientCode;
	}
	public String getPosId() {
		return posId;
	}
	public void setPosId(String posId) {
		this.posId = posId;
	}
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	public String getClientName() {
		return clientName;
	}
	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
	public String getCustomerBaseNumber() {
		return customerBaseNumber;
	}
	public void setCustomerBaseNumber(String customerBaseNumber) {
		this.customerBaseNumber = customerBaseNumber;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getClientCpt() {
		return clientCpt;
	}
	public void setClientCpt(String clientCpt) {
		this.clientCpt = clientCpt;
	}
	public byte[] getPbk() {
		return pbk;
	}
	public void setPbk(byte[] pbk) {
		this.pbk = pbk;
	}
	public byte[] getPrk() {
		return prk;
	}
	public void setPrk(byte[] prk) {
		this.prk = prk;
	}
	public int getFileAct() {
		return fileAct;
	}
	public void setFileAct(int fileAct) {
		this.fileAct = fileAct;
	}
	public int getB2b() {
		return b2b;
	}
	public void setB2b(int b2b) {
		this.b2b = b2b;
	}
	public int getCollection() {
		return collection;
	}
	public void setCollection(int collection) {
		this.collection = collection;
	}
	public String getPrimaryAccno() {
		return primaryAccno;
	}
	public void setPrimaryAccno(String primaryAccno) {
		this.primaryAccno = primaryAccno;
	}
	public String getAccmFromAcc() {
		return accmFromAcc;
	}
	public void setAccmFromAcc(String accmFromAcc) {
		this.accmFromAcc = accmFromAcc;
	}
	public String getAccmToAcc() {
		return accmToAcc;
	}
	public void setAccmToAcc(String accmToAcc) {
		this.accmToAcc = accmToAcc;
	}
	/*public List<Product> getProduct() {
		return product;
	}
	public void setProduct(List<Product> product) {
		this.product = product;
	}*/
	public String getPbkFileName() {
		return pbkFileName;
	}
	public void setPbkFileName(String pbkFileName) {
		this.pbkFileName = pbkFileName;
	}
	public String getPrkFileName() {
		return prkFileName;
	}
	public void setPrkFileName(String prkFileName) {
		this.prkFileName = prkFileName;
	}
	public String getBic() {
		return bic;
	}
	public void setBic(String bic) {
		this.bic = bic;
	}
	public double getDailyLimit() {
		return dailyLimit;
	}
	public void setDailyLimit(double dailyLimit) {
		this.dailyLimit = dailyLimit;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public double getRetailCreditLimit() {
		return retailCreditLimit;
	}
	public void setRetailCreditLimit(double retailCreditLimit) {
		this.retailCreditLimit = retailCreditLimit;
	}
	public double getFcyLimit() {
		return fcyLimit;
	}
	public void setFcyLimit(double fcyLimit) {
		this.fcyLimit = fcyLimit;
	}
	public int getValueDateChange() {
		return valueDateChange;
	}
	public void setValueDateChange(int valueDateChange) {
		this.valueDateChange = valueDateChange;
	}
	public int getFin() {
		return fin;
	}
	public void setFin(int fin) {
		this.fin = fin;
	}
	public String getInternalAccountNumber() {
		return internalAccountNumber;
	}
	public void setInternalAccountNumber(String internalAccountNumber) {
		this.internalAccountNumber = internalAccountNumber;
	}		
	
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public String getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	public Date getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	public Date getCertificateExpDate() {
		return certificateExpDate;
	}
	public void setCertificateExpDate(Date certificateExpDate) {
		this.certificateExpDate = certificateExpDate;
	}

    public Integer getSchemeSeq() {
        return this.schemeSeq;
    }

    public void setSchemeSeq(Integer schemeSeq) {
        this.schemeSeq = schemeSeq;
    }
	
	
	
	
	/*
	@Override
	public String toString() {
		return "{\"clientId\":\"" + clientId + "\", \"clientName\":\"" + clientName
				+ "\", \"customerBaseNumber\":\"" + customerBaseNumber + "\", \"category\":\""
				+ category + "\", \"clientCpt\":\"" + clientCpt + "\", \"profileId\":\""
				+ profileId + "\", \"secProfileId\":\"" + secProfileId
				+ "\", \"pbkFileName\":\"" + pbkFileName + "\", \"prkFileName\":\""
				+ prkFileName + "\", \"fileAct\":\"" + fileAct + "\", \"b2b\":\"" + b2b
				+ "\", \"collection\":\"" + collection + "\", \"fin\":\"" + fin
				+ "\", \"primaryAccno\":\"" + primaryAccno + "\", \"accmFromAcc\":\""
				+ accmFromAcc + "\", \"accmToAcc\":\"" + accmToAcc + "\", \"bic\":\"" + bic
				+ "\", \"status\":\"" + status + "\", \"retailCreditLimit\":\""
				+ retailCreditLimit + "\", \"fcyLimit\":\"" + fcyLimit
				+ "\", \"valueDateChange\":\"" + valueDateChange
				+ "\", \"internalAccountNumber\":\"" + internalAccountNumber + "\",\"email\":\"" + email
				+ "\", \"clientIbanCode\":\"" + clientIbanCode + "\",\"schemeCode\":\"" + schemeCode
				+ "\", \"createdBy\":\"" + createdBy + "\",\"createdDate\":\"" + createdDate 
				+ "\", \"modifiedBy\":\"" + modifiedBy + "\",\"modifiedDate\":\"" + modifiedDate +"\"}";
	}	*/
}
