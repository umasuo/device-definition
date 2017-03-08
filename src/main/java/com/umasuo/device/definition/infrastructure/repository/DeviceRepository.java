package com.umasuo.device.definition.infrastructure.repository;

import com.umasuo.device.definition.domain.model.Device;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by umasuo on 17/2/10.
 */
@Repository
public interface DeviceRepository extends JpaRepository<Device, String>,
    CrudRepository<Device, String> {

}