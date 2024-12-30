package com.alfaris.ipsh.subscription.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="PSH_SUBS_SYS")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class Subscription {
	
	@EmbeddedId
	@Id
	private SubscriptionPk pk;
	@Column(name = "email")
	@JsonProperty("email")
	private String email;

	@Column(name = "full_name")
	@JsonProperty("fullName")
	private String fullName;

	@Column(name = "created_by")
	@JsonProperty("createdBy")
	private String createdBy;

	@Column(name = "modified_by")
	@JsonProperty("modifiedBy")
	private String modifiedBy;

	@Column(name = "verified_by")
	@JsonProperty("verifiedBy")
	private String verifiedBy;

	@Column(name = "status")
	@JsonProperty("status")
	private String status;

	@Column(name = "comnt")
	@JsonProperty("comments")
	private String comments;

	@Column(name = "pymt_mthd")
	@JsonProperty("paymentMethod")
	private String paymentMethod;

	@Column(name = "pymt_by")
	@JsonProperty("paymentBy")
	private String paymentBy;

	@Column(name = "duration")
	@JsonProperty("duration")
	private Long duration;

	@Column(name = "price")
	@JsonProperty("price")
	private Double price;

	@Column(name = "total_price")
	@JsonProperty("fPrice")
	private Double fPrice;

	@Column(name = "modif_time")
	@JsonProperty("modificationTime")
	private LocalDateTime modificationTime;

	@Column(name = "subs_date")
	@JsonProperty("subscriptionDate")
	private LocalDate subscriptionDate;

	@Column(name = "crt_time")
	@JsonProperty("creationTime")
	private LocalDateTime creationTime;

	@Column(name = "vryf_time")
	@JsonProperty("verificationTime")
	private LocalDateTime verificationTime;

	@Column(name = "pymt_time")
	@JsonProperty("paymentDate")
	private LocalDateTime patmentDate;

	@Column(name = "end_sub")
	@JsonProperty("endSubscriptionDate")
	private LocalDate endSubscriptionDate;

	@Column(name = "mobile_number")
	@JsonProperty("mobileNumber")
	private String mobileNubmer;

	

}
