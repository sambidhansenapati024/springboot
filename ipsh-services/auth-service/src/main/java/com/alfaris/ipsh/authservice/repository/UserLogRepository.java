package com.alfaris.ipsh.authservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.alfaris.ipsh.authservice.entity.Eftsecadt;
import com.alfaris.ipsh.authservice.entity.EftsecadtPK;

@Repository
public interface UserLogRepository extends JpaRepository<Eftsecadt, EftsecadtPK> {
}
