package com.alfaris.ipsh.authservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.alfaris.ipsh.authservice.entity.UserThemeConfig;

@Repository
public interface UserThemeConfigRepository extends JpaRepository<UserThemeConfig, String> {

}
