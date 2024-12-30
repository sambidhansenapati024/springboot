package com.alfaris.ipsh.subscription.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.alfaris.ipsh.subscription.entity.Subscription;
import com.alfaris.ipsh.subscription.entity.SubscriptionPk;
@Repository
public interface SubscRepo extends JpaRepository<Subscription, SubscriptionPk>, JpaSpecificationExecutor<Subscription> {

}
