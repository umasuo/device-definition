package com.umasuo.product.infrastructure.repository;

import com.umasuo.product.domain.model.StatusRequest;
import com.umasuo.product.infrastructure.enums.RequestStatus;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;

/**
 * Repository for Request.
 */
public interface RequestRepository extends JpaRepository<StatusRequest, String> {

  /**
   * Change request status.
   *
   * @param developerId the developerId
   * @param productId the productId
   * @param status the status
   * @return changed count
   */
  @Modifying
  @Transactional
  @Query("update StatusRequest sr set sr.status = ?3 where sr.developerId = ?1 and sr.productId = ?2")
  int changeRequestStatus(String developerId, String productId, RequestStatus status);
}
