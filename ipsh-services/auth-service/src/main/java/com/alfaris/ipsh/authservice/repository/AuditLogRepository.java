package com.alfaris.ipsh.authservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.alfaris.ipsh.authservice.entity.PshCasHry;
import com.alfaris.ipsh.authservice.entity.PshCasHryPK;

@Repository
public interface AuditLogRepository extends JpaRepository<PshCasHry, PshCasHryPK>, JpaSpecificationExecutor<PshCasHry> {
}
