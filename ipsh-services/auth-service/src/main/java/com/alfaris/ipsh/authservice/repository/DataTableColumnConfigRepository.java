package com.alfaris.ipsh.authservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.alfaris.ipsh.authservice.entity.PshCasHry;
import com.alfaris.ipsh.authservice.entity.PshCasHryPK;
import com.alfaris.ipsh.authservice.entity.PshColCnf;
import com.alfaris.ipsh.authservice.entity.PshColCnfPK;

@Repository
public interface DataTableColumnConfigRepository extends JpaRepository<PshColCnf, PshColCnfPK> {

}
