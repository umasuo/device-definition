package com.umasuo.product.application.service.update;

import com.umasuo.exception.ParametersException;
import com.umasuo.model.Updater;
import com.umasuo.product.application.dto.action.RemoveFunction;
import com.umasuo.product.domain.model.Product;
import com.umasuo.product.infrastructure.update.UpdateAction;
import com.umasuo.product.infrastructure.update.UpdateActionUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 从产品中移除功能的service.
 */
@Service(UpdateActionUtils.REMOVE_FUNCTION)
public class RemoveFunctionService implements Updater<Product, UpdateAction> {

  /**
   * Logger.
   */
  private static final Logger LOG = LoggerFactory.getLogger(RemoveFunctionService.class);

  /**
   * 执行update的方法。
   *
   * @param product the Product
   * @param updateAction the RemoveFunction
   */
  @Override
  public void handle(Product product, UpdateAction updateAction) {
    LOG.debug("Enter. product: {}, updateAction: {}.", product, updateAction);

    RemoveFunction action = (RemoveFunction) updateAction;
    List<String> functionIds = action.getFunctionIds();

    checkFunctionId(product, functionIds);
    product.getProductFunctions().removeIf(function -> functionIds.contains(function.getId()));

    LOG.debug("Exit.");
  }

  /**
   * 判断functionIds是否存在。
   *
   * @param product the Product
   * @param functionIds list build function id
   */
  private void checkFunctionId(Product product, List<String> functionIds) {
    LOG.debug("Enter.");

    List<String> existFunctionIds = product.getProductFunctions().stream()
        .map(function -> function.getId())
        .collect(Collectors.toList());

    if (!existFunctionIds.containsAll(functionIds)) {
      LOG.debug("Function: {} is not all in product: {}.", functionIds, product.getId());
      throw new ParametersException("FunctionIds is not all in product");
    }

    LOG.debug("Exit.");
  }
}
