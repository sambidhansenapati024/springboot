package com.alfaris.ipsh.kycservice.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.alfaris.ipsh.kycservice.entity.KycRequest;
import com.alfaris.ipsh.kycservice.entity.KycRequestPk;
@Repository
public interface KycRepo extends JpaRepository<KycRequest, KycRequestPk>, JpaSpecificationExecutor<KycRequest> {

}
