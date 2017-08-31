package com.umasuo.product.infrastructure.validator;

import com.umasuo.exception.AlreadyExistException;
import com.umasuo.product.application.dto.ProductTypeView;
import com.umasuo.product.domain.model.ProductFunction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * 用于判断FunctionId是否合法.
 */
public final class FunctionIdValidator {

  /**
   * Logger.
   */
  private static final Logger LOG = LoggerFactory.getLogger(FunctionIdValidator.class);

  /**
   * Private constructor.
   */
  private FunctionIdValidator() {
  }

  /**
   * 在产品添加数据功能的时候使用，判断要添加的functionId是否合法：对应的ProductType没有该functionId，已存在的function没有该id。
   *
   * @param functionId the function id
   * @param productType the product type
   * @param functionViews the function views
   */
  public static void checkForAdd(String functionId, ProductTypeView productType,
      List<ProductFunction> functionViews) {
    LOG.debug("Enter.");
    boolean sameAsPlatformData = existPlatformFunctionId(functionId, productType);

    boolean existDataId =
        functionViews.stream().anyMatch(function -> functionId.equals(function.getFunctionId()));

    existFunctionId(functionId, sameAsPlatformData, existDataId);
    LOG.debug("Exit.");
  }

  /**
   * 在产品更新数据功能的时候使用，判断要添加的functionId是否合法：对应的ProductType没有该functionId，已存在的其他function没有该id。
   *
   * @param productFunctionId the product function id
   * @param functionId the function id
   * @param productType the product type
   * @param productFunctions the product functions
   */
  public static void checkForUpdate(String productFunctionId, String functionId,
      ProductTypeView productType, List<ProductFunction> productFunctions) {
    LOG.debug("Enter.");
    boolean sameAsPlatformData = existPlatformFunctionId(functionId, productType);

    boolean existDataId = productFunctions.stream().anyMatch(
        function -> functionId.equals(function.getFunctionId()) &&
            !productFunctionId.equals(function.getId()));

    existFunctionId(functionId, sameAsPlatformData, existDataId);
    LOG.debug("Exit.");
  }

  /**
   * Exist platform function id boolean.
   *
   * @param functionId the function id
   * @param productType the product type
   * @return the boolean
   */
  public static boolean existPlatformFunctionId(String functionId, ProductTypeView productType) {
    LOG.debug("Enter.");

    boolean sameAsPlatformData = false;

    if (!CollectionUtils.isEmpty(productType.getFunctions())) {
      sameAsPlatformData = productType.getFunctions().stream()
          .anyMatch(function -> functionId.equals(function.getFunctionId()));
    }

    LOG.debug("Exit. result: {}.", sameAsPlatformData);

    return sameAsPlatformData;
  }

  /**
   * 根据ProductType的functionId情况和已存在的Function的情况，判断functionId是否合法。
   *
   * @param functionId the dataId
   * @param sameAsPlatformFunction ProductType的functionId情况
   * @param existFunctionId 已存在的Function的情况
   */
  private static void existFunctionId(String functionId, boolean sameAsPlatformFunction,
      boolean existFunctionId) {
    if (sameAsPlatformFunction || existFunctionId) {
      LOG.debug("Can not add or update exist functionId: {}.", functionId);
      throw new AlreadyExistException("FunctionId already exist");
    }
  }
}
