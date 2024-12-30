package com.alfaris.ipsh.security.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.alfaris.ipsh.security.entity.Users;

@Repository
public interface UserRepository extends JpaRepository<Users, String>, JpaSpecificationExecutor<Users> {

	Optional<Users> findByUserId(String userId);

	List<Users> findByStatus(String status, Pageable pageable);

	@Query("SELECT DISTINCT a.groupId FROM Group a WHERE a.status=?1")
	List<String> findAll(String status);
	
}
