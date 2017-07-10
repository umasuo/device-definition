package com.umasuo.device.definition.application.service.update;

import com.umasuo.device.definition.application.dto.action.UpdateProduct;
import com.umasuo.device.definition.domain.model.Device;
import com.umasuo.device.definition.infrastructure.update.UpdateAction;
import com.umasuo.device.definition.infrastructure.update.UpdateActionUtils;
import com.umasuo.model.Updater;

import org.springframework.stereotype.Service;

/**
 * Created by umasuo on 17/6/1.
 */
@Service(value = UpdateActionUtils.UPDATE_PRODUCT)
public class UpdateDeviceService implements Updater<Device, UpdateAction> {

  @Override
  public void handle(Device entity, UpdateAction updateAction) {
    UpdateProduct action = (UpdateProduct) updateAction;
    entity.setName(action.getName());
    entity.setIcon(action.getIcon());
    entity.setCommunicationType(action.getType());
    entity.setOpenable(action.getOpenable());
    entity.setDescription(action.getDescription());
    entity.setWifiModule(action.getWifiModule());
    entity.setModel(action.getModel());
    entity.setFirmwareVersion(action.getFirmwareVersion());
  }

}
