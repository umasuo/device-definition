package com.umasuo.device.definition.application.service.update;

import com.umasuo.device.definition.application.dto.action.AddDataDefinition;
import com.umasuo.device.definition.application.service.RestClient;
import com.umasuo.device.definition.domain.model.Device;
import com.umasuo.device.definition.infrastructure.update.UpdateAction;
import com.umasuo.device.definition.infrastructure.update.UpdateActionUtils;
import com.umasuo.model.Updater;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Davis on 17/7/7.
 */
@Service(UpdateActionUtils.ADD_DATA_DEFINITION)
public class AddDataDefinitionService implements Updater<Device, UpdateAction>{

  /**
   * Logger.
   */
  private static final Logger LOG = LoggerFactory.getLogger(AddDataDefinitionService.class);

  @Autowired
  private transient RestClient restClient;

  @Override
  public void handle(Device device, UpdateAction updateAction) {
    AddDataDefinition action = (AddDataDefinition) updateAction;

    action.setProductId(device.getId());

    String dataDefinitionId = restClient.createDataDefinition(device.getDeveloperId(), action);

    device.getDataDefineIds().add(dataDefinitionId);
  }
}
