package com.umasuo.device.definition.application.service.update;

import com.umasuo.device.definition.application.dto.action.UpdateDataDefinition;
import com.umasuo.device.definition.domain.model.Device;
import com.umasuo.device.definition.infrastructure.update.UpdateAction;
import com.umasuo.device.definition.infrastructure.update.UpdateActionUtils;
import com.umasuo.model.Updater;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import sun.rmi.runtime.Log;

/**
 * Created by Davis on 17/7/12.
 */
@Service(UpdateActionUtils.UPDATE_DATA_DEFINITION)
public class UpdateDataService implements Updater<Device, UpdateAction>{

  /**
   * Logger.
   */
  private static final Logger LOG = LoggerFactory.getLogger(UpdateDataService.class);


  @Override
  public void handle(Device device, UpdateAction updateAction) {
    UpdateDataDefinition action = (UpdateDataDefinition) updateAction;
    String dataDefinitionId = action.getDataDefinitionId();

    if (device.getDataDefineIds() == null ||
        !device.getDataDefineIds().contains(dataDefinitionId)) {

    }
  }
}
