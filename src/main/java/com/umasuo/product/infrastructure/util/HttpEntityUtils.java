package com.umasuo.product.infrastructure.util;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;

/**
 * 用于创建HttpEntity.
 */
public final class HttpEntityUtils {

  /**
   * Private constructor.
   */
  private HttpEntityUtils() {
  }

  /**
   * Build a HttpEntity with developerId in header.
   *
   * @param developerId the developerId
   * @return HttpEntity
   */
  public static HttpEntity build(String developerId) {
    HttpHeaders headers = buildHeaders(developerId);

    return new HttpEntity(headers);
  }

  /**
   * Build a HttpEntity with developerId in header and add request body.
   *
   * @param developerId the developerId
   * @param requestBody the requestBody
   * @return HttpEntity
   */
  public static HttpEntity build(String developerId, Object requestBody) {
    HttpHeaders headers = buildHeaders(developerId);

    return new HttpEntity(requestBody, headers);
  }

  /**
   * Build a HttpEntity with request body.
   *
   * @param requestBody the requestBody
   * @return HttpEntity
   */
  public static HttpEntity build(Object requestBody) {
    HttpHeaders headers = buildJsonHeader();

    return new HttpEntity(requestBody, headers);
  }

  /**
   * Build a HttpHeaders with developerId in header.
   *
   * @param developerId the developerId
   * @return HttpHeaders
   */
  private static HttpHeaders buildHeaders(String developerId) {
    HttpHeaders headers = buildJsonHeader();
    headers.set("developerId", developerId);
    return headers;
  }

  /**
   * Build a HttpHeaders use application/json as Content-Type.
   *
   * @return HttpHeaders
   */
  private static HttpHeaders buildJsonHeader() {
    HttpHeaders headers = new HttpHeaders();
    headers.set("Content-Type", "application/json");
    return headers;
  }
}
