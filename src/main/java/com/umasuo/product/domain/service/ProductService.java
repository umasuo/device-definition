package com.umasuo.product.domain.service;

import com.umasuo.exception.AlreadyExistException;
import com.umasuo.exception.NotExistException;
import com.umasuo.product.domain.model.Product;
import com.umasuo.product.infrastructure.repository.ProductRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service class for Product.
 */
@Service
public class ProductService {

  /**
   * Logger.
   */
  private static final Logger LOG = LoggerFactory.getLogger(ProductService.class);

  /**
   * ProductRepository.
   */
  @Autowired
  private transient ProductRepository repository;

  /**
   * 保存product.
   *
   * @param product the product
   * @return product product
   */
  public Product save(Product product) {
    LOG.debug("Enter. product: {}.", product);

    Product savedProduct = repository.save(product);

    LOG.debug("Exit. saved product: {}.", savedProduct);
    return savedProduct;
  }

  /**
   * Delete product by it's id.
   *
   * @param id the id
   */
  public void delete(String id) {
    LOG.debug("Enter. id: {}.", id);

    repository.delete(id);

    LOG.debug("Exit.");
  }

  /**
   * Gets by developer id.
   *
   * @param developerId the developer id
   * @return by developer id
   */
  public List<Product> getByDeveloperId(String developerId) {
    LOG.debug("Enter. developerId: {}.", developerId);

    Product sample = new Product();
    sample.setDeveloperId(developerId);

    Example<Product> example = Example.of(sample);
    Sort sort = new Sort(Direction.ASC, "createdAt");

    List<Product> products = repository.findAll(example, sort);

    LOG.debug("Exit. products size: {}.", products.size());

    return products;
  }

  /**
   * Get product.
   *
   * @param id the id
   * @return product product
   */
  public Product get(String id) {
    LOG.debug("Enter. id: {}.", id);

    Product product = repository.findOne(id);
    if (product == null) {
      throw new NotExistException("Product not exist, id: " + id);
    }

    LOG.debug("Exit. product: {}.", product);
    return product;
  }

  /**
   * Gets all open product.
   *
   * @param developerId the developer id
   * @return the all openable product
   */
  public List<Product> getAllOpenProduct(String developerId) {
    LOG.debug("Enter. developerId: {}.", developerId);

    Product sample = new Product();
    sample.setDeveloperId(developerId);
    sample.setOpenable(true);

    Example<Product> exam = Example.of(sample);

    List<Product> products = repository.findAll(exam);

    LOG.debug("Exit. products size: {}.", products.size());

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
    LOG.debug("Enter. developerId: {}, name: {}.", developerId, name);

    Product sample = new Product();
    sample.setDeveloperId(developerId);
    sample.setName(name);

    Example<Product> example = Example.of(sample);

    boolean result = repository.exists(example);

    if (result) {
      LOG.debug("Product name: {} has existed in developer: {}.", name, developerId);
      throw new AlreadyExistException("Product name has existed");
    }

    LOG.debug("Exit. name: {} exist: {}.", name, result);

    return result;
  }

  /**
   * Count products.
   *
   * @return long
   */
  public Long countProducts() {
    LOG.debug("Enter.");

    Long count = repository.count();

    LOG.debug("Exit. product countProducts: {}.", count);

    return count;
  }
}
