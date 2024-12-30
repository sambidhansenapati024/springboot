/*********************************************************************************************
 *                              COPYRIGHT NOTICE
*
*                      Copyright(@2006) by Interland Technology Services PVT. LTD **
*
*      This program is used to monitor the stream control and Stop/Start
*      the streams. The program and related materials are confidential and
*      proprietary of Interland Technology Services PVT. LTD and no part of these materials
*      should be reproduced, published in any forms without the written
*      approval of INTERLAND
*
** Project Name         : iPSH
** Version No           : 1.0.0
** Author               : Sujith K S
** Date Created         : 21-may-2020
** Modification Log     :   
CRId/ProjectId	Date Modified      	User		         Description		
Prod_1.0.0	               			 
**********************************************************************************************/
package com.alfaris.ipsh.liquidity.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name = "PSH_OTR_DET", schema = "IPSH")
public class PshOtrDet implements Serializable {

	private static final long serialVersionUID = 1241850561500405226L;

	@EmbeddedId
	private PshOtrDetPK id;
	
	@Column(name = "dep")
	private String dep;
	
	@Column(name = "instruction_uuid")
	private String instructionUuid;
	
	@Column(name = "instruction_timestamp")
	@Temporal(TemporalType.TIMESTAMP)
	private Date instructionTimestamp;
	
	@Column(name = "product_id")
	private String productId;
	
	@Column(name = "service_id")
	private String serviceId;
	
	@Column(name = "value_date")
	@Temporal(TemporalType.DATE)
	private Date valueDate;
	
	@Column(name = "debit_date")
	@Temporal(TemporalType.DATE)
	private Date debitDate;
	
	@Column(name = "transaction_amount")
	private BigDecimal transactionAmount;
	
	@Column(name = "currency_code")
	private String currencyCode;
	
	@Column(name = "customer_reference")
	private String customerReference;
	
	@Column(name = "ordering_customer_account")
	private String orderingCustomerAccount;
	
	@Column(name = "ordering_Customer_name")
	private String orderingCustomerName;
	
	@Column(name = "ordering_Customer_det1")
	private String  orderingCustomerDet1;
	
	@Column(name = "ordering_Customer_det2")
	private String  orderingCustomerDet2;
	
	@Column(name = "ordering_Customer_det3")
	private String  orderingCustomerDet3;
	
	@Column(name = "ordering_institutio_bic")
	private String orderingInstitutionBic;
	
	@Column(name = "account_with_institution_bic")
	private String accountWithInstitutionBic;
	
	@Column(name = "account_with_institution_id")
	private String accountWithInstitutionId;
	
	@Column(name = "account_with_institution_det1")
	private String accountWithInstitutionDet1;
	
	@Column(name = "account_with_institution_det2")
	private String accountWithInstitutionDet2;
	
	@Column(name = "account_with_institution_det3")
	private String accountWithInstitutionDet3;
	
	@Column(name = "beneficiary_bank_Code")
	private String beneficiaryBankCode;
	
	@Column(name = "beneficiary_customer_account")
	private String beneficiaryCustomerAccount;
	
	@Column(name = "beneficiary_customer_name")
	private String beneficiaryCustomerName;
	
	@Column(name = "beneficiary_customer_add1")
	private String beneficiaryCustomerAdd1;
	
	@Column(name = "beneficiary_customer_add2")
	private String beneficiaryCustomerAdd2;
	
	@Column(name = "beneficiary_customer_add3")
	private String beneficiaryCustomerAdd3;
	
	@Column(name = "purpose_of_the_payment")
	private String purposeOfThePayment;
	
	@Column(name = "payment_detail_1")
	private String paymentDetail1;
	
	@Column(name = "payment_detail_2")
	private String paymentDetail2;
	
	@Column(name = "payment_detail_3")
	private String paymentDetail3;
	
	@Column(name = "payment_detail_4")
	private String paymentDetail4;
	
	@Column(name = "sender_to_receiver_info1")
	private String senderToReceiverInfo1;
	
	@Column(name = "sender_to_receiver_info2")
	private String senderToReceiverInfo2;
	
	@Column(name = "sender_to_receiver_info3")
	private String senderToReceiverInfo3;
	
	@Column(name = "sender_to_receiver_info4")
	private String senderToReceiverInfo4;
	
	@Column(name = "sender_to_receiver_info5")
	private String senderToReceiverInfo5;
	
