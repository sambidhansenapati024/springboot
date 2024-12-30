package com.alfaris.ipsh.subscription.entity;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
@ToString

public class SubscriptionPk implements Serializable {
	
	private static final long serialVersionUID = 1L;
	@Column(name = "unq_sub_id")
	@JsonProperty("uniqSubId")
	private String uniqSubId;

	@Column(name = "unq_user_id")
	@JsonProperty("uniqUserId")
	private Long uniqUserId;

	@Column(name = "platforms")
	@JsonProperty("platforms")
	private String platforms;

	@Column(name = "user_name")
	@JsonProperty("userName")
	private String userName;


}
