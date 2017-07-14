package com.umasuo.device.definition.application.service.update;

import com.umasuo.device.definition.application.dto.action.CopyFunction;
import com.umasuo.device.definition.application.dto.mapper.CommonFunctionMapper;
import com.umasuo.device.definition.domain.model.CommonFunction;
import com.umasuo.device.definition.domain.model.Product;
import com.umasuo.device.definition.domain.model.DeviceFunction;
import com.umasuo.device.definition.domain.model.ProductType;
import com.umasuo.device.definition.domain.service.ProductTypeService;
import com.umasuo.device.definition.infrastructure.update.UpdateAction;
import com.umasuo.device.definition.infrastructure.update.UpdateActionUtils;
import com.umasuo.exception.AlreadyExistException;
import com.umasuo.exception.ParametersException;
import com.umasuo.model.Updater;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Created by Davis on 17/7/4.
 */
@Service(UpdateActionUtils.COPY_FUNCTION)
public class CopyFunctionService implements Updater<Product, UpdateAction>{

  /**
   * Logger.
   */
  private static final Logger LOG = LoggerFactory.getLogger(CopyFunctionService.class);

  @Autowired
  private transient ProductTypeService productTypeService;


  @Override
  public void handle(Product device, UpdateAction updateAction) {
    LOG.debug("Enter. device: {}, updateAction: {}.", device, updateAction);

    List<String> functionIds = ((CopyFunction) updateAction).getFunctionIds();

    ProductType productType = productTypeService.getById(device.getProductType());

    List<CommonFunction> commonFunctions = getFunctions(productType, functionIds);

    checkExistFunction(commonFunctions, device);

    List<DeviceFunction> functions = CommonFunctionMapper.copy(commonFunctions);

    device.getDeviceFunctions().addAll(functions);

    LOG.debug("Exit.");
  }

  private void checkExistFunction(List<CommonFunction> commonFunctions, Product device) {
    List<String> functionIds = commonFunctions.stream().map(
        function -> function.getFunctionId()
    ).collect(Collectors.toList());

    Predicate<DeviceFunction> predicate =
        function -> functionIds.contains(function.getFunctionId());
    boolean existFunctionId = device.getDeviceFunctions().stream().anyMatch(predicate);
    if (existFunctionId) {
      LOG.debug("Function has been exist.");
      throw new AlreadyExistException("Function has bean exist");
    }
  }

  private List<CommonFunction> getFunctions(ProductType productType, List<String> functionIds) {
    List<String> existFunctionIds = productType.getFunctions().stream().map(
        function -> function.getId()).collect(Collectors.toList());

    if (!existFunctionIds.containsAll(functionIds)) {
      LOG.debug("Function: {} is not all in productType: {}.", functionIds, productType.getId());
      throw new ParametersException("FunctionIds is not all in productType");
    }

    List<CommonFunction> result = productType.getFunctions().stream()
        .filter(function -> functionIds.contains(function.getId()))
        .collect(Collectors.toList());

    return result;
  }
}
