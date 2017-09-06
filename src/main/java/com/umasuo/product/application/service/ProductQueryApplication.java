package com.umasuo.product.application.service;

import com.google.common.collect.Lists;
import com.umasuo.product.application.dto.ProductDataView;
import com.umasuo.product.application.dto.ProductView;
import com.umasuo.product.application.dto.mapper.ProductMapper;
import com.umasuo.product.domain.model.Product;
import com.umasuo.product.domain.service.ProductService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 用于查询Product.
 */
@Service
public class ProductQueryApplication {

  /**
   * Logger.
   */
  private final static Logger LOG = LoggerFactory.getLogger(ProductQueryApplication.class);

  /**
   * ProductService.
   */
  @Autowired
  private transient ProductService productService;

  /**
   * RestClient.
   */
  @Autowired
  private transient RestClient restClient;

  /**
   * CacheApplication.
   */
  @Autowired
  private transient CacheApplication cacheApplication;

  /**
   * Get product by id.
   *
   * @param id String
   * @param developerId the developer id
   * @return the product view
   */
  public ProductView get(String id, String developerId) {
    LOG.debug("Enter. id: {}, developerId: {}.", id, developerId);

    ProductView result = cacheApplication.getProductById(developerId, id);

    if (result == null) {
      LOG.debug("Cache fail, get from database.");

      List<ProductView> productViews = fetchProducts(developerId);

      result = productViews.stream().filter(view -> id.equals(view.getId())).findAny().orElse(null);
    }

    LOG.debug("Exit. productView: {}.", result);
    return result;
  }

  /**
   * Get all product by developer id.
   *
   * @param developerId developer id
   * @return list build product view
   */
  public List<ProductView> getAllByDeveloperId(String developerId) {
    LOG.debug("Enter. developerId: {}.", developerId);

    List<ProductView> result = cacheApplication.getProducts(developerId);

    if (result.isEmpty()) {
      LOG.debug("Cache fail, get from database.");
      result = fetchProducts(developerId);
    }

    LOG.trace("products: {}.", result);
    LOG.debug("Exit. product Size: {}.", result.size());
    return result;
  }

  /**
   * Gets data definitions by productId.
   *
   * @param developerId the developer id
   * @param productId the product id
   * @return the data definitions
   */
  public List<ProductDataView> getDataDefinitions(String developerId, String productId) {
    LOG.debug("Enter. developerId: {}, productId: {}.", developerId, productId);

    List<ProductDataView> dataViews = Lists.newArrayList();
    ProductView product = get(productId, developerId);

    if (product != null && product.getDataDefinitions() != null) {
      dataViews = product.getDataDefinitions();
    }

    LOG.debug("Exit. dataDefinition size: {}.", dataViews.size());

    return dataViews;
  }

  /**
   * Get all open product define by developer id.
   * 接口比较少用，暂时不需要使用缓存。
   *
   * @param id developer id
   * @return list build product view
   */
  public List<ProductView> getAllOpenProduct(String id) {
    LOG.debug("Enter. developerId: {}.", id);

    List<Product> products = productService.getAllOpenProduct(id);
    List<ProductView> views = ProductMapper.toView(products);

    LOG.trace("products: {}.", views);
    LOG.debug("Exit. product Size: {}.", views.size());
    return views;
  }

  /**
   * Count products.
   *
   * @return Long
   */
  public Long countProducts() {
    LOG.debug("Enter.");

    Long count = productService.countProducts();

    LOG.debug("Exit. product countProducts: {}.", count);

    return count;
  }

  /**
   * Fetch product by developerId.
   *
   * @param developerId the developerId
   * @return list build ProductView
   */
  private List<ProductView> fetchProducts(String developerId) {
    LOG.debug("Enter. developerId: {}.", developerId);

    List<Product> products = productService.getByDeveloperId(developerId);

    List<ProductView> result = ProductMapper.toView(products);

    List<String> productIds = products.stream().map(Product::getId).collect(Collectors.toList());

    Map<String, List<ProductDataView>> productDataViews =
        restClient.getProductData(developerId, productIds);

    mergeProductData(result, productDataViews);

    cacheApplication.cacheProducts(developerId, result);

    LOG.trace("Product: {}.", result);
    LOG.debug("Exit. product size: {}.", result.size());

    return result;
  }

  /**
   * Merge ProductData into Product.
   *
   * @param productViews list build Product
   * @param productDataViews list build ProductData
   */
  private void mergeProductData(List<ProductView> productViews,
      Map<String, List<ProductDataView>> productDataViews) {
    LOG.debug("Enter.");

    productViews.stream().forEach(
        product -> product.setDataDefinitions(productDataViews.get(product.getId())));

    LOG.debug("Exit.");
  }
}
