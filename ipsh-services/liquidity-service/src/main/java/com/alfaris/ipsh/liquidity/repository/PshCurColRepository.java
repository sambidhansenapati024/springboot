package com.alfaris.ipsh.liquidity.repository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.alfaris.ipsh.liquidity.entity.PshCurCol;
import com.alfaris.ipsh.liquidity.entity.PshCurColPK;


@Repository
public interface PshCurColRepository extends JpaRepository<PshCurCol, PshCurColPK> , JpaSpecificationExecutor<PshCurCol>{
	
	
	@Query("select e.grossPaySent from PshCurCol e where e.id.timeStamp >= :startDate AND e.id.timeStamp < :endDate AND e.id.productId= 'RTGS' AND e.grossPaySent IS NOT NULL order by e.grossPaySent DESC")
	Optional<List<BigDecimal>> getGrossPaySent(Date startDate, Date endDate);
	
	@Query("select e.grossPayRecv from PshCurCol e where e.id.timeStamp >= :startDate AND e.id.timeStamp < :endDate AND e.id.productId= 'RTGS' AND e.grossPayRecv IS NOT NULL order by e.grossPayRecv DESC")
	Optional<List<BigDecimal>> getGrossPayRecv(Date startDate, Date endDate);

	@Query("select e.percSent from PshCurCol e where e.id.timeStamp >= :startDate AND e.id.timeStamp < :endDate  AND hour(e.id.timeStamp)= :hour AND e.id.productId= 'RTGS' order by e.percSent DESC")
	Optional<List<BigDecimal>> getThroughPutSentPerc(int hour,Date startDate, Date endDate);
	
	@Query("select e.percRecv from PshCurCol e where e.id.timeStamp >= :startDate AND e.id.timeStamp < :endDate  AND e.id.productId= 'RTGS' order by e.percRecv DESC")
	Optional<List<BigDecimal>> getThroughPutRecvPerc(Date startDate, Date endDate);
	
	@Query("select count(p) from PshCurCol p where (p.grossPaySent > 0 OR grossPayRecv > 0) AND id.valueDate BETWEEN :startDate AND :endDate")
	BigDecimal getNoOfDays(Date startDate, Date endDate);

	@Query("select count(p) from PshCurCol p where p.grossPaySent > 0 AND id.valueDate >= :startDate AND id.valueDate < :endDate")
	BigDecimal noOfDaysWithGrossSent(Date startDate, Date endDate);

	@Query("select e.percSent*100 from PshCurCol e where e.id.timeStamp >= :startDate AND e.id.timeStamp < :endDate  AND hour(e.id.timeStamp)= :hour AND e.id.productId= 'RTGS' order by e.id.valueDate")
	Optional<List<BigDecimal>> getPercentageSentAtTimeList(int hour,Date startDate, Date endDate);
	
	@Query("select e.grossPaySent from PshCurCol e where e.grossPaySent >= 0 AND e.id.valueDate >= :startDate AND e.id.valueDate < :endDate order by e.id.valueDate")
	List<BigDecimal> listOfGrossPaymentSent(Date startDate, Date endDate);
	
}
