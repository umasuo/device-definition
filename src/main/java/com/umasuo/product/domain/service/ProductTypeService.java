package com.umasuo.product.domain.service;

import com.umasuo.exception.NotExistException;
import com.umasuo.product.domain.model.ProductType;
import com.umasuo.product.infrastructure.repository.ProductTypeRepository;

import org.apache.commons.logging.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Davis on 17/6/28.
 */
@Service
public class ProductTypeService {

  /**
   * Logger.
   */
  private static final Logger LOG = LoggerFactory.getLogger(ProductTypeService.class);

  @Autowired
  private transient ProductTypeRepository repository;

  /**
   * 查询所有的产品类型。
   *
   * @return
   */
  public List<ProductType> getAll() {
    LOG.debug("Enter.");

    List<ProductType> productTypes = repository.findAll();


    LOG.debug("Exit. productType size: {}.", productTypes.size());

    return productTypes;
  }

  /**
   * 根据id查询产品类型
   *
   * @param id
   * @return
   */
  public ProductType getById(String id) {
    LOG.debug("Enter. id: {}.", id);

    ProductType result = repository.findOne(id);

    if (result == null) {
      LOG.debug("Can not find productType: {}.", id);
      throw new NotExistException("ProductType is not exist");
    }

    LOG.debug("Exit. productType: {}.", result);
    return result;
  }

  public void exists(String id) {
    LOG.debug("Enter. id: {}.", id);

    boolean exists = repository.exists(id);

    if (!exists) {
      LOG.debug("Can not find productType: {}.", id);
      throw new NotExistException("ProductType is not exist");
    }

    LOG.debug("Exit. productType exist? {}.", exists);
  }

  public void delete(String id) {
    LOG.info("Enter. product type id: {}.", id);

    repository.delete(id);

    LOG.info("Exit.");
  }
}
