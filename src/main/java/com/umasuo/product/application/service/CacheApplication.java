package com.umasuo.product.application.service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.umasuo.product.application.dto.ProductTypeView;
import com.umasuo.product.application.dto.ProductView;
import com.umasuo.product.infrastructure.util.RedisUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 用于处理缓存。
 */
@Service
public class CacheApplication {

  /**
   * Logger.
   */
  private static final Logger LOG = LoggerFactory.getLogger(CacheApplication.class);

  /**
   * The redis template.
   */
  @Autowired
  private transient RedisTemplate redisTemplate;

  /**
   * Gets all ProductType.
   *
   * @return the all product type
   */
  public List<ProductTypeView> getAllProductType() {
    LOG.debug("Enter.");
    List<ProductTypeView> result = Lists.newArrayList();

    Map<String, ProductTypeView> cacheProductTypes =
        redisTemplate.opsForHash().entries(RedisUtils.PRODUCT_TYPE_KEY);

    if (!CollectionUtils.isEmpty(cacheProductTypes)) {
      result = cacheProductTypes.values().stream()
          .sorted((view1, view2) -> view2.getCreatedAt().compareTo(view1.getCreatedAt()))
          .collect(Collectors.toList());
    }

    LOG.trace("ProductType: {}.", result);
    LOG.debug("Exit. productType size: {}.", result.size());
    return result;
  }

  /**
   * Cache ProductType.
   *
   * @param productTypeViews the product type views
   */
  @Async
  public void cacheProductType(List<ProductTypeView> productTypeViews) {
    LOG.debug("Enter. productType size: {}.", productTypeViews.size());

    Map<String, ProductTypeView> cacheProductTypes = Maps.newHashMap();
    productTypeViews.stream().forEach(view -> cacheProductTypes.put(view.getId(), view));
    redisTemplate.opsForHash().putAll(RedisUtils.PRODUCT_TYPE_KEY, cacheProductTypes);

    LOG.debug("Exit. cache done.");
  }

  /**
   * Delete ProductType from cache.
   */
  @Async
  public void deleteProductTypes() {
    LOG.debug("Enter.");

    redisTemplate.delete(RedisUtils.PRODUCT_TYPE_KEY);

    LOG.debug("Exit. delete done.");
  }

  /**
   * Gets products by developerId.
   *
   * @param developerId the developer id
   * @return the products
   */
  public List<ProductView> getProducts(String developerId) {
    LOG.debug("Enter. developerId: {}.", developerId);

    List<ProductView> result = Lists.newArrayList();

    String key = String.format(RedisUtils.PRODUCT_KEY_FORMAT, developerId);
    Map<String, ProductView> cacheProducts = redisTemplate.opsForHash().entries(key);

    if (!CollectionUtils.isEmpty(cacheProducts)) {
      result = cacheProducts.values().stream()
          .sorted((view1, view2) -> view2.getCreatedAt().compareTo(view1.getCreatedAt()))
          .collect(Collectors.toList());
    }

    LOG.trace("Products: {}.", result);
    LOG.debug("Exit. products size: {}.", result.size());
    return result;
  }

  /**
   * Cache products.
   *
   * @param developerId the developer id
   * @param products the products
   */
  @Async
  public void cacheProducts(String developerId, List<ProductView> products) {
    LOG.debug("Enter. products size: {}.", products.size());

    Map<String, ProductView> cacheProducts = Maps.newHashMap();
    products.stream().forEach(view -> cacheProducts.put(view.getId(), view));

    redisTemplate.opsForHash()
        .putAll(String.format(RedisUtils.PRODUCT_KEY_FORMAT, developerId), cacheProducts);

    LOG.debug("Exit. cache done.");
  }

  /**
   * Gets product by it's id and developerId.
   *
   * @param developerId the developer id
   * @param productId the product id
   * @return the product by id
   */
  public ProductView getProductById(String developerId, String productId) {
    LOG.debug("Enter. developerId: {}, productId: {}.", developerId, productId);

    String key = String.format(RedisUtils.PRODUCT_KEY_FORMAT, developerId);

    ProductView result = (ProductView) redisTemplate.opsForHash().get(key, productId);

    LOG.debug("Exit. product: {}.", result);
    return result;
  }

  /**
   * Delete products by developerId.
   *
   * @param developerId the developer id
   */
  @Async
  public void deleteProducts(String developerId) {
    LOG.debug("Enter. developerId: {}.", developerId);

    redisTemplate.delete(String.format(RedisUtils.PRODUCT_KEY_FORMAT, developerId));

    LOG.debug("Exit. delete done.");
  }
}
