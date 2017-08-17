package com.umasuo.product.infrastructure.validator;

import com.umasuo.exception.ParametersException;
import com.umasuo.product.infrastructure.enums.Category;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Davis on 17/8/17.
 */
public final class CategoryValidator {

  /**
   * Logger.
   */
  private static final Logger LOG = LoggerFactory.getLogger(CategoryValidator.class);

  /**
   * Instantiates a new Category validator.
   */
  public CategoryValidator() {
  }

  public static void checkCategory(Category requestCategory, Category realCategory) {
    LOG.debug("Enter. requestCategory: {}, realCategory: {}.", requestCategory, realCategory);

    if (!requestCategory.equals(realCategory)) {
      LOG.debug("Category not match.");
      throw new ParametersException("Can not update on wrong category");
    }

    LOG.debug("Exit.");
  }
}
