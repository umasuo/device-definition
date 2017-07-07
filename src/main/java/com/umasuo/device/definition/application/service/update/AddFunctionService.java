package com.umasuo.device.definition.application.service.update;

import com.umasuo.device.definition.application.dto.action.AddFunction;
import com.umasuo.device.definition.application.dto.mapper.DeviceFunctionMapper;
import com.umasuo.device.definition.domain.model.Device;
import com.umasuo.device.definition.domain.model.DeviceFunction;
import com.umasuo.device.definition.domain.model.ProductType;
import com.umasuo.device.definition.domain.service.ProductTypeService;
import com.umasuo.device.definition.infrastructure.update.UpdateAction;
import com.umasuo.device.definition.infrastructure.update.UpdateActionUtils;
import com.umasuo.exception.ConflictException;
import com.umasuo.model.Updater;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Davis on 17/7/4.
 */
@Service(UpdateActionUtils.ADD_FUNCTION)
public class AddFunctionService implements Updater<Device, UpdateAction> {

  /**
   * Logger.
   */
  private static final Logger LOG = LoggerFactory.getLogger(AddFunctionService.class);

  @Autowired
  private transient ProductTypeService productTypeService;

  @Override
  public void handle(Device device, UpdateAction updateAction) {
    LOG.debug("Enter. device: {}, updateAction: {}.", device, updateAction);

    AddFunction action = (AddFunction) updateAction;

    checkFunctionId(device, action.getFunctionId());

    DeviceFunction deviceFunction = DeviceFunctionMapper.build(action);

    device.getDeviceFunctions().add(deviceFunction);

    LOG.debug("Exit.");
  }

  private void checkFunctionId(Device device, String functionId) {
    LOG.debug("Enter.");

    boolean existFunctionId = device.getDeviceFunctions().stream()
        .anyMatch(function -> function.getFunctionId().equals(functionId));

    if (existFunctionId) {
      LOG.debug("Can not add function: {} in this product: {}.",
          functionId, device.getId());
      throw new ConflictException("FunctionId is exist");
    }

    ProductType productType = productTypeService.getById(device.getProductType());

    existFunctionId =
        productType.getFunctions().stream().anyMatch(p -> functionId.equals(p.getFunctionId()));

    if (existFunctionId) {
      LOG.debug("Can not add function: {} in this product: {} belongs to productType: {}.",
          functionId, device.getId(), device.getProductType());
      throw new ConflictException("FunctionId is exist");
    }

    LOG.debug("Exit.");
  }
}
