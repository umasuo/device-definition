package com.umasuo.device.definition.infrastructure.repository;

import com.umasuo.device.definition.domain.model.Device;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by umasuo on 17/2/10.
 */
@Repository
public interface DeviceRepository extends JpaRepository<Device, String>,
    QueryByExampleExecutor<Device> {

  /**
   * Find all by developer id.
   *
   * @param developerId the developer id
   * @return the list
   */
  List<Device> findAllByDeveloperId(String developerId);
}
