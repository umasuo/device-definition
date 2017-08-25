package com.umasuo.product.application.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.collect.Lists;
import com.umasuo.product.application.dto.ProductDataView;
import com.umasuo.product.application.dto.ProductView;
import com.umasuo.product.application.dto.mapper.ProductMapper;
import com.umasuo.product.domain.model.Product;
import com.umasuo.product.domain.service.ProductService;
import com.umasuo.product.infrastructure.util.JsonUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by umasuo on 17/6/1.
 */
@Service
public class ProductQueryApplication {

  /**
   * Logger.
   */
  private final static Logger logger = LoggerFactory.getLogger(ProductQueryApplication.class);

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

  @Autowired
  private transient CacheApplication cacheApplication;

  /**
   * get product by id.
   *
   * @param id String
   */
  public ProductView get(String id, String developerId) {
    logger.debug("Enter. id: {}, developerId: {}.", id, developerId);

    ProductView result = cacheApplication.getProductById(developerId, id);

    if (result == null) {
      logger.debug("Cache fail, get from database.");

      List<ProductView> productViews = fetchProducts(developerId);

      result = productViews.stream().filter(view -> id.equals(view.getId())).findAny().orElse(null);
    }

    logger.debug("Exit. productView: {}.", result);
    return result;
  }

  /**
   * get all product define by developer id.
   *
   * @param developerId developer id
   * @return list of product view
   */
  public List<ProductView> getAllByDeveloperId(String developerId) {
    logger.debug("Enter. developerId: {}.", developerId);

    List<ProductView> result = cacheApplication.getProducts(developerId);

    if (result.isEmpty()) {
      logger.debug("Cache fail, get from database.");
      result = fetchProducts(developerId);
    }

    result.stream().forEach(
        productView -> productView.getDataDefinitions().stream().forEach(
            data -> data.setDataSchema(JsonUtils.deserialize(data.getSchema(), JsonNode.class))
        )
    );

    logger.trace("products: {}.", result);
    logger.debug("Exit. product Size: {}.", result.size());
    return result;
  }

  public List<ProductDataView> getDataDefinitions(String developerId, String productId) {
    logger.debug("Enter. developerId: {}, productId: {}.", developerId, productId);

    List<ProductDataView> dataViews = Lists.newArrayList();
    ProductView product = get(productId, developerId);

    if (product != null && product.getDataDefinitions() != null) {
      dataViews = product.getDataDefinitions();
    }

    logger.debug("Exit. dataDefinition size: {}.", dataViews.size());

    return dataViews;
  }

  /**
   * Get all open product define by developer id.
   * 接口比较少用，暂时不需要使用缓存。
   *
   * @param id developer id
   * @return list of product view
   */
  public List<ProductView> getAllOpenProduct(String id) {
    logger.debug("Enter. developerId: {}.", id);

    List<Product> products = productService.getAllOpenProduct(id);
    List<ProductView> views = ProductMapper.toView(products);

    logger.trace("products: {}.", views);
    logger.debug("Exit. product Size: {}.", views.size());
    return views;
  }

  private List<ProductView> fetchProducts(String developerId) {
    logger.debug("Enter. developerId: {}.", developerId);

    List<Product> products = productService.getByDeveloperId(developerId);

    List<ProductView> result = ProductMapper.toView(products);

    List<String> productIds = products.stream().map(Product::getId).collect(Collectors.toList());

    Map<String, List<ProductDataView>> productDataViews =
        restClient.getProductData(developerId, productIds);

    mergeProductData(result, productDataViews);

    cacheApplication.cacheProducts(developerId, result);

    logger.trace("Product: {}.", result);
    logger.debug("Exit. product size: {}.", result.size());

    return result;
  }

  private void mergeProductData(List<ProductView> productViews,
      Map<String, List<ProductDataView>> productDataViews) {
    logger.debug("Enter.");

    productViews.stream().forEach(
        product -> product.setDataDefinitions(productDataViews.get(product.getId())));

    logger.debug("Exit.");
  }

  public Long countProducts() {
    logger.debug("Enter.");

    Long count = productService.count();

    logger.debug("Exit. product count: {}.", count);

    return count;
  }
}
