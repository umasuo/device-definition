package com.umasuo.product.infrastructure.repository;

import com.umasuo.product.domain.model.StatusRequest;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Davis on 17/7/19.
 */
public interface RequestRepository extends JpaRepository<StatusRequest, String>{

}
