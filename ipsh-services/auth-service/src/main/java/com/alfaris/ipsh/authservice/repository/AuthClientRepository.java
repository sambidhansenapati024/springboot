package com.alfaris.ipsh.authservice.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.alfaris.ipsh.authservice.entity.TestClient;

@Repository
public interface AuthClientRepository extends JpaRepository<TestClient, String> {
	//Optional<TestClient> findByClientId(String clientId);
}
