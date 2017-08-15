package com.umasuo.product.domain.service;

import com.umasuo.exception.AlreadyExistException;
import com.umasuo.exception.NotExistException;
import com.umasuo.product.domain.model.Product;
import com.umasuo.product.infrastructure.repository.ProductRepository;

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
  private transient ProductRepository repository;

  /**
   * 新建product.
   *
   * @param product the product
   * @return product product
   */
  public Product save(Product product) {
    logger.debug("Enter. product: {}.", product);

    Product savedProduct = repository.save(product);

    logger.debug("Exit. saved product: {}.", savedProduct);
    return savedProduct;
  }

  /**
   * Get product.
   *
   * @param id the id
   * @return product product
   */
  public Product get(String id) {
    logger.debug("Enter. id: {}.", id);

    Product product = repository.findOne(id);
    if (product == null) {
      throw new NotExistException("Product not exist, id: " + id);
    }

    logger.debug("Exit. product: {}.", product);
    return product;
  }

  /**
   * Gets by developer id.
   *
   * @param developerId the developer id
   * @return by developer id
   */
  public List<Product> getByDeveloperId(String developerId) {
    logger.debug("Enter. developerId: {}.", developerId);

    List<Product> products = repository.findAllByDeveloperId(developerId);

    logger.debug("Exit. products size: {}.", products.size());

    return products;
  }

  /**
   * Gets all open product.
   *
   * @param developerId the developer id
   * @return the all openable product
   */
  public List<Product> getAllOpenProduct(String developerId) {
    logger.debug("Enter. developerId: {}.", developerId);

    Product sample = new Product();
    sample.setDeveloperId(developerId);
    sample.setOpenable(true);

    Example<Product> exam = Example.of(sample);

    List<Product> products = repository.findAll(exam);

    logger.debug("Exit. products size: {}.", products.size());

    return products;
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

    boolean result = repository.exists(example);

    if (result) {
      logger.debug("Product name: {} has existed in developer: {}.", name, developerId);
      throw new AlreadyExistException("Product name has existed");
    }

    logger.debug("Exit. name: {} exist: {}.", name, result);

    return result;
  }

  public void delete(String id) {
    logger.debug("Enter. id: {}.", id);

    repository.delete(id);

    logger.debug("Exit.");
  }
}
