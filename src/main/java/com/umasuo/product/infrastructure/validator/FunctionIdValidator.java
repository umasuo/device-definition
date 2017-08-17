package com.umasuo.product.infrastructure.validator;

import com.umasuo.exception.AlreadyExistException;
import com.umasuo.product.application.dto.ProductTypeView;
import com.umasuo.product.domain.model.ProductFunction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * Created by Davis on 17/8/15.
 */
public final class FunctionIdValidator {

  /**
   * Logger.
   */
  private static final Logger LOG = LoggerFactory.getLogger(FunctionIdValidator.class);

  /**
   * Instantiates a new Data id validator.
   */
  private FunctionIdValidator() {
  }

  public static void checkForAdd(String functionId, ProductTypeView productType,
      List<ProductFunction> functionViews) {
    boolean sameAsPlatformData = existPlatformFunctionId(functionId, productType);

    boolean existDataId =
        functionViews.stream().anyMatch(function -> functionId.equals(function.getFunctionId()));

    existFunctionId(functionId, sameAsPlatformData, existDataId);
  }

  public static void checkForUpdate(String productFunctionId, String functionId,
      ProductTypeView productType, List<ProductFunction> productFunctions) {
    boolean sameAsPlatformData = existPlatformFunctionId(functionId, productType);

    boolean existDataId = productFunctions.stream().anyMatch(
        function -> functionId.equals(function.getFunctionId()) &&
            !productFunctionId.equals(function.getId()));

    existFunctionId(functionId, sameAsPlatformData, existDataId);
  }

  private static boolean existPlatformFunctionId(String functionId, ProductTypeView productType) {
    boolean sameAsPlatformData = false;
    if (!CollectionUtils.isEmpty(productType.getFunctions())) {
      sameAsPlatformData = productType.getFunctions().stream()
          .anyMatch(function -> functionId.equals(function.getFunctionId()));
    }
    return sameAsPlatformData;
  }

  private static void existFunctionId(String functionId, boolean sameAsPlatformFunction,
      boolean existFunctionId) {
    if (sameAsPlatformFunction || existFunctionId) {
      LOG.debug("Can not add exist functionId: {}.", functionId);
      throw new AlreadyExistException("FunctionId already exist");
    }
  }
}
