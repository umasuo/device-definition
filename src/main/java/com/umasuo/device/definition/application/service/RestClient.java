package com.umasuo.device.definition.application.service;

import static org.springframework.http.HttpMethod.DELETE;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.HttpMethod.PUT;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.umasuo.device.definition.application.dto.CommonDataView;
import com.umasuo.device.definition.application.dto.CopyRequest;
import com.umasuo.device.definition.application.dto.ProductDataView;
import com.umasuo.device.definition.application.dto.action.AddDataDefinition;
import com.umasuo.device.definition.infrastructure.update.UpdateRequest;
import com.umasuo.exception.ParametersException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by umasuo on 17/5/22.
 */
@Component
public class RestClient {

  /**
   * logger.
   */
  private final static Logger logger = LoggerFactory.getLogger(RestClient.class);

  /**
   * developer service uri
   */
  @Value("${developer.service.uri:http://developer}")
  private transient String developerUrl;

  /**
   * Data-definition service uri.
   */
  @Value("${datadefinition.service.uri:http://data-definition}")
  private transient String definitionUrl;

  /**
   * RestTemplate.
   */
  private transient RestTemplate restTemplate = new RestTemplate();

  /**
   * 调取开发者服务，检查给定的开发者是否存在.
   *
   * @param developerId 开发者ID
   * @return boolean boolean
   */
  public boolean isDeveloperExist(String developerId) {
    logger.debug("Enter. developerId: {}.", developerId);

    String url = developerUrl + developerId;
    logger.debug("check url: {}.", url);

    Boolean result = restTemplate.getForObject(url, Boolean.class);

    logger.debug("Exit. developer: {} exist? {}.", developerId, result);
    return result;
  }

  /**
   * Check definition exist.
   *
   * @param developerId the developer id
   * @param definitionIds the definition ids
   * @return the map
   */
  public Map<String, Boolean> checkDefinitionExist(String developerId, List<String> definitionIds) {
    logger.debug("Enter. developerId: {}, definitionIds: {}.", developerId, definitionIds);

    UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(definitionUrl)
        .queryParam("developerId", developerId)
        .queryParam("dataIds", String.join(",", definitionIds));

    String url = builder.build().encode().toUriString();

    Map<String, Boolean> result = restTemplate.getForObject(url, Map.class);

    logger.debug("Exit. result: {}.", result);

    return result;
  }

  public Map<String, List<CommonDataView>> getPlatformDataDefinition() {
    logger.debug("Enter.");
    String url = definitionUrl + "/platform";

    Map<String, List<CommonDataView>> result = Maps.newHashMap();

    try {
      result = restTemplate.getForObject(url, Map.class);
    } catch (Exception e) {
      logger.warn("Fetch data definition failed.", e);
    }

    logger.debug("Exit. result size: {}.", result.size());

    return result;
  }

  /**
   * 设备创建时调用, 将定义好的数据定义复制一分到新定义的设备名下，如果复制出错，返回空的，待后面重新添加，不妨碍设备创建.
   *
   * @param developerId 开发者ID
   */
  public List<String> copyDataDefinitions(String developerId, CopyRequest request) {
    logger.debug("Enter. developerId: {}, dataDefinitionIds: {}.", developerId, request);
    HttpHeaders headers = new HttpHeaders();
    headers.set("developerId", developerId);
    headers.set("Content-Type", "application/json");
    HttpEntity entity = new HttpEntity(request, headers);

    try {
      HttpEntity<String[]> response =
          restTemplate.exchange(definitionUrl + "/copy", POST, entity, String[].class);
      return Lists.newArrayList(response.getBody());
    } catch (RestClientException ex) {
      logger.warn("Fetch data definition failed.", ex);
      //todo add message or retry
      return new ArrayList<>();
    }
  }

  public void deleteDataDefinition(String developerId, String productId, String removeId) {
    logger.debug("Enter. developerId: {}, productId: {}, removed dataDefinition: {}.",
        developerId, productId, removeId);

    HttpHeaders headers = new HttpHeaders();
    headers.set("developerId", developerId);
    headers.set("Content-Type", "application/json");
    HttpEntity entity = new HttpEntity(headers);

    String url = UriComponentsBuilder.fromHttpUrl(definitionUrl + "/" + removeId)
        .queryParam("productId", productId).toUriString();

    try {
      restTemplate.exchange(url, DELETE, entity, Void.class);

    } catch (Exception ex) {
      logger.debug("Something wrong when delete dataDefinition.", ex);
    }

  }

  public String createDataDefinition(String developerId, AddDataDefinition action) {
    logger.debug("Enter.");
    HttpHeaders headers = new HttpHeaders();
    headers.set("developerId", developerId);
    headers.set("Content-Type", "application/json");
    HttpEntity entity = new HttpEntity(action, headers);

    String newDataDefinitionId = null;

    try {

      ResponseEntity response =
          restTemplate.exchange(definitionUrl, POST, entity, Map.class);
      newDataDefinitionId = ((LinkedHashMap) response.getBody()).get("id").toString();
    } catch (Exception ex) {
      logger.debug("Wrong when create dataDefinition.", ex);
      throw new ParametersException("Something wrong when create dataDefinition");
    }
    logger.debug("Exit.");

    return newDataDefinitionId;
  }

  public Map<String, List<ProductDataView>> getProductData(String developerId,
      List<String> productIds) {

    logger.debug("Enter. developerId: {}, productIds: {}.", developerId, productIds);

    HttpHeaders headers = new HttpHeaders();
    headers.set("developerId", developerId);
    headers.set("Content-Type", "application/json");

    HttpEntity entity = new HttpEntity(headers);

    UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(definitionUrl)
        .queryParam("productIds", String.join(",", productIds));

    String url = builder.build().encode().toUriString();

    ResponseEntity<Map> responseEntity =
        restTemplate.exchange(url, GET, entity, Map.class);

    Map<String, List<ProductDataView>> result = responseEntity.getBody();

    logger.debug("Exit. productData size: {}.", result.size());

    return result;
  }

  public List<ProductDataView> getProductData(String developerId, String productId) {

    logger.debug("Enter. developerId: {}, productId: {}.", developerId, productId);

    HttpHeaders headers = new HttpHeaders();
    headers.set("developerId", developerId);
    headers.set("Content-Type", "application/json");

    HttpEntity entity = new HttpEntity(headers);

    UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(definitionUrl)
        .queryParam("productId", productId);

    String url = builder.build().encode().toUriString();

    ResponseEntity<List> responseEntity =
        restTemplate.exchange(url, GET, entity, List.class);

    List<ProductDataView> result = responseEntity.getBody();

    logger.debug("Exit. productData size: {}.", result.size());

    return result;
  }

  public void updateDataDefinition(String dataDefinitionId, String developerId,
      UpdateRequest request) {
    logger.debug("Enter. developerId: {}.", developerId);

    HttpHeaders headers = new HttpHeaders();
    headers.set("developerId", developerId);
    headers.set("Content-Type", "application/json");

    HttpEntity entity = new HttpEntity(request, headers);

    String url = definitionUrl + "/" + dataDefinitionId;

    restTemplate.exchange(url, PUT, entity, Void.class);
  }
}
