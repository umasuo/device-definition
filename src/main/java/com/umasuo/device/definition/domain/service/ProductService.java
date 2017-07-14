package com.umasuo.device.definition.domain.service;

import com.umasuo.device.definition.domain.model.Product;
import com.umasuo.device.definition.infrastructure.repository.ProductRepository;
import com.umasuo.exception.NotExistException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by umasuo on 17/5/31.
 */
@Service
public class ProductService {

  private final static Logger logger = LoggerFactory.getLogger(ProductService.class);

  @Autowired
  private transient ProductRepository deviceRepository;

  /**
   * 新建device.
   *
   * @param device the device
   * @return device device
   */
  public Product save(Product device) {
    logger.debug("Enter. device: {}.", device);

    Product deviceInDB = deviceRepository.save(device);

    logger.debug("Exit. deviceInDB: {}.", deviceInDB);
    return deviceInDB;
  }

  /**
   * Get device.
   *
   * @param id the id
   * @return device device
   */
  public Product get(String id) {
    logger.debug("Enter. id: {}.", id);

    Product device = deviceRepository.findOne(id);
    if (device == null) {
      throw new NotExistException("Product not exist, id: " + id);
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
  public List<Product> getByDeveloperId(String developerId) {
    logger.debug("Enter. developerId: {}.", developerId);

    List<Product> devices = deviceRepository.findAllByDeveloperId(developerId);

    logger.debug("Exit. devicesSize: {}.", devices.size());

    return devices;
  }

  /**
   * Gets all open device.
   *
   * @param developerId the developer id
   * @return the all openable device
   */
  public List<Product> getAllOpenProduct(String developerId) {
    logger.debug("Enter. developerId: {}.", developerId);

    Product sample = new Product();
    sample.setDeveloperId(developerId);
    sample.setOpenable(true);

    Example<Product> exam = Example.of(sample);

    List<Product> devices = deviceRepository.findAll(exam);

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

    Product sample = new Product();
    sample.setDeveloperId(developerId);
    sample.setName(name);

    Example<Product> example = Example.of(sample);

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
