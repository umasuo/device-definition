package com.umasuo.product.application.service.update;

import com.umasuo.exception.NotExistException;
import com.umasuo.model.Updater;
import com.umasuo.product.application.dto.ProductTypeView;
import com.umasuo.product.application.dto.action.UpdateFunction;
import com.umasuo.product.application.dto.mapper.ProductFunctionMapper;
import com.umasuo.product.application.service.ProductTypeApplication;
import com.umasuo.product.domain.model.Product;
import com.umasuo.product.domain.model.ProductFunction;
import com.umasuo.product.infrastructure.enums.Category;
import com.umasuo.product.infrastructure.update.UpdateAction;
import com.umasuo.product.infrastructure.update.UpdateActionUtils;
import com.umasuo.product.infrastructure.util.FunctionUtils;
import com.umasuo.product.infrastructure.validator.CategoryValidator;
import com.umasuo.product.infrastructure.validator.FunctionIdValidator;

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
    ProductFunction function =
        FunctionUtils.getProductFunction(product.getProductFunctions(), action.getId());

    ProductFunctionMapper.merge(function, action);
    LOG.debug("Exit.");
  }

  private void checkFunctionId(String id, String functionId, Product product) {
    LOG.debug("Enter.");
    ProductTypeView productType = productTypeApplication.get(product.getProductType());

    FunctionIdValidator.checkForUpdate(id, functionId, productType, product.getProductFunctions());

    LOG.debug("Exit.");
  }
}
