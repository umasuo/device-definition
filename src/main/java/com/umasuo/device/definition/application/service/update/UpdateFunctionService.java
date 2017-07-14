package com.umasuo.device.definition.application.service.update;

import com.umasuo.device.definition.application.dto.ProductTypeView;
import com.umasuo.device.definition.application.dto.action.UpdateFunction;
import com.umasuo.device.definition.application.dto.mapper.DeviceFunctionMapper;
import com.umasuo.device.definition.application.service.ProductQueryApplication;
import com.umasuo.device.definition.application.service.ProductTypeApplication;
import com.umasuo.device.definition.domain.model.Product;
import com.umasuo.device.definition.domain.model.DeviceFunction;
import com.umasuo.device.definition.domain.model.ProductType;
import com.umasuo.device.definition.infrastructure.update.UpdateAction;
import com.umasuo.device.definition.infrastructure.update.UpdateActionUtils;
import com.umasuo.exception.AlreadyExistException;
import com.umasuo.exception.NotExistException;
import com.umasuo.model.Updater;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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


  @Autowired
  private transient ProductTypeApplication productTypeApplication;


  @Override
  public void handle(Product product, UpdateAction updateAction) {
    LOG.debug("Enter.");
    UpdateFunction action = (UpdateFunction) updateAction;

    checkFunctionId(action.getId(), action.getFunctionId(), product);
    DeviceFunction function = getFunction(product.getDeviceFunctions(), action.getId());

    DeviceFunctionMapper.merge(function, action);
    LOG.debug("Exit.");
  }

  private DeviceFunction getFunction(List<DeviceFunction> deviceFunctions, String id) {
    LOG.debug("Enter.");
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

    LOG.debug("Exit.");
    return function;
  }

  private void checkFunctionId(String id, String functionId, Product product) {
    LOG.debug("Enter.");

    ProductTypeView productType = productTypeApplication.get(product.getProductType());

    boolean sameAsPlatformFunction =
        productType.getFunctions().stream()
            .anyMatch(function -> functionId.equals(function.getFunctionId()));

    boolean existFunctionId = product.getDeviceFunctions().stream().anyMatch(
        function -> functionId.equals(function.getFunctionId()) && !id.equals(function.getId()));

    if (sameAsPlatformFunction || existFunctionId) {
      LOG.debug("FunctionId: {} is already exist.", functionId);
      throw new AlreadyExistException("FunctionId already exist");
    }

    LOG.debug("Exit.");
  }
}
