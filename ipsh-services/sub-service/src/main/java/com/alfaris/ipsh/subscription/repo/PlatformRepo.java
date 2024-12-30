package com.alfaris.ipsh.subscription.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.alfaris.ipsh.subscription.entity.Platforms;

@Repository
public interface PlatformRepo extends JpaRepository<Platforms, String> {

}
