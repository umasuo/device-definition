package com.umasuo.device.definition.application.service.update;

import com.google.common.collect.Lists;
import com.umasuo.device.definition.application.dto.action.AddDataDefinition;
import com.umasuo.device.definition.application.service.RestClient;
import com.umasuo.device.definition.domain.model.Device;
import com.umasuo.device.definition.infrastructure.update.UpdateAction;
import com.umasuo.device.definition.infrastructure.update.UpdateActionUtils;
import com.umasuo.exception.ParametersException;
import com.umasuo.model.Updater;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Consumer;

/**
 * Created by umasuo on 17/6/1.
 */
@Service(value = UpdateActionUtils.ADD_DATA_DEFINITION)
public class AddDataDefinitionService implements Updater<Device, UpdateAction> {

  /**
   * Logger.
   */
  private static final Logger LOG = LoggerFactory.getLogger(AddDataDefinitionService.class);

  /**
   * The Rest client.
   */
  @Autowired
  private transient RestClient restClient;

  @Override
  public void handle(Device entity, UpdateAction action) {
    AddDataDefinition addDataDefinition = (AddDataDefinition) action;

    List<String> dataDefinitions = entity.getDataDefineIds();

    checkDataDefinitionExist(entity.getDeveloperId(), dataDefinitions);

    List<String> newDataDefinitions = addDataDefinition.getDataDefinitionIds();
    newDataDefinitions.stream().forEach(
        dataDefinition -> {
          if (!dataDefinitions.contains(dataDefinition)) {
            dataDefinitions.add(dataDefinition);
          }
        }
    );

  }

  /**
   * Check if DataDefinition exist or belong to developer.
   *
   * @param developerId the Developer id
   * @param dataDefinitions the DataDefinition id
   */
  private void checkDataDefinitionExist(String developerId, List<String> dataDefinitions) {
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