	@Column(name = "sender_to_receiver_info6")
	private String senderToReceiverInfo6;
	
	@Column(name = "additional_Info1")
	private String additionalInfo1;
	
	@Column(name = "additional_Info2")
	private String additionalInfo2;
	
	@Column(name = "additional_Info3")
	private String additionalInfo3;
	
	@Column(name = "additional_Info4")
	private String additionalInfo4;
	
	@Column(name = "routing_code")
	private String routingCode;
	
	@Column(name = "pro_id")
	private String proId;
	
	@Column(name = "date_cre")
	@Temporal(TemporalType.TIMESTAMP)
	private Date creDate;
	
	@Column(name = "ver_id")
	private String verId;
	
	@Column(name = "settlement_account")
	private String settlementAccount;
	
	@Column(name = "external_ref")
	private String externalRef;
	
	@Column(name = "bank_reference")
	private String bankReference;
	
	@Column(name = "charge")
	public BigDecimal charge;
	
	@Column(name = "vat")
	public BigDecimal vat;
	
	@Column(name = "exchange_rate")
	public BigDecimal exchangeRate;
	
	@Column(name = "service_code")
	private String serviceCode;
	
	@Column(name = "network")
	private String network;
	
	@Column(name = "sb_flag")
	private Integer sbFlag;
	
	@Column(name = "fd_flag")
	private Integer fdFlag;
	
	@Column(name = "status")
	private String status;
	
	@Column(name = "status_dcr")
	private String statusDcr;
	
	@Column(name = "intermediate_id")
	private Integer IntermediateId;
	
	@Column(name = "end_to_end_id")
	private String endToEndId;
	
	@Column(name = "related_reference")
	private String relatedReference;
	
	@Column(name = "payment_type")
	private String paymentType;
	
	@Column(name = "cancellation_uti")
	private String cancelUti;
	
	@Column(name = "amendp_uti")
	private String amendpUti;
	
	@Column(name = "account_with_institution_name")
	private String accountWithInstitutionName;
	

	public String getAmendpUti() {
		return amendpUti;
	}

	public void setAmendpUti(String amendpUti) {
		this.amendpUti = amendpUti;
	}

	public String getAccountWithInstitutionName() {
		return accountWithInstitutionName;
	}

	public void setAccountWithInstitutionName(String accountWithInstitutionName) {
		this.accountWithInstitutionName = accountWithInstitutionName;
	}

	public String getCancelUti() {
		return cancelUti;
	}

	public void setCancelUti(String cancelUti) {
		this.cancelUti = cancelUti;
	}

	public PshOtrDetPK getId() {
		return id;
	}

	public void setId(PshOtrDetPK id) {
		this.id = id;
	}
	
	public Integer getIntermediateId() {
		return IntermediateId;
	}

	public void setIntermediateId(Integer intermediateId) {
		IntermediateId = intermediateId;
	}

	public String getDep() {
		return dep;
	}

	public void setDep(String dep) {
		this.dep = dep;
	}

	public String getInstructionUuid() {
		return instructionUuid;
	}

	public void setInstructionUuid(String instructionUuid) {
		this.instructionUuid = instructionUuid;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getServiceId() {
		return serviceId;
	}

	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}

	public Date getValueDate() {
		return valueDate;
	}

	public void setValueDate(Date valueDate) {
		this.valueDate = valueDate;
	}

	public Date getDebitDate() {
		return debitDate;
	}

