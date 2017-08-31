package com.umasuo.product.application.service;

import static org.springframework.http.HttpMethod.DELETE;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.HttpMethod.PUT;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.umasuo.exception.ParametersException;
import com.umasuo.product.application.dto.CommonDataView;
import com.umasuo.product.application.dto.CopyRequest;
import com.umasuo.product.application.dto.ProductDataView;
import com.umasuo.product.application.dto.action.AddDataDefinition;
import com.umasuo.product.application.dto.action.AddProductTypeData;
import com.umasuo.product.infrastructure.update.UpdateRequest;
import com.umasuo.product.infrastructure.util.HttpEntityUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Rest client，用于调用其它服务的接口.
 */
@Component
public class RestClient {

  /**
   * LOG.
   */
  private final static Logger LOG = LoggerFactory.getLogger(RestClient.class);

  /**
   * Data-definition service uri.
   */
  @Value("${datadefinition.service.uri:http://data-definition}")
  private transient String dataDefinitionUrl;

  /**
   * RestTemplate.
   */
  private transient RestTemplate restTemplate = new RestTemplate();

  /**
   * Create data definition.
   *
   * @param developerId the developer id
   * @param action the action
   * @return the string
   */
  public String createDataDefinition(String developerId, AddDataDefinition action) {
    LOG.debug("Enter.");

    HttpEntity entity = HttpEntityUtils.build(developerId, action);

    String newDataDefinitionId = null;

    try {

      ResponseEntity response =
          restTemplate.exchange(dataDefinitionUrl, POST, entity, Map.class);
      newDataDefinitionId = ((LinkedHashMap) response.getBody()).get("id").toString();
    } catch (RestClientException ex) {
      LOG.debug("Wrong when create dataDefinition.", ex);
      throw new ParametersException("Something wrong when create dataDefinition");
    }
    LOG.debug("Exit. new dataDefinition id: {}.", newDataDefinitionId);

    return newDataDefinitionId;
  }

  /**
   * 设备创建时调用, 将定义好的数据定义复制一分到新定义的设备名下，如果复制出错，返回空的，待后面重新添加，不妨碍设备创建.
   *
   * @param developerId 开发者ID
   * @param request the request
   * @return the list
   */
  public List<String> copyDataDefinitions(String developerId, CopyRequest request) {
    LOG.debug("Enter. developerId: {}, dataDefinitionIds: {}.", developerId, request);

    List<String> result = Lists.newArrayList();

    HttpEntity entity = HttpEntityUtils.build(developerId, request);

    String url = dataDefinitionUrl + "/copy";

    LOG.debug("url: {}.", url);

    try {
      HttpEntity<List<String>> response = restTemplate.exchange(url, POST, entity,
          new ParameterizedTypeReference<List<String>>() {
          });
      result = Lists.newArrayList(response.getBody());
    } catch (RestClientException ex) {
      LOG.warn("Copy data definition failed.", ex);
      //todo add message or retry
    }
    return result;
  }

  /**
   * 根据productId删除该产品所有的DataDefinition.
   *
   * @param developerId the developer id
   * @param productId the product id
   */
  public void deleteAllDataDefinition(String developerId, String productId) {
    LOG.debug("Enter. developerId: {}, productId: {}.", developerId, productId);

    HttpEntity entity = HttpEntityUtils.build(developerId);

    String url = UriComponentsBuilder.fromHttpUrl(dataDefinitionUrl)
        .queryParam("productId", productId).toUriString();

    try {
      restTemplate.exchange(url, DELETE, entity, Void.class);
    } catch (RestClientException ex) {
      LOG.debug("Something wrong when delete product dataDefinition.", ex);
    }

    LOG.debug("Exit.");
  }

  /**
   * 根据id和productId删除对应的DataDefinition。
   *
   * @param developerId the developer id
   * @param productId the product id
   * @param removeId the remove id
   */
  public void deleteDataDefinition(String developerId, String productId, String removeId) {
    LOG.debug("Enter. developerId: {}, productId: {}, removed dataDefinition: {}.",
        developerId, productId, removeId);

    HttpEntity entity = HttpEntityUtils.build(developerId);

    String url = UriComponentsBuilder.fromHttpUrl(dataDefinitionUrl + "/" + removeId)
        .queryParam("productId", productId).toUriString();

    try {
      restTemplate.exchange(url, DELETE, entity, Void.class);

    } catch (RestClientException ex) {
      LOG.debug("Something wrong when delete product dataDefinition.", ex);
    }

    LOG.debug("Exit.");
  }

  /**
   * Update data definition.
   *
   * @param dataDefinitionId the data definition id
   * @param developerId the developer id
   * @param request the request
   */
  public void updateDataDefinition(String dataDefinitionId, String developerId,
      UpdateRequest request) {
    LOG.debug("Enter. developerId: {}.", developerId);

    HttpEntity entity = HttpEntityUtils.build(developerId, request);

    String url = dataDefinitionUrl + "/" + dataDefinitionId;

    restTemplate.exchange(url, PUT, entity, Void.class);

    LOG.debug("Exit. update done.");
  }

