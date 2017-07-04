package com.umasuo.device.definition.application.service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
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

  public List<ProductTypeView> getAll() {
    List<ProductTypeView> result = Lists.newArrayList();


    Map<String, Object> cacheProductTypes =
        redisTemplate.opsForHash().entries(RedisUtils.PRODUCT_TYPE_KEY);

    return result;
  }

  public void batchCacheProductType(List<ProductTypeView> productTypeViews) {

    Map<String, ProductTypeView> finalCacheProductTypes = Maps.newHashMap();
    productTypeViews.stream().forEach(view -> finalCacheProductTypes.put(view.getId(), view));
    redisTemplate.opsForHash().putAll(RedisUtils.PRODUCT_TYPE_KEY, finalCacheProductTypes);
  }
}
