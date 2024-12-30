package com.alfaris.ipsh.authservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.alfaris.ipsh.authservice.entity.GroupScreen;
import com.alfaris.ipsh.authservice.entity.GroupScreenPK;

@Repository
public interface GroupScreenRepository
		extends JpaRepository<GroupScreen, GroupScreenPK>, JpaSpecificationExecutor<GroupScreen> {
	
	List<GroupScreen> findAllByIdGroupId(String groupId);
}
