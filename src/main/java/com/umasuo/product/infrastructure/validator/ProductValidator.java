package com.umasuo.product.infrastructure.validator;

import com.umasuo.exception.ConflictException;
import com.umasuo.exception.NotExistException;
import com.umasuo.exception.ParametersException;
import com.umasuo.product.application.dto.ProductDraft;
import com.umasuo.product.domain.model.CommonFunction;
import com.umasuo.product.domain.model.Product;
import com.umasuo.product.domain.model.ProductType;
import com.umasuo.product.infrastructure.enums.ProductStatus;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Davis on 17/6/29.
 */
public final class ProductValidator {

  /**
   * Logger.
   */
  private static final Logger logger = LoggerFactory.getLogger(ProductValidator.class);

  /**
   * Instantiates a new Product validator.
   */
  private ProductValidator() {
  }

  public static void validateProductType(ProductDraft draft, ProductType productType) {
    String productTypeId = draft.getProductTypeId();

    if (productType == null) {
      logger.debug("Can not find productType: {}.", productTypeId);
      throw new NotExistException("ProductType is not exist");
    }

  }

  public static void validateFunction(List<String> functionIds, ProductType productType) {
    List<String> productTypeFunctionIds =
        productType.getFunctions().stream().map(CommonFunction::getFunctionId)
            .collect(Collectors.toList());

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


  public static void checkStatus(Product product) {
    if (product.getStatus().equals(ProductStatus.PUBLISHED) ||
        product.getStatus().equals(ProductStatus.CHECKING)) {
      logger.debug("Can not delete or update a published product in status: {}.",
          product.getStatus());
      throw new ParametersException(
          "Can not delete or update a published product when it is checking or published");
    }
  }

  public static void checkDeveloper(String developerId, Product product) {
    if (!product.getDeveloperId().equals(developerId)) {
      logger.debug("Product: {} not belong to developer: {}.", product.getId(), developerId);
      throw new ParametersException("The Product not belong to the developer: " + developerId + "," +
          " productId: " + product.getId());
    }
  }

  /**
   * check the version.
   *
   * @param inputVersion Integer
   * @param existVersion Integer
   */
  public static void checkVersion(Integer inputVersion, Integer existVersion) {
    if (!inputVersion.equals(existVersion)) {
      logger.debug("Product definition version is not correct.");
      throw new ConflictException("Product definition version is not correct.");
    }
  }

  public static void validateStatus(ProductStatus requestStatus, ProductStatus realStatus) {
    logger.debug("Enter. requestStatus: {}, realStatus: {}.", requestStatus, realStatus);

    if (!requestStatus.equals(realStatus)) {
      logger.debug("Product status not match, requestStatus: {}, realStatus: {}.",
          requestStatus, realStatus);
      throw new ParametersException("Product status is wrong");
    }

    logger.debug("Exit.");
  }
}
