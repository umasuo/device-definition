package com.umasuo.device.definition.infrastructure.validator;

import com.umasuo.device.definition.application.dto.DeviceDraft;
import com.umasuo.device.definition.domain.model.CommonFunction;
import com.umasuo.device.definition.domain.model.ProductType;
import com.umasuo.exception.NotExistException;
import com.umasuo.exception.ParametersException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Davis on 17/6/29.
 */
public final class DeviceValidator {

  /**
   * Logger.
   */
  private static final Logger logger = LoggerFactory.getLogger(DeviceValidator.class);

  /**
   * Instantiates a new Device validator.
   */
  private DeviceValidator() {
  }

  public static void validateProductType(DeviceDraft draft, ProductType productType) {
    String productTypeId = draft.getProductTypeId();

    if (productType == null) {
      logger.debug("Can not find productType: {}.", productTypeId);
      throw new NotExistException("ProductType is not exist");
    }

  }

  public static void validateFunction(List<String> functionIds, ProductType productType) {
    List<String> productTypeFunctionIds =
        productType.getFunctions().stream().map(CommonFunction::getFunctionId).collect(Collectors.toList());

    if (!productTypeFunctionIds.containsAll(functionIds)) {
      logger.debug("Should use function defined in this product type: {}.", productType.getId());
      throw new ParametersException("Can not use function not defined in this product type");
    }
  }

  public static void validateDataDefinition(List<String> dataDefineIds, ProductType productType) {
    if (!productType.getDataIds().containsAll(dataDefineIds)) {
      logger.debug("Should use data defined in this product type: {}.", productType);
      throw new ParametersException("Can not use data not defined in this product type");
    }
  }
}
