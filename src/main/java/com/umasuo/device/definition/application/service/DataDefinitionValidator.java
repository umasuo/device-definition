package com.umasuo.device.definition.application.service;

import com.google.common.collect.Lists;
import com.umasuo.exception.ParametersException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Consumer;

/**
 * Created by Davis on 17/6/19.
 */
@Service
public class DataDefinitionValidator {

  /**
   * Logger.
   */
  private static final Logger LOG = LoggerFactory.getLogger(DataDefinitionValidator.class);

  /**
   * The Rest client.
   */
  @Autowired
  private transient RestClient restClient;

  /**
   * Check if DataDefinition exist or belong to developer.
   *
   * @param developerId the Developer id
   * @param dataDefinitions the DataDefinition id
   */
  public void checkDataDefinitionExist(String developerId, List<String> dataDefinitions) {
    Map<String, Boolean> existResult =
        restClient.checkDefinitionExist(developerId, dataDefinitions);

    List<String> notExistDefinitions = Lists.newArrayList();

    Consumer<Entry<String, Boolean>> consumer = entry -> {
      if (entry.getValue().equals(false)) {
        notExistDefinitions.add(entry.getKey());
      }
    };

    existResult.entrySet().stream().forEach(consumer);

    if (!notExistDefinitions.isEmpty()) {
      LOG.debug("DataDefinitions: {} not exist or not belong to developer: {}.",
          notExistDefinitions, developerId);
      throw new ParametersException("DataDefinitions not exist: " + notExistDefinitions.toString());
    }
  }
}
