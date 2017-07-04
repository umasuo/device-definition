package com.umasuo.device.definition.application.service.update;

import com.umasuo.device.definition.application.dto.action.RemoveFunction;
import com.umasuo.device.definition.domain.model.Device;
import com.umasuo.device.definition.infrastructure.update.UpdateAction;
import com.umasuo.device.definition.infrastructure.update.UpdateActionUtils;
import com.umasuo.exception.ParametersException;
import com.umasuo.model.Updater;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Davis on 17/7/4.
 */
@Service(UpdateActionUtils.REMOVE_FUNCTION)
public class RemoveFunctionService implements Updater<Device, UpdateAction> {

  /**
   * Logger.
   */
  private static final Logger LOG = LoggerFactory.getLogger(RemoveFunctionService.class);

  @Override
  public void handle(Device device, UpdateAction updateAction) {
    LOG.debug("Enter. device: {}, updateAction: {}.", device, updateAction);

    RemoveFunction action = (RemoveFunction) updateAction;
    List<String> functionIds = action.getFunctionIds();

    checkFunctionId(device, functionIds);
    device.getDeviceFunctions().removeIf(function -> functionIds.contains(function.getId()));

    LOG.debug("Exit.");
  }

  private void checkFunctionId(Device device, List<String> functionIds) {
    LOG.debug("Enter.");

    List<String> existFunctionIds = device.getDeviceFunctions().stream()
        .map(function -> function.getId())
        .collect(Collectors.toList());

    if (!existFunctionIds.containsAll(functionIds)) {
      LOG.debug("Function: {} is not all in product: {}.", functionIds, device.getId());
      throw new ParametersException("FunctionIds is not all in product");
    }

    LOG.debug("Exit.");
  }
}
