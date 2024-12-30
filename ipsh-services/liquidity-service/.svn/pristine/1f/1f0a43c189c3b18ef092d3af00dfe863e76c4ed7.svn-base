package com.alfaris.ipsh.liquidity.repository;

import java.util.Date;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.alfaris.ipsh.liquidity.entity.PshRtgsSdi;
import com.alfaris.ipsh.liquidity.entity.PshSamLiqRep;

@Repository
public interface PshRtgsSdiRepository extends JpaRepository<PshRtgsSdi,String>, JpaSpecificationExecutor<PshRtgsSdi>{

	@Query("SELECT  d FROM PshRtgsSdi d WHERE d.valueDate>=:todayDate ORDER BY d.createDate DESC LIMIT 1")
	Optional<PshRtgsSdi> findByValueDate(Date todayDate);
}
