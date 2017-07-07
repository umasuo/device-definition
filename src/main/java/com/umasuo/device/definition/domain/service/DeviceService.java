package com.umasuo.device.definition.domain.service;

import com.umasuo.device.definition.domain.model.Device;
import com.umasuo.device.definition.infrastructure.repository.DeviceRepository;
import com.umasuo.exception.NotExistException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Repository;
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
   * @param device the device
   * @return device device
   */
  public Device save(Device device) {
    logger.debug("Enter. device: {}.", device);

    Device deviceInDB = deviceRepository.save(device);

    logger.debug("Exit. deviceInDB: {}.", deviceInDB);
    return deviceInDB;
  }

  /**
   * Get device.
   *
   * @param id the id
   * @return device device
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
   * Gets by developer id.
   *
   * @param developerId the developer id
   * @return by developer id
   */
  public List<Device> getByDeveloperId(String developerId) {
    logger.debug("Enter. developerId: {}.", developerId);

    List<Device> devices = deviceRepository.findAllByDeveloperId(developerId);

    logger.debug("Exit. devicesSize: {}.", devices.size());

    return devices;
  }

  /**
   * Gets all open device.
   *
   * @param developerId the developer id
   * @return the all openable device
   */
  public List<Device> getAllOpenDevice(String developerId) {
    logger.debug("Enter. developerId: {}.", developerId);

    Device sample = new Device();
    sample.setDeveloperId(developerId);
    sample.setOpenable(true);

    Example<Device> exam = Example.of(sample);

    List<Device> devices = deviceRepository.findAll(exam);

    logger.debug("Exit. devicesSize: {}.", devices.size());

    return devices;
  }


  /**
   * Is exist name in developer.
   *
   * @param developerId the developer id
   * @param name the name
   * @return the boolean
   */
  public boolean isExistName(String developerId, String name) {
    logger.debug("Enter. developerId: {}, name: {}.", developerId, name);

    Device sample = new Device();
    sample.setDeveloperId(developerId);
    sample.setName(name);

    Example<Device> example = Example.of(sample);

    boolean result = deviceRepository.exists(example);

    logger.debug("Exit. name: {} exist: {}.", name, result);

    return result;
  }

  public void delete(String id) {
    logger.debug("Enter. id: {}.", id);

    deviceRepository.delete(id);

    logger.debug("Exit.");
  }
}
