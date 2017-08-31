package com.umasuo.product.infrastructure.util;

/**
 * Redis缓存使用的key.
 */
public class RedisUtils {

  /**
   * 缓存ProductType使用的key。
   */
  public static final String PRODUCT_TYPE_KEY = "product:producttype";

  /**
   * 缓存Product使用的key。
   */
  public static final String PRODUCT_KEY_FORMAT = "product:%s";
}
