package com.umasuo.device.definition.infrastructure.repository;

import com.umasuo.device.definition.domain.model.ProductType;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Davis on 17/6/28.
 */
public interface ProductTypeRepository extends JpaRepository<ProductType, String> {

}