	public void setDebitDate(Date debitDate) {
		this.debitDate = debitDate;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public String getCustomerReference() {
		return customerReference;
	}

	public void setCustomerReference(String customerReference) {
		this.customerReference = customerReference;
	}

	public String getOrderingCustomerAccount() {
		return orderingCustomerAccount;
	}

	public void setOrderingCustomerAccount(String orderingCustomerAccount) {
		this.orderingCustomerAccount = orderingCustomerAccount;
	}

	public String getOrderingCustomerName() {
		return orderingCustomerName;
	}

	public void setOrderingCustomerName(String orderingCustomerName) {
		this.orderingCustomerName = orderingCustomerName;
	}

	public String getOrderingCustomerDet1() {
		return orderingCustomerDet1;
	}

	public void setOrderingCustomerDet1(String orderingCustomerDet1) {
		this.orderingCustomerDet1 = orderingCustomerDet1;
	}

	public String getOrderingCustomerDet2() {
		return orderingCustomerDet2;
	}

	public void setOrderingCustomerDet2(String orderingCustomerDet2) {
		this.orderingCustomerDet2 = orderingCustomerDet2;
	}

	public String getOrderingCustomerDet3() {
		return orderingCustomerDet3;
	}

	public void setOrderingCustomerDet3(String orderingCustomerDet3) {
		this.orderingCustomerDet3 = orderingCustomerDet3;
	}

	public String getOrderingInstitutionBic() {
		return orderingInstitutionBic;
	}

	public void setOrderingInstitutionBic(String orderingInstitutioBic) {
		this.orderingInstitutionBic = orderingInstitutioBic;
	}

	public String getAccountWithInstitutionBic() {
		return accountWithInstitutionBic;
	}

	public void setAccountWithInstitutionBic(String accountWithInstitutionBic) {
		this.accountWithInstitutionBic = accountWithInstitutionBic;
	}

	public String getAccountWithInstitutionId() {
		return accountWithInstitutionId;
	}

	public void setAccountWithInstitutionId(String accountWithInstitutionId) {
		this.accountWithInstitutionId = accountWithInstitutionId;
	}

	public String getAccountWithInstitutionDet1() {
		return accountWithInstitutionDet1;
	}

	public void setAccountWithInstitutionDet1(String accountWithInstitutionDet1) {
		this.accountWithInstitutionDet1 = accountWithInstitutionDet1;
	}

	public String getAccountWithInstitutionDet2() {
		return accountWithInstitutionDet2;
	}

	public void setAccountWithInstitutionDet2(String accountWithInstitutionDet2) {
		this.accountWithInstitutionDet2 = accountWithInstitutionDet2;
	}

	public String getAccountWithInstitutionDet3() {
		return accountWithInstitutionDet3;
	}

	public void setAccountWithInstitutionDet3(String accountWithInstitutionDet3) {
		this.accountWithInstitutionDet3 = accountWithInstitutionDet3;
	}

	public String getBeneficiaryBankCode() {
		return beneficiaryBankCode;
	}

	public void setBeneficiaryBankCode(String beneficiaryBankCode) {
		this.beneficiaryBankCode = beneficiaryBankCode;
	}

	public String getBeneficiaryCustomerAccount() {
		return beneficiaryCustomerAccount;
	}

	public void setBeneficiaryCustomerAccount(String beneficiaryCustomerAccount) {
		this.beneficiaryCustomerAccount = beneficiaryCustomerAccount;
	}

	public String getBeneficiaryCustomerName() {
		return beneficiaryCustomerName;
	}

	public void setBeneficiaryCustomerName(String beneficiaryCustomerName) {
		this.beneficiaryCustomerName = beneficiaryCustomerName;
	}

	public String getBeneficiaryCustomerAdd1() {
		return beneficiaryCustomerAdd1;
	}

	public void setBeneficiaryCustomerAdd1(String beneficiaryCustomerAdd1) {
		this.beneficiaryCustomerAdd1 = beneficiaryCustomerAdd1;
	}

	public String getBeneficiaryCustomerAdd2() {
		return beneficiaryCustomerAdd2;
	}

	public void setBeneficiaryCustomerAdd2(String beneficiaryCustomerAdd2) {
		this.beneficiaryCustomerAdd2 = beneficiaryCustomerAdd2;
	}

	public String getBeneficiaryCustomerAdd3() {
		return beneficiaryCustomerAdd3;
	}

	public void setBeneficiaryCustomerAdd3(String beneficiaryCustomerAdd3) {
		this.beneficiaryCustomerAdd3 = beneficiaryCustomerAdd3;
	}

	public String getPurposeOfThePayment() {
		return purposeOfThePayment;
	}

	public void setPurposeOfThePayment(String purposeOfThePayment) {
		this.purposeOfThePayment = purposeOfThePayment;
	}

	public String getPaymentDetail1() {
		return paymentDetail1;
	}

	public void setPaymentDetail1(String paymentDetail1) {
		this.paymentDetail1 = paymentDetail1;
	}

	public String getPaymentDetail2() {
		return paymentDetail2;
	}

	public void setPaymentDetail2(String paymentDetail2) {
		this.paymentDetail2 = paymentDetail2;
	}

	public String getPaymentDetail3() {
		return paymentDetail3;
	}

	public void setPaymentDetail3(String paymentDetail3) {
		this.paymentDetail3 = paymentDetail3;
	}

	public String getPaymentDetail4() {
		return paymentDetail4;
	}

	public void setPaymentDetail4(String paymentDetail4) {
		this.paymentDetail4 = paymentDetail4;
	}

	public String getSenderToReceiverInfo1() {
		return senderToReceiverInfo1;
	}

	public void setSenderToReceiverInfo1(String senderToReceiverInfo1) {
		this.senderToReceiverInfo1 = senderToReceiverInfo1;
	}

	public String getSenderToReceiverInfo2() {
		return senderToReceiverInfo2;
	}

	public void setSenderToReceiverInfo2(String senderToReceiverInfo2) {
		this.senderToReceiverInfo2 = senderToReceiverInfo2;
	}

	public String getSenderToReceiverInfo3() {
		return senderToReceiverInfo3;
	}

	public void setSenderToReceiverInfo3(String senderToReceiverInfo3) {
		this.senderToReceiverInfo3 = senderToReceiverInfo3;
	}

	public String getSenderToReceiverInfo4() {
		return senderToReceiverInfo4;
	}

	public void setSenderToReceiverInfo4(String senderToReceiverInfo4) {
		this.senderToReceiverInfo4 = senderToReceiverInfo4;
	}

	public String getSenderToReceiverInfo5() {
		return senderToReceiverInfo5;
	}

	public void setSenderToReceiverInfo5(String senderToReceiverInfo5) {
		this.senderToReceiverInfo5 = senderToReceiverInfo5;
	}

	public String getSenderToReceiverInfo6() {
		return senderToReceiverInfo6;
	}

	public void setSenderToReceiverInfo6(String senderToReceiverInfo6) {
		this.senderToReceiverInfo6 = senderToReceiverInfo6;
	}

	public String getAdditionalInfo1() {
		return additionalInfo1;
	}

	public void setAdditionalInfo1(String additionalInfo1) {
		this.additionalInfo1 = additionalInfo1;
	}

	public String getAdditionalInfo2() {
		return additionalInfo2;
	}

	public void setAdditionalInfo2(String additionalInfo2) {
		this.additionalInfo2 = additionalInfo2;
	}

	public String getAdditionalInfo3() {
		return additionalInfo3;
	}

	public void setAdditionalInfo3(String additionalInfo3) {
		this.additionalInfo3 = additionalInfo3;
	}

	public String getAdditionalInfo4() {
		return additionalInfo4;
	}

	public void setAdditionalInfo4(String additionalInfo4) {
		this.additionalInfo4 = additionalInfo4;
	}

	public String getRoutingCode() {
		return routingCode;
	}

	public void setRoutingCode(String routingCode) {
		this.routingCode = routingCode;
	}

	public String getProId() {
		return proId;
	}

	public void setProId(String proId) {
		this.proId = proId;
	}

	public String getVerId() {
		return verId;
	}

	public void setVerId(String verId) {
		this.verId = verId;
	}

	public String getSettlementAccount() {
		return settlementAccount;
	}

	public void setSettlementAccount(String settlementAccount) {
		this.settlementAccount = settlementAccount;
	}

	public String getExternalRef() {
		return externalRef;
	}

	public void setExternalRef(String externalRef) {
		this.externalRef = externalRef;
	}

	public String getBankReference() {
		return bankReference;
	}

	public void setBankReference(String bankReference) {
		this.bankReference = bankReference;
	}

	public String getServiceCode() {
		return serviceCode;
	}

	public void setServiceCode(String serviceCode) {
		this.serviceCode = serviceCode;
	}

	public String getNetwork() {
		return network;
	}

	public void setNetwork(String network) {
		this.network = network;
	}

	public Integer getSbFlag() {
		return sbFlag;
	}

	public void setSbFlag(Integer sbFlag) {
		this.sbFlag = sbFlag;
	}

	public Integer getFdFlag() {
		return fdFlag;
	}

	public void setFdFlag(Integer fdFlag) {
		this.fdFlag = fdFlag;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatusDcr() {
		return statusDcr;
	}

	public void setStatusDcr(String statusDcr) {
		this.statusDcr = statusDcr;
	}

	public BigDecimal getTransactionAmount() {
		return transactionAmount;
	}

	public void setTransactionAmount(BigDecimal transactionAmount) {
		this.transactionAmount = transactionAmount;
	}

	public Date getInstructionTimestamp() {
		return instructionTimestamp;
	}

	public void setInstructionTimestamp(Date instructionTimestamp) {
		this.instructionTimestamp = instructionTimestamp;
	}

	public BigDecimal getCharge() {
		return charge;
	}

	public void setCharge(BigDecimal charge) {
		this.charge = charge;
	}

	public BigDecimal getVat() {
		return vat;
	}

	public void setVat(BigDecimal vat) {
		this.vat = vat;
	}

	public BigDecimal getExchangeRate() {
		return exchangeRate;
	}

	public void setExchangeRate(BigDecimal exchangeRate) {
		this.exchangeRate = exchangeRate;
	}

	public Date getCreDate() {
		return creDate;
	}

	public void setCreDate(Date creDate) {
		this.creDate = creDate;
	}

	public String getEndToEndId() {
		return endToEndId;
	}

	public void setEndToEndId(String endToEndId) {
		this.endToEndId = endToEndId;
	}

	public String getRelatedReference() {
		return relatedReference;
	}

	public void setRelatedReference(String relatedReference) {
		this.relatedReference = relatedReference;
	}

	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	@Override
	public String toString() {
		return "PshOtrDet [id=" + id + ", dep=" + dep + ", instructionUuid=" + instructionUuid
				+ ", instructionTimestamp=" + instructionTimestamp + ", productId=" + productId + ", serviceId="
				+ serviceId + ", valueDate=" + valueDate + ", debitDate=" + debitDate + ", transactionAmount="
				+ transactionAmount + ", currencyCode=" + currencyCode + ", customerReference=" + customerReference
				+ ", orderingCustomerAccount=" + orderingCustomerAccount + ", orderingCustomerName="
				+ orderingCustomerName + ", orderingCustomerDet1=" + orderingCustomerDet1 + ", orderingCustomerDet2="
				+ orderingCustomerDet2 + ", orderingCustomerDet3=" + orderingCustomerDet3 + ", orderingInstitutionBic="
				+ orderingInstitutionBic + ", accountWithInstitutionBic=" + accountWithInstitutionBic
				+ ", accountWithInstitutionId=" + accountWithInstitutionId + ", accountWithInstitutionDet1="
				+ accountWithInstitutionDet1 + ", accountWithInstitutionDet2=" + accountWithInstitutionDet2
				+ ", accountWithInstitutionDet3=" + accountWithInstitutionDet3 + ", beneficiaryBankCode="
				+ beneficiaryBankCode + ", beneficiaryCustomerAccount=" + beneficiaryCustomerAccount
				+ ", beneficiaryCustomerName=" + beneficiaryCustomerName + ", beneficiaryCustomerAdd1="
				+ beneficiaryCustomerAdd1 + ", beneficiaryCustomerAdd2=" + beneficiaryCustomerAdd2
				+ ", beneficiaryCustomerAdd3=" + beneficiaryCustomerAdd3 + ", purposeOfThePayment="
				+ purposeOfThePayment + ", paymentDetail1=" + paymentDetail1 + ", paymentDetail2=" + paymentDetail2
				+ ", paymentDetail3=" + paymentDetail3 + ", paymentDetail4=" + paymentDetail4
				+ ", senderToReceiverInfo1=" + senderToReceiverInfo1 + ", senderToReceiverInfo2="
				+ senderToReceiverInfo2 + ", senderToReceiverInfo3=" + senderToReceiverInfo3
				+ ", senderToReceiverInfo4=" + senderToReceiverInfo4 + ", senderToReceiverInfo5="
				+ senderToReceiverInfo5 + ", senderToReceiverInfo6=" + senderToReceiverInfo6 + ", additionalInfo1="
				+ additionalInfo1 + ", additionalInfo2=" + additionalInfo2 + ", additionalInfo3=" + additionalInfo3
				+ ", additionalInfo4=" + additionalInfo4 + ", routingCode=" + routingCode + ", proId=" + proId
				+ ", creDate=" + creDate + ", verId=" + verId + ", settlementAccount=" + settlementAccount
				+ ", externalRef=" + externalRef + ", bankReference=" + bankReference + ", charge=" + charge + ", vat="
				+ vat + ", exchangeRate=" + exchangeRate + ", serviceCode=" + serviceCode + ", network=" + network
				+ ", sbFlag=" + sbFlag + ", fdFlag=" + fdFlag + ", status=" + status + ", statusDcr=" + statusDcr
				+ ", IntermediateId=" + IntermediateId + ", endToEndId=" + endToEndId + ", relatedReference="
				+ relatedReference + ", paymentType=" + paymentType + "]";
	}
	
	

}
