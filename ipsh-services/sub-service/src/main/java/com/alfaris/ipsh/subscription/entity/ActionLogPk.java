package com.alfaris.ipsh.subscription.entity;

import java.io.Serializable;
import java.util.Date;

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
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class ActionLogPk implements Serializable  {

	private static final long serialVersionUID = 1L;
	@Column(name="act_id")
	private String id;
	@Column(name="act_time")
	private Date Actiontime;
	@Column(name="userName")
	private String userName;


}
