package com.alfaris.ipsh.subscription.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.alfaris.ipsh.subscription.entity.ActionLog;
import com.alfaris.ipsh.subscription.entity.ActionLogPk;

@Repository
public interface ActionLogRepo extends JpaRepository<ActionLog, ActionLogPk>,JpaSpecificationExecutor<ActionLog> {

}
