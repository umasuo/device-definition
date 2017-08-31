package com.umasuo.product.infrastructure.validator;

import com.umasuo.exception.ParametersException;
import com.umasuo.product.infrastructure.enums.Category;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 用于判断Category是否符合要求.
 */
public final class CategoryValidator {

  /**
   * Logger.
   */
  private static final Logger LOG = LoggerFactory.getLogger(CategoryValidator.class);

  /**
   * Private constructor.
   */
  private CategoryValidator() {
  }

  /**
   * 判断Category是否一致。
   *
   * @param requestCategory the requestCategory
   * @param realCategory the realCategory
   */
  public static void checkCategory(Category requestCategory, Category realCategory) {
    LOG.debug("Enter. requestCategory: {}, realCategory: {}.", requestCategory, realCategory);

    if (!requestCategory.equals(realCategory)) {
      LOG.debug("Category not match.");
      throw new ParametersException("Can not update on wrong category");
    }

    LOG.debug("Exit.");
  }
}
