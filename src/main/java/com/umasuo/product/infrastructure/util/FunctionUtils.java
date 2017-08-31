package com.umasuo.product.infrastructure.util;

import com.umasuo.exception.NotExistException;
import com.umasuo.product.domain.model.ProductFunction;
import com.umasuo.product.infrastructure.enums.Category;
import com.umasuo.product.infrastructure.validator.CategoryValidator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * FunctionUtils class, used to filter function from list of function.
 */
public final class FunctionUtils {

  /**
   * Logger.
   */
  private static final Logger LOG = LoggerFactory.getLogger(FunctionUtils.class);

  /**
   * Private Constructor.
   */
  private FunctionUtils() {
  }

  /**
   * 根据id从产品的功能中挑选出对应的标准功能。
   *
   * @param productFunctions list of ProductFunction
   * @param id the id
   * @return ProductFunction
   */
  public static ProductFunction getStandardFunction(List<ProductFunction> productFunctions,
      String id) {
    LOG.debug("Enter. id: {}.", id);
    ProductFunction function = getFunction(productFunctions, id);

    CategoryValidator.checkCategory(Category.PLATFORM, function.getCategory());

    LOG.debug("Exit.");
    return function;
  }

  /**
   * 根据id从产品的功能中挑选出对应的自定义功能。
   *
   * @param productFunctions list of ProductFunction
   * @param id the id
   * @return ProductFunction
   */
  public static ProductFunction getProductFunction(List<ProductFunction> productFunctions,
      String id) {
    LOG.debug("Enter. id: {}.", id);
    ProductFunction function = getFunction(productFunctions, id);

    CategoryValidator.checkCategory(Category.PRODUCT, function.getCategory());

    LOG.debug("Exit.");
    return function;
  }

  /**
   * 根据id从产品的功能列表中挑选对应的功能。
   *
   * @param productFunctions list of ProductFunction
   * @param id the id
   * @return ProductFunction
   */
  private static ProductFunction getFunction(List<ProductFunction> productFunctions, String id) {
    LOG.debug("Enter. id: {}.", id);

    if (CollectionUtils.isEmpty(productFunctions)) {
      LOG.debug("Product do not have any functions.");
      throw new NotExistException("Function not exist");
    }

    ProductFunction function =
        productFunctions.stream().filter(func -> id.equals(func.getId())).findAny().orElse(null);

    if (function == null) {
      LOG.debug("Can not find function: {}.", id);
      throw new NotExistException("Function not exist");
    }
    LOG.debug("Exit.");

    return function;
  }
}
