package com.umasuo.device.definition.domain.service;

import com.umasuo.device.definition.domain.model.Device;
import com.umasuo.device.definition.infrastructure.repository.DeviceRepository;
import com.umasuo.exception.NotExistException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by umasuo on 17/5/31.
 */
@Service
public class DeviceService {

  private final static Logger logger = LoggerFactory.getLogger(DeviceService.class);

  @Autowired
  private transient DeviceRepository deviceRepository;

  /**
   * 新建device.
   *
   * @param device
   * @return
   */
  public Device save(Device device) {
    logger.debug("Enter. device: {}.", device);

    Device deviceInDB = deviceRepository.save(device);

    logger.debug("Exit. deviceInDB: {}.", deviceInDB);
    return deviceInDB;
  }

  /**
   * @param id
   * @return
   */
  public Device get(String id) {
    logger.debug("Enter. id: {}.", id);

    Device device = deviceRepository.findOne(id);
    if (device == null) {
      throw new NotExistException("Device not exist, id: " + id);
    }

    logger.debug("Exit. device: {}.", device);
    return device;
  }

  /**
   * @return
   */
  public List<Device> getByDeveloperId(String developerId) {
    logger.debug("Enter. developerId: {}.", developerId);

    List<Device> devices = deviceRepository.findAllByDeveloperId(developerId);

    logger.debug("Exit. devicesSize: {}.", devices.size());

    return devices;
  }
}
