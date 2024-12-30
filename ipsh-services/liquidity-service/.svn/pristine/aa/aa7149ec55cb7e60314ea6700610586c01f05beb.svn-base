package com.alfaris.ipsh.liquidity.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.alfaris.ipsh.liquidity.entity.PshSamLiqRep;
@Repository
public interface SamaLiquidityReportRepository extends JpaRepository<PshSamLiqRep, Integer>{

	Page<PshSamLiqRep> findAll(Specification<PshSamLiqRep> recordsBySearchSpec, Pageable pageable);

}
