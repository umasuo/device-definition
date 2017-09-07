package com.umasuo.product.domain.service;

import com.umasuo.exception.NotExistException;
import com.umasuo.product.domain.model.ProductType;
import com.umasuo.product.infrastructure.repository.ProductTypeRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service class for ProductType.
 */
@Service
public class ProductTypeService {

  /**
   * Logger.
   */
  private static final Logger LOG = LoggerFactory.getLogger(ProductTypeService.class);

  /**
   * ProductTypeRepository.
   */
  @Autowired
  private transient ProductTypeRepository repository;

  /**
   * Save product type.
   *
   * @param productType the product type
   * @return product type
   */
  public ProductType save(ProductType productType) {
    LOG.debug("Enter. productType: {}", productType);

    ProductType newProductType = repository.save(productType);

    LOG.debug("Exit. new productType id: {}.", newProductType.getId());

    return newProductType;
  }

  /**
   * Delete by id.
   *
   * @param id the id
   */
  public void delete(String id) {
    LOG.info("Enter. product type id: {}.", id);

    repository.delete(id);

    LOG.info("Exit.");
  }

  /**
   * 查询所有的产品类型。
   *
   * @return all
   */
  public List<ProductType> getAll() {
    LOG.debug("Enter.");

    List<ProductType> productTypes = repository.findAllByOrderByCreatedAt();

    LOG.debug("Exit. productType size: {}.", productTypes.size());

    return productTypes;
  }

  /**
   * 根据id查询产品类型
   *
   * @param id the id
   * @return by id
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

  /**
   * Exists.
   *
   * @param id the id
   */
  public void exists(String id) {
    LOG.debug("Enter. id: {}.", id);

    boolean exists = repository.exists(id);

    if (!exists) {
      LOG.debug("Can not find productType: {}.", id);
      throw new NotExistException("ProductType is not exist");
    }

    LOG.debug("Exit. productType exist? {}.", exists);
  }
}
