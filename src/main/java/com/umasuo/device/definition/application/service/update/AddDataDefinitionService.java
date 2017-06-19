package com.umasuo.device.definition.application.service.update;

import com.umasuo.device.definition.application.dto.action.AddDataDefinition;
import com.umasuo.device.definition.application.service.DataDefinitionValidator;
import com.umasuo.device.definition.domain.model.Device;
import com.umasuo.device.definition.infrastructure.update.UpdateAction;
import com.umasuo.device.definition.infrastructure.update.UpdateActionUtils;
import com.umasuo.model.Updater;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
   * The DataDefinitionValidator.
   */
  @Autowired
  private transient DataDefinitionValidator dataDefinitionValidator;

  @Override
  public void handle(Device entity, UpdateAction action) {
    AddDataDefinition addDataDefinition = (AddDataDefinition) action;

    List<String> dataDefinitions = entity.getDataDefineIds();

    dataDefinitionValidator.checkDataDefinitionExist(entity.getDeveloperId(), dataDefinitions);

    List<String> newDataDefinitions = addDataDefinition.getDataDefinitionIds();
    newDataDefinitions.stream().forEach(
        dataDefinition -> {
          if (!dataDefinitions.contains(dataDefinition)) {
            dataDefinitions.add(dataDefinition);
          }
        }
    );

  }

}
