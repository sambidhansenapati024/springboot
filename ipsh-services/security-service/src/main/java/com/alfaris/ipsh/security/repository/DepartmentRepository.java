package com.alfaris.ipsh.security.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.alfaris.ipsh.security.entity.PshUsrDep;
import com.alfaris.ipsh.security.entity.PshUsrDepPK;

public interface DepartmentRepository extends JpaRepository<PshUsrDep, PshUsrDepPK>, JpaSpecificationExecutor<PshUsrDep>{
	
	@Query("SELECT p FROM PshUsrDep p WHERE p.status =:status")
	List<PshUsrDep> getDepDropdown(String status);
	

}
