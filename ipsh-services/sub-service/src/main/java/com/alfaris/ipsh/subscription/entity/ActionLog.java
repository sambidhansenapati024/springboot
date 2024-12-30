package com.alfaris.ipsh.subscription.entity;


import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="psh_act_log",indexes = {@Index(name="idx_act",columnList = "userName,status")})
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ActionLog {
	@EmbeddedId
	@Id
	private  ActionLogPk pk;
	@Column(name="status")
	private String status;
	@Column(name="action")
	private String action;
	@Column(name="sys_time")
	private Timestamp systime;
	@Column(name="reason")
	private String Reason;


}
