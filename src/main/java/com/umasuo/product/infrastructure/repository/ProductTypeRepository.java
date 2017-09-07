package com.umasuo.product.infrastructure.repository;

import com.umasuo.product.domain.model.ProductType;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repository class for ProductType.
 */
public interface ProductTypeRepository extends JpaRepository<ProductType, String> {

  /**
   * Find all ProductType order by
   * @return
   */
  List<ProductType> findAllByOrderByCreatedAt();
}
