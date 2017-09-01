package com.umasuo.product.infrastructure.repository;

import com.umasuo.product.domain.model.ApplicationRecord;
import com.umasuo.product.infrastructure.enums.RequestStatus;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

import javax.persistence.OrderBy;
import javax.transaction.Transactional;

/**
 * Repository for Request.
 */
public interface ApplicationRecordRepository extends JpaRepository<ApplicationRecord, String> {

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
  @Query("update ApplicationRecord sr set sr.status = ?3 where sr.developerId = ?1 and sr.productId = ?2")
  int changeRequestStatus(String developerId, String productId, RequestStatus status);

  /**
   * Get all request order by last modified time.
   *
   * @return list of ApplicationRecord
   */
//  List<ApplicationRecord> getAllOnOrder();
}
