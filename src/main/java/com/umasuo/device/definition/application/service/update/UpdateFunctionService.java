package com.umasuo.device.definition.application.service.update;

import com.umasuo.device.definition.application.dto.action.UpdateFunction;
import com.umasuo.device.definition.application.dto.mapper.DeviceFunctionMapper;
import com.umasuo.device.definition.domain.model.Product;
import com.umasuo.device.definition.domain.model.DeviceFunction;
import com.umasuo.device.definition.infrastructure.update.UpdateAction;
import com.umasuo.device.definition.infrastructure.update.UpdateActionUtils;
import com.umasuo.exception.NotExistException;
import com.umasuo.model.Updater;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Davis on 17/7/7.
 */
@Service(UpdateActionUtils.UPDATE_FUNCTION)
public class UpdateFunctionService implements Updater<Product, UpdateAction> {

  /**
   * Logger.
   */
  private static final Logger LOG = LoggerFactory.getLogger(UpdateFunctionService.class);

  @Override
  public void handle(Product device, UpdateAction updateAction) {
    UpdateFunction action = (UpdateFunction) updateAction;

    DeviceFunction function = getFunction(device.getDeviceFunctions(), action.getId());

    DeviceFunctionMapper.merge(function, action);
  }

  private DeviceFunction getFunction(List<DeviceFunction> deviceFunctions, String id) {
    if (deviceFunctions == null) {
      LOG.debug("Product do not have any functions.");
      throw new NotExistException("Function not exist");
    }

    DeviceFunction function =
        deviceFunctions.stream().filter(f -> id.equals(f.getId())).findAny().orElse(null);

    if (function == null) {
      LOG.debug("Can not find function: {}.", id);
      throw new NotExistException("Function not exist");
    }

    return function;
  }
}
