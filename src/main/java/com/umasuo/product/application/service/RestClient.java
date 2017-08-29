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
   * LOG.
   */
  private final static Logger LOG = LoggerFactory.getLogger(RestClient.class);

  /**
   * Data-definition service uri.
   */
  @Value("${datadefinition.service.uri:http://data-definition}")
  private transient String definitionUrl;

  /**
   * RestTemplate.
   */
  private transient RestTemplate restTemplate = new RestTemplate();
  public String url;

  /**
   * Check definition exist.
   *
   * @param developerId the developer id
   * @param definitionIds the definition ids
   * @return the map
   */
  public Map<String, Boolean> checkDefinitionExist(String developerId, List<String> definitionIds) {
    LOG.debug("Enter. developerId: {}, definitionIds: {}.", developerId, definitionIds);

    UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(definitionUrl)
        .queryParam("developerId", developerId)
        .queryParam("dataIds", String.join(",", definitionIds));

    String url = builder.build().encode().toUriString();

    Map<String, Boolean> result = restTemplate.getForObject(url, Map.class);

    LOG.debug("Exit. result: {}.", result);

    return result;
  }

  public Map<String, List<CommonDataView>> getPlatformDataDefinition() {
    LOG.debug("Enter.");
    String url = definitionUrl + "/platform";

    LOG.debug("Url: {}.", url);

    Map<String, List<CommonDataView>> result = Maps.newHashMap();

    try {
      Map<String, List<CommonDataView>> dataView = restTemplate.getForObject(url, Map.class);

      dataView.entrySet().stream().forEach(
          entey -> result.put(entey.getKey(), CommonDataView.build(entey.getValue())));

    } catch (Exception e) {
      LOG.warn("Fetch data definition failed.", e);
    }

    LOG.debug("Exit. result size: {}.", result.size());

    return result;
  }

  /**
   * 设备创建时调用, 将定义好的数据定义复制一分到新定义的设备名下，如果复制出错，返回空的，待后面重新添加，不妨碍设备创建.
   *
   * @param developerId 开发者ID
   */
  public List<String> copyDataDefinitions(String developerId, CopyRequest request) {
    LOG.debug("Enter. developerId: {}, dataDefinitionIds: {}.", developerId, request);

    HttpHeaders headers = new HttpHeaders();
    headers.set("developerId", developerId);
    headers.set("Content-Type", "application/json");

    HttpEntity entity = new HttpEntity(request, headers);
    url = definitionUrl + "/copy";

    LOG.debug("url: {}.", url);

    try {
      HttpEntity<List<String>> response = restTemplate.exchange(url, POST, entity,
          new ParameterizedTypeReference<List<String>>() {
          });
      return Lists.newArrayList(response.getBody());
    } catch (RestClientException ex) {
      LOG.warn("Copy data definition failed.", ex);
      //todo add message or retry
      return new ArrayList<>();
    }
  }

  public void deleteDataDefinition(String developerId, String productId, String removeId) {
    LOG.debug("Enter. developerId: {}, productId: {}, removed dataDefinition: {}.",
        developerId, productId, removeId);

    HttpEntity entity = HttpEntityUtils.build(developerId);

    String url = UriComponentsBuilder.fromHttpUrl(definitionUrl + "/" + removeId)
        .queryParam("productId", productId).toUriString();

    try {
      restTemplate.exchange(url, DELETE, entity, Void.class);

    } catch (Exception ex) {
      LOG.debug("Something wrong when delete dataDefinition.", ex);
    }

  }

  public void deleteAllDataDefinition(String developerId, String productId) {
    LOG.debug("Enter. developerId: {}, productId: {}.", developerId, productId);

    HttpEntity entity = HttpEntityUtils.build(developerId);

    String url = UriComponentsBuilder.fromHttpUrl(definitionUrl)
        .queryParam("productId", productId).toUriString();

    try {
      restTemplate.exchange(url, DELETE, entity, Void.class);
    } catch (Exception ex) {
      LOG.debug("Something wrong when delete dataDefinition.", ex);
    }

    LOG.debug("Exit.");
  }

  public String createDataDefinition(String developerId, AddDataDefinition action) {
    LOG.debug("Enter.");

    HttpEntity entity = HttpEntityUtils.build(developerId, action);

    String newDataDefinitionId = null;

    try {

      ResponseEntity response =
          restTemplate.exchange(definitionUrl, POST, entity, Map.class);
      newDataDefinitionId = ((LinkedHashMap) response.getBody()).get("id").toString();
    } catch (Exception ex) {
      LOG.debug("Wrong when create dataDefinition.", ex);
      throw new ParametersException("Something wrong when create dataDefinition");
    }
    LOG.debug("Exit.");

    return newDataDefinitionId;
  }

  public Map<String, List<ProductDataView>> getProductData(String developerId,
      List<String> productIds) {

    LOG.debug("Enter. developerId: {}, productIds: {}.", developerId, productIds);

    HttpEntity entity = HttpEntityUtils.build(developerId);

    UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(definitionUrl)
        .queryParam("productIds", String.join(",", productIds));

    String url = builder.build().encode().toUriString();

    LOG.debug("data definition url: {}.", url);

    Map<String, List<ProductDataView>> result = Maps.newHashMap();

    try {

      ResponseEntity<Map<String, List<ProductDataView>>> responseEntity = restTemplate.exchange(url,
          GET, entity, new ParameterizedTypeReference<Map<String, List<ProductDataView>>>() {
          });

      result = responseEntity.getBody();
    } catch (Exception ex) {
      LOG.debug("Can not get data definition: {}", url, ex);
    }

    LOG.debug("Exit. productData size: {}.", result.size());

    return result;
  }

  public List<ProductDataView> getProductData(String developerId, String productId) {

    LOG.debug("Enter. developerId: {}, productId: {}.", developerId, productId);

    HttpEntity entity = HttpEntityUtils.build(developerId);

    UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(definitionUrl)
        .queryParam("productId", productId);

    String url = builder.build().encode().toUriString();
    List<ProductDataView> result = Lists.newArrayList();
    try {

      ResponseEntity<List<ProductDataView>> responseEntity = restTemplate.exchange(url, GET,
          entity, new ParameterizedTypeReference<List<ProductDataView>>() {
          });

      result = responseEntity.getBody();
    } catch (Exception ex) {
      LOG.debug("Can not get data definition by url: {}.", url, ex);
    }

    LOG.debug("Exit. productData size: {}.", result.size());

    return result;
  }

  public void updateDataDefinition(String dataDefinitionId, String developerId,
      UpdateRequest request) {
    LOG.debug("Enter. developerId: {}.", developerId);

    HttpEntity entity = HttpEntityUtils.build(developerId, request);

    String url = definitionUrl + "/" + dataDefinitionId;

    restTemplate.exchange(url, PUT, entity, Void.class);

    LOG.debug("Exit.");
  }

  public String createProductTypeData(AddProductTypeData action) {
    LOG.debug("Enter. action: {}.", action);

    LOG.debug("Enter.");

    HttpEntity entity = HttpEntityUtils.build(action);

    String newDataDefinitionId = null;

    try {

      ResponseEntity response =
          restTemplate.exchange(definitionUrl + "/platform", POST, entity, Map.class);
      newDataDefinitionId = ((LinkedHashMap) response.getBody()).get("id").toString();
    } catch (Exception ex) {
      LOG.debug("Wrong when create dataDefinition.", ex);
      throw new ParametersException("Something wrong when create dataDefinition");
    }
    LOG.debug("Exit.");

    return newDataDefinitionId;
  }

  public void deletePlatformData(String productTypeId) {
    LOG.debug("Enter. productTypeId: {}.", productTypeId);

    String url = UriComponentsBuilder.fromHttpUrl(definitionUrl + "/platform")
        .queryParam("productTypeId", productTypeId).toUriString();

    try {
      restTemplate.delete(url);
    } catch (Exception ex) {
      LOG.debug("Something wrong when delete dataDefinition.", ex);
    }

    LOG.debug("Exit.");
  }

  public void deletePlatformData(String productTypeId, String dataDefinitionId) {
    LOG.debug("Enter. productTypeId: {}, dataDefinitionId: {}.", productTypeId, dataDefinitionId);
    String url = UriComponentsBuilder.fromHttpUrl(definitionUrl + "/platform/" + dataDefinitionId)
        .queryParam("productTypeId", productTypeId).toUriString();
    try {
      restTemplate.delete(url);
    } catch (Exception ex) {
      LOG.debug("Something wrong when delete dataDefinition.", ex);
    }

    LOG.debug("Exit.");
  }

  public void updatePlatformData(String dataDefinitionId, UpdateRequest request) {
    LOG.debug("Enter. dataDefinitionId: {}.", dataDefinitionId);

    HttpEntity entity = HttpEntityUtils.build(request);

    String url = definitionUrl + "/platform/" + dataDefinitionId;

    restTemplate.exchange(url, PUT, entity, Void.class);

    LOG.debug("Exit.");
  }
}
