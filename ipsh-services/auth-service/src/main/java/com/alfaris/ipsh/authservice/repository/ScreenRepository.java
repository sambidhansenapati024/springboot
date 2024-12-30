package com.alfaris.ipsh.authservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.alfaris.ipsh.authservice.entity.Screen;

@Repository
public interface ScreenRepository extends JpaRepository<Screen, String>, JpaSpecificationExecutor<Screen> {

}
