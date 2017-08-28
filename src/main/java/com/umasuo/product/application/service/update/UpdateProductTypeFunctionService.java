package com.umasuo.product.application.service.update;

import com.umasuo.exception.NotExistException;
import com.umasuo.model.Updater;
import com.umasuo.product.application.dto.action.UpdateProductTypeFunction;
import com.umasuo.product.domain.model.CommonFunction;
import com.umasuo.product.domain.model.ProductType;
import com.umasuo.product.infrastructure.update.UpdateAction;
import com.umasuo.product.infrastructure.update.UpdateActionUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Davis on 17/7/7.
 */
@Service(UpdateActionUtils.UPDATE_PRODUCT_TYPE_FUNCTION)
public class UpdateProductTypeFunctionService implements Updater<ProductType, UpdateAction> {

  /**
   * Logger.
   */
  private static final Logger LOG = LoggerFactory.getLogger(UpdateProductTypeFunctionService.class);

  @Override
  public void handle(ProductType productType, UpdateAction updateAction) {
    LOG.debug("Enter.");
    UpdateProductTypeFunction action = (UpdateProductTypeFunction) updateAction;

    CommonFunction function = getProductFunction(productType.getFunctions(), action.getId());

    mergeFunction(function, action);

    LOG.debug("Exit.");
  }

  private void mergeFunction(CommonFunction function, UpdateProductTypeFunction action) {
    function.setName(action.getName());
    function.setDescription(action.getDescription());
    function.setTransferType(action.getTransferType());
    function.setDataType(action.getDataType());
  }

  private CommonFunction getProductFunction(List<CommonFunction> functions, String id) {
    if (functions == null) {
      LOG.debug("Product type do not have any functions.");
      throw new NotExistException("Function not exist");
    }

    CommonFunction function =
        functions.stream().filter(f -> id.equals(f.getId())).findAny().orElse(null);

    if (function == null) {
      LOG.debug("Can not find function: {}.", id);
      throw new NotExistException("Function not exist");
    }

    return function;
  }
}