  /**
   * Gets product data.
   *
   * @param developerId the developer id
   * @param productIds the product ids
   * @return the product data
   */
  public Map<String, List<ProductDataView>> getProductData(String developerId,
      List<String> productIds) {

    LOG.debug("Enter. developerId: {}, productIds: {}.", developerId, productIds);

    HttpEntity entity = HttpEntityUtils.build(developerId);

    UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(dataDefinitionUrl)
        .queryParam("productIds", String.join(",", productIds));

    String url = builder.build().encode().toUriString();

    LOG.debug("data definition url: {}.", url);

    Map<String, List<ProductDataView>> result = Maps.newHashMap();

    try {

      ResponseEntity<Map<String, List<ProductDataView>>> responseEntity =
          restTemplate.exchange(url, GET, entity,
              new ParameterizedTypeReference<Map<String, List<ProductDataView>>>() {
              });

      result = responseEntity.getBody();
    } catch (RestClientException ex) {
      LOG.debug("Can not get data definition: {}", url, ex);
    }

    LOG.debug("Exit. productData size: {}.", result.size());

    return result;
  }

  /**
   * 根据productId获取该product对应的所有DataDefinition.
   *
   * @param developerId the developer id
   * @param productId the product id
   * @return the product data
   */
  public List<ProductDataView> getProductData(String developerId, String productId) {

    LOG.debug("Enter. developerId: {}, productId: {}.", developerId, productId);

    HttpEntity entity = HttpEntityUtils.build(developerId);

    UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(dataDefinitionUrl)
        .queryParam("productId", productId);

    String url = builder.build().encode().toUriString();
    List<ProductDataView> result = Lists.newArrayList();
    try {

      ResponseEntity<List<ProductDataView>> responseEntity = restTemplate.exchange(url, GET,
          entity, new ParameterizedTypeReference<List<ProductDataView>>() {
          });

      result = responseEntity.getBody();
    } catch (RestClientException ex) {
      LOG.debug("Can not get data definition by url: {}.", url, ex);
    }

    LOG.debug("Exit. productData size: {}.", result.size());

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
    LOG.debug("Enter. developerId: {}, definitionIds: {}.", developerId, definitionIds);

    UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(dataDefinitionUrl)
        .queryParam("developerId", developerId)
        .queryParam("dataIds", String.join(",", definitionIds));

    String url = builder.build().encode().toUriString();

    Map<String, Boolean> result = restTemplate.getForObject(url, Map.class);

    LOG.debug("Exit. result: {}.", result);

    return result;
  }


  /**
   * Create product type data string.
   *
   * @param action the action
   * @return the string
   */
  public String createProductTypeData(AddProductTypeData action) {
    LOG.debug("Enter. action: {}.", action);

    LOG.debug("Enter.");

    HttpEntity entity = HttpEntityUtils.build(action);

    String newDataDefinitionId = null;

    try {
      String url = dataDefinitionUrl + "/platform";
      LOG.debug("url: {}.", url);
      ResponseEntity response = restTemplate.exchange(url, POST, entity, Map.class);
      newDataDefinitionId = ((LinkedHashMap) response.getBody()).get("id").toString();
    } catch (RestClientException ex) {
      LOG.debug("Wrong when create dataDefinition.", ex);
      throw new ParametersException("Something wrong when create dataDefinition");
    }

    LOG.debug("Exit. new dataDefinition id: {}.", newDataDefinitionId);

    return newDataDefinitionId;
  }

  /**
   * Delete platform data.
   *
   * @param productTypeId the product type id
   */
  public void deleteProductTypeData(String productTypeId) {
    LOG.debug("Enter. productTypeId: {}.", productTypeId);

    String url = UriComponentsBuilder.fromHttpUrl(dataDefinitionUrl + "/platform")
        .queryParam("productTypeId", productTypeId).toUriString();

    try {
      restTemplate.delete(url);
    } catch (RestClientException ex) {
      LOG.debug("Something wrong when delete product type dataDefinition.", ex);
    }

    LOG.debug("Exit. delete done.");
  }

  /**
   * Delete platform data.
   *
   * @param productTypeId the product type id
   * @param dataDefinitionId the data definition id
   */
  public void deleteProductTypeData(String productTypeId, String dataDefinitionId) {
    LOG.debug("Enter. productTypeId: {}, dataDefinitionId: {}.", productTypeId, dataDefinitionId);
    String url = UriComponentsBuilder
        .fromHttpUrl(dataDefinitionUrl + "/platform/" + dataDefinitionId)
        .queryParam("productTypeId", productTypeId).toUriString();
    try {
      restTemplate.delete(url);
    } catch (RestClientException ex) {
      LOG.debug("Something wrong when delete product type dataDefinition.", ex);
    }

    LOG.debug("Exit. delete done.");
  }

  /**
   * Update platform data.
   *
   * @param dataDefinitionId the data definition id
   * @param request the request
   */
  public void updateProductTypeData(String dataDefinitionId, UpdateRequest request) {
    LOG.debug("Enter. dataDefinitionId: {}, updateRequest: {}.", dataDefinitionId, request);

    HttpEntity entity = HttpEntityUtils.build(request);

    String url = dataDefinitionUrl + "/platform/" + dataDefinitionId;

    restTemplate.exchange(url, PUT, entity, Void.class);

    LOG.debug("Exit. update done.");
  }

  /**
   * Gets platform data definition.
   *
   * @return the platform data definition
   */
  public Map<String, List<CommonDataView>> getProductTypeData() {
    LOG.debug("Enter.");
    String url = dataDefinitionUrl + "/platform";

    LOG.debug("Url: {}.", url);

    Map<String, List<CommonDataView>> result = Maps.newHashMap();

    try {
      ResponseEntity<Map<String, List<CommonDataView>>> responseEntity =
          restTemplate.exchange(url, GET, null,
              new ParameterizedTypeReference<Map<String, List<CommonDataView>>>() {
              });

      result = responseEntity.getBody();
    } catch (RestClientException ex) {
      LOG.warn("Fetch data definition failed.", ex);
    }

    LOG.debug("Exit. result size: {}.", result.size());

    return result;
  }
}
