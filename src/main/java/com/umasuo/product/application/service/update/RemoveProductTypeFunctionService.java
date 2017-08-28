package com.umasuo.product.application.service.update;

import com.umasuo.exception.ParametersException;
import com.umasuo.model.Updater;
import com.umasuo.product.application.dto.action.RemoveProductTypeFunction;
import com.umasuo.product.domain.model.ProductType;
import com.umasuo.product.infrastructure.update.UpdateAction;
import com.umasuo.product.infrastructure.update.UpdateActionUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Davis on 17/7/4.
 */
@Service(UpdateActionUtils.REMOVE_PRODUCT_TYPE_FUNCTION)
public class RemoveProductTypeFunctionService implements Updater<ProductType, UpdateAction> {

  /**
   * Logger.
   */
  private static final Logger LOG = LoggerFactory.getLogger(RemoveProductTypeFunctionService.class);

  @Override
  public void handle(ProductType productType, UpdateAction updateAction) {
    LOG.debug("Enter.", productType, updateAction);

    RemoveProductTypeFunction action = (RemoveProductTypeFunction) updateAction;
    List<String> functionIds = action.getFunctionIds();

    checkFunctionId(productType, functionIds);
    productType.getFunctions().removeIf(function -> functionIds.contains(function.getId()));

    LOG.debug("Exit.");
  }

  private void checkFunctionId(ProductType productType, List<String> functionIds) {
    LOG.debug("Enter.");

    List<String> existFunctionIds = productType.getFunctions().stream()
        .map(function -> function.getId())
        .collect(Collectors.toList());

    if (!existFunctionIds.containsAll(functionIds)) {
      LOG.debug("Function: {} is not all in product: {}.", functionIds, productType.getId());
      throw new ParametersException("FunctionIds is not all in product");
    }

    LOG.debug("Exit.");
  }
}
