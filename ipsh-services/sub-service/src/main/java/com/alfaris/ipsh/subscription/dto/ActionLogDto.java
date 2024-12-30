package com.alfaris.ipsh.subscription.dto;

import java.sql.Timestamp;
import java.util.Date;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class ActionLogDto {
	
	private String status;
	
	private String action;

	private Timestamp systime;

	private String Reason;

	private String id;

	private Date Actiontime;
	
	private String userName;

}
