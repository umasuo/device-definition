package com.umasuo.product.application.service.update;

import com.umasuo.model.Updater;
import com.umasuo.product.application.dto.action.UpdateStandardFunction;
import com.umasuo.product.application.dto.mapper.ProductFunctionMapper;
import com.umasuo.product.domain.model.Product;
import com.umasuo.product.domain.model.ProductFunction;
import com.umasuo.product.infrastructure.update.UpdateAction;
import com.umasuo.product.infrastructure.update.UpdateActionUtils;
import com.umasuo.product.infrastructure.util.FunctionUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * 更新产品的标准功能的service.
 */
@Service(UpdateActionUtils.UPDATE_STANDARD_FUNCTION)
public class UpdateStandardFunctionService implements Updater<Product, UpdateAction> {

  /**
   * Logger.
   */
  private static final Logger LOG = LoggerFactory.getLogger(UpdateStandardFunctionService.class);


  /**
   * 执行update的方法。
   *
   * @param product the Product
   * @param updateAction the UpdateStandardFunction
   */
  @Override
  public void handle(Product product, UpdateAction updateAction) {
    LOG.debug("Enter. productId: {}, updateAction: {}.", product.getId(), updateAction);

    UpdateStandardFunction action = (UpdateStandardFunction) updateAction;

    ProductFunction function =
        FunctionUtils.getStandardFunction(product.getProductFunctions(), action.getId());

    ProductFunctionMapper.merge(function, action);

    LOG.debug("Exit.");
  }
}
