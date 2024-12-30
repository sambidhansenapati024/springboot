package com.alfaris.ipsh.liquidity.repository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


import com.alfaris.ipsh.liquidity.entity.PshLmsCol;
import com.alfaris.ipsh.liquidity.entity.PshLmsColPK;

@Repository
public interface SamaCollateralRepository extends JpaRepository<PshLmsCol, PshLmsColPK>, JpaSpecificationExecutor<PshLmsCol>{

	
	@Query("SELECT e.samaReserve from PshLmsCol e WHERE e.id.timeStamp >= :startDate AND e.id.timeStamp < :endDate AND e.status= 'VERIFIED' AND e.id.productId='RTGS' order by e.samaReserve ASC")
	Optional<List<BigDecimal>> getListOfSamaReserve(Date startDate, Date endDate);
	
	@Query("SELECT e.colPldgSama from PshLmsCol e WHERE e.id.timeStamp >= :startDate AND e.id.timeStamp < :endDate AND e.status= 'VERIFIED' AND e.id.productId='RTGS' order by e.colPldgSama ASC")
	Optional<List<BigDecimal>> getListOfColPldgCb(Date startDate, Date endDate);
	
	@Query("SELECT e.colPldsAnci from PshLmsCol e WHERE e.id.timeStamp >= :startDate AND e.id.timeStamp < :endDate AND e.status= 'VERIFIED' AND e.id.productId='RTGS' order by e.colPldsAnci ASC")
	Optional<List<BigDecimal>> getListOfColPldgAs(Date startDate, Date endDate);
	
	@Query("SELECT e.unLiquidAssets from PshLmsCol e WHERE e.id.timeStamp >= :startDate AND e.id.timeStamp < :endDate AND e.status= 'VERIFIED' AND e.id.productId='RTGS' order by e.unLiquidAssets ASC")
	Optional<List<BigDecimal>> getListOfUnLiquidAssets(Date startDate, Date endDate);
	
	@Query("SELECT e.securedCrline from PshLmsCol e WHERE e.id.timeStamp >= :startDate AND e.id.timeStamp < :endDate AND e.status= 'VERIFIED' AND e.id.productId='RTGS' order by e.securedCrline ASC")
	Optional<List<BigDecimal>> getListOfSecCrdLine(Date startDate, Date endDate);
	
	@Query("SELECT e.committedCrline from PshLmsCol e WHERE e.id.timeStamp >= :startDate AND e.id.timeStamp < :endDate AND e.status= 'VERIFIED' AND e.id.productId='RTGS' order by e.committedCrline ASC")
	Optional<List<BigDecimal>> getListOfcmtdCrdLine(Date startDate, Date endDate);
	
	@Query("SELECT e.others from PshLmsCol e WHERE e.id.timeStamp >= :startDate AND e.id.timeStamp < :endDate AND e.status= 'VERIFIED' AND e.id.productId='RTGS' order by e.others ASC")
	Optional<List<BigDecimal>> getListOfOthers(Date startDate, Date endDate);

	@Query("SELECT e.total from PshLmsCol e WHERE e.id.timeStamp >= :startDate AND e.id.timeStamp < :endDate AND e.status= 'VERIFIED' AND e.id.productId='RTGS' order by e.total ASC")
	Optional<List<BigDecimal>> getListOfTotal(Date startDate, Date endDate);
	
	@Query("SELECT e.totalCrline from PshLmsCol e WHERE e.id.timeStamp >= :startDate AND e.id.timeStamp < :endDate AND e.status= 'VERIFIED' AND e.id.productId='RTGS' order by e.totalCrline ASC")
	Optional<List<BigDecimal>> getListOfTotalCrline(Date startDate, Date endDate);
	
	@Query("SELECT e.balWithOthrBnks from PshLmsCol e WHERE e.id.timeStamp >= :startDate AND e.id.timeStamp < :endDate AND e.status= 'VERIFIED' AND e.id.productId='RTGS' order by e.balWithOthrBnks ASC")
	Optional<List<BigDecimal>> getListOfBalWithOthrBnks(Date startDate, Date endDate);
}
