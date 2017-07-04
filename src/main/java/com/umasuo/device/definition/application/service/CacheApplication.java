package com.umasuo.device.definition.application.service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.umasuo.device.definition.application.dto.DeviceView;
import com.umasuo.device.definition.application.dto.ProductTypeView;
import com.umasuo.device.definition.infrastructure.util.RedisUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by Davis on 17/7/4.
 */
@Service
public class CacheApplication {

  /**
   * Logger.
   */
  private static final Logger LOG = LoggerFactory.getLogger(CacheApplication.class);

  @Autowired
  private transient RedisTemplate redisTemplate;

  public List<ProductTypeView> getAllProductType() {
    LOG.debug("Enter.");
    List<ProductTypeView> result = Lists.newArrayList();

    Map<String, Object> cacheProductTypes =
        redisTemplate.opsForHash().entries(RedisUtils.PRODUCT_TYPE_KEY);

    if (cacheProductTypes != null && !cacheProductTypes.isEmpty()) {
      cacheProductTypes.entrySet().stream().forEach(
          entry -> result.add((ProductTypeView) entry.getValue()));
    }

    LOG.trace("ProductType: {}.", result);
    LOG.debug("Exit. productType size: {}.", result.size());
    return result;
  }

  public void batchCacheProductType(List<ProductTypeView> productTypeViews) {
    LOG.debug("Enter. productType size: {}.", productTypeViews.size());

    Map<String, ProductTypeView> cacheProductTypes = Maps.newHashMap();
    productTypeViews.stream().forEach(view -> cacheProductTypes.put(view.getId(), view));
    redisTemplate.opsForHash().putAll(RedisUtils.PRODUCT_TYPE_KEY, cacheProductTypes);

    LOG.debug("Exit.");
  }

  public List<DeviceView> getDeveloperProduct(String developerId) {
    LOG.debug("Enter. developerId: {}.", developerId);

    List<DeviceView> result = Lists.newArrayList();

    String key = String.format(RedisUtils.PRODUCT_KEY_FORMAT, developerId);
    Map<String, Object> cacheProducts = redisTemplate.opsForHash().entries(key);
    if (cacheProducts != null && !cacheProducts.isEmpty()) {
      cacheProducts.entrySet().stream().forEach(
          entry -> result.add((DeviceView) entry.getValue())
      );
    }

    LOG.trace("Products: {}.", result);
    LOG.debug("Exit. products size: {}.", result.size());
    return result;
  }

  public void batchCacheProduct(String developerId, List<DeviceView> products) {
    LOG.debug("Enter. products size: {}.", products.size());

    Map<String, DeviceView> cacheProducts = Maps.newHashMap();
    products.stream().forEach(view -> cacheProducts.put(view.getId(), view));
    redisTemplate.opsForHash()
        .putAll(String.format(RedisUtils.PRODUCT_KEY_FORMAT, developerId), cacheProducts);
  }

  public DeviceView getProductById(String developerId, String productId) {
    LOG.debug("Enter. developerId: {}, productId: {}.", developerId, productId);

    DeviceView result = (DeviceView)redisTemplate.opsForHash().get(developerId, productId);

    LOG.debug("Exit. product: {}.", result);
    return result;
  }

  public void deleteDeveloperProducts(String developerId) {
    LOG.debug("Enter. developerId: {}.", developerId);

    redisTemplate.delete(String.format(RedisUtils.PRODUCT_KEY_FORMAT, developerId));

  }
}
