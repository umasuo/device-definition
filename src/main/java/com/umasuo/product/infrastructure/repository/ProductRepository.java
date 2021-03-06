package com.umasuo.product.infrastructure.repository;

import com.umasuo.product.domain.model.Product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by umasuo on 17/2/10.
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, String>,
    QueryByExampleExecutor<Product> {

  /**
   * Find all by developer id.
   *
   * @param developerId the developer id
   * @return the list
   */
  List<Product> findAllByDeveloperId(String developerId);
}
