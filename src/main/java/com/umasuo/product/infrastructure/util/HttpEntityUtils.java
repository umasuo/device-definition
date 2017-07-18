package com.umasuo.product.infrastructure.util;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;

/**
 * Created by Davis on 17/7/14.
 */
public final class HttpEntityUtils {

  /**
   * Instantiates a new Http header utils.
   */
  private HttpEntityUtils() {
  }

  public static HttpEntity build(String developerId) {
    HttpHeaders headers = new HttpHeaders();
    headers.set("developerId", developerId);
    headers.set("Content-Type", "application/json");

    return new HttpEntity(headers);
  }

  public static HttpEntity build(String developerId, Object requestBody) {
    HttpHeaders headers = new HttpHeaders();
    headers.set("developerId", developerId);
    headers.set("Content-Type", "application/json");

    return new HttpEntity(requestBody, headers);
  }
}
