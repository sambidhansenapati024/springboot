package com.alfaris.ipsh.liquidity.repository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.alfaris.ipsh.liquidity.entity.PshLmsDtr;
import com.alfaris.ipsh.liquidity.entity.PshSamLiqRep;



@Repository
public interface LiquidityDetailRepository extends JpaRepository<PshLmsDtr, String>, JpaSpecificationExecutor<PshLmsDtr>{
	
	@Query("select a from PshLmsDtr a where a.timeStamp >= :today ORDER BY a.timeStamp ASC")
	Optional<List<PshLmsDtr>> findTodaysGraphData(Date today);
	
	Optional<PshLmsDtr> findTopByOrderByTimeStampDesc();
	
	@Query("select a.ogTxnAmt from PshLmsDtr a where a.ogTxnAmt != NULL ORDER BY a.timeStamp DESC")
	Optional<List<BigDecimal>> findLatestOutgoingAmount();
	
	@Query("select a.incTxnAmt from PshLmsDtr a where a.incTxnAmt != NULL ORDER BY a.timeStamp DESC")
	Optional<List<BigDecimal>> findLatestIncomingAmount();
	
	@Query("SELECT e.netAmt from PshLmsDtr e WHERE e.timeStamp >= :startDate AND e.timeStamp < :endDate order by e.netAmt DESC")
	Optional<List<BigDecimal>> getListOfNetAmount(Date startDate, Date endDate);

	@Query("select max(e.netAmt) from PshLmsDtr e WHERE e.netAmt>0 AND e.timeStamp >= :startDate AND e.timeStamp < :endDate group by e.valueDate")
	Optional<List<BigDecimal>> getPositiveNetCumilativeList(Date startDate, Date endDate);
	
	@Query("select min(e.netAmt) from PshLmsDtr e WHERE e.netAmt<0 AND e.timeStamp >= :startDate AND e.timeStamp < :endDate group by e.valueDate")
	Optional<List<BigDecimal>> getNegetiveNetCumilativeList(Date startDate, Date endDate);
	
	

}
