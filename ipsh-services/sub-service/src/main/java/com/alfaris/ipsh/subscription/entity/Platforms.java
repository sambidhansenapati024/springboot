package com.alfaris.ipsh.subscription.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name="PSH_SUBSC_PLTFRM")
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Platforms {
	@Id
	@Column(name="plat_id")
	private String platId;
	@Column(name="plat_name")
	private String platName;

}
