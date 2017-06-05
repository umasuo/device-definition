package com.umasuo.device.definition.application.service.update;

import com.umasuo.device.definition.application.dto.action.UpdateDevice;
import com.umasuo.device.definition.domain.model.Device;
import com.umasuo.device.definition.infrastructure.update.UpdateAction;
import com.umasuo.device.definition.infrastructure.update.UpdateActionUtils;
import com.umasuo.model.Updater;
import org.springframework.stereotype.Service;

/**
 * Created by umasuo on 17/6/1.
 */
@Service(value = UpdateActionUtils.UPDATE_DATA_DEFINITION)
public class UpdateDeviceService implements Updater<Device, UpdateAction> {

  @Override
  public void handle(Device entity, UpdateAction action) {
    UpdateDevice updateDevice = (UpdateDevice) action;
    entity.setName(updateDevice.getName());
    entity.setIcon(updateDevice.getIcon());
    entity.setType(updateDevice.getType());
  }

}
