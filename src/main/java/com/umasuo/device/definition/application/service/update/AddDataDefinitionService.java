package com.umasuo.device.definition.application.service.update;

import com.umasuo.device.definition.application.dto.action.AddDataDefinition;
import com.umasuo.device.definition.domain.model.Device;
import com.umasuo.device.definition.infrastructure.update.UpdateAction;
import com.umasuo.device.definition.infrastructure.update.UpdateActionUtils;
import com.umasuo.model.Updater;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by umasuo on 17/6/1.
 */
@Service(value = UpdateActionUtils.ADD_DATA_DEFINITION)
public class AddDataDefinitionService implements Updater<Device, UpdateAction> {

  @Override
  public void handle(Device entity, UpdateAction action) {
    AddDataDefinition addDataDefinition = (AddDataDefinition) action;
    //TODO 检查data definition是否存在，且是否属于该开发者
    List<String> dataDefinitions = entity.getDataDefineIds();
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
