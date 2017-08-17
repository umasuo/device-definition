package com.umasuo.product.infrastructure.util;

import com.umasuo.exception.NotExistException;
import com.umasuo.product.domain.model.ProductFunction;
import com.umasuo.product.infrastructure.enums.Category;
import com.umasuo.product.infrastructure.validator.CategoryValidator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by Davis on 17/8/17.
 */
public final class FunctionUtils {

  /**
   * Logger.
   */
  private static final Logger LOG = LoggerFactory.getLogger(FunctionUtils.class);

  /**
   * Instantiates a new Function utils.
   */
  public FunctionUtils() {
  }

  public static ProductFunction getStandardFunction(List<ProductFunction> productFunctions,
      String id) {
    LOG.debug("Enter.");
    ProductFunction function = getFunction(productFunctions, id);

    CategoryValidator.checkCategory(Category.PLATFORM, function.getCategory());

    LOG.debug("Exit.");
    return function;
  }

  public static ProductFunction getProductFunction(List<ProductFunction> productFunctions,
      String id) {
    LOG.debug("Enter.");
    ProductFunction function = getFunction(productFunctions, id);

    CategoryValidator.checkCategory(Category.PRODUCT, function.getCategory());

    LOG.debug("Exit.");
    return function;
  }

  private static ProductFunction getFunction(List<ProductFunction> productFunctions, String id) {
    if (productFunctions == null) {
      LOG.debug("Product do not have any functions.");
      throw new NotExistException("Function not exist");
    }

    ProductFunction function =
        productFunctions.stream().filter(f -> id.equals(f.getId())).findAny().orElse(null);

    if (function == null) {
      LOG.debug("Can not find function: {}.", id);
      throw new NotExistException("Function not exist");
    }
    return function;
  }
}
