package com.umasuo.device.definition.application.service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.umasuo.device.definition.application.dto.CommonDataView;
import com.umasuo.device.definition.application.dto.CopyRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.InvalidMediaTypeException;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

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
  @Value("${developer.service.uri:http://developer/}")
  private transient String developerUrl;

  /**
   * Data-definition service uri.
   */
  @Value("${datadefinition.service.uri:http://data-definition/}")
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
  public boolean isDeveloperExist(String developerId){
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
  public Map<String, Boolean> checkDefinitionExist(String developerId, List<String> definitionIds){
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
    } catch (InvalidMediaTypeException ex) {
      logger.debug("Something wrong when call api: ", ex);
    }

    logger.debug("Exit. result size: {}.", result.size());

    return result;
  }

  public List<String> copyDataDefinitions(String developerId, CopyRequest dataDefinitionIds) {
    logger.debug("Enter. developerId: {}, dataDefinitionIds: {}.", developerId, dataDefinitionIds);
    HttpHeaders headers = new HttpHeaders();
    headers.set("developerId", developerId);
    HttpEntity entity = new HttpEntity(dataDefinitionIds, headers);

    HttpEntity<String[]> response =
        restTemplate.exchange(definitionUrl + "/copy", HttpMethod.POST, entity, String[].class);

    return Lists.newArrayList(response.getBody());
  }
}
