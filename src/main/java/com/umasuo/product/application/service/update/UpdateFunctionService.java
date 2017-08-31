package com.umasuo.product.application.service.update;

import com.umasuo.model.Updater;
import com.umasuo.product.application.dto.ProductTypeView;
import com.umasuo.product.application.dto.action.UpdateFunction;
import com.umasuo.product.application.dto.mapper.ProductFunctionMapper;
import com.umasuo.product.application.service.ProductTypeApplication;
import com.umasuo.product.domain.model.Product;
import com.umasuo.product.domain.model.ProductFunction;
import com.umasuo.product.infrastructure.update.UpdateAction;
import com.umasuo.product.infrastructure.update.UpdateActionUtils;
import com.umasuo.product.infrastructure.util.FunctionUtils;
import com.umasuo.product.infrastructure.validator.FunctionIdValidator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 更新产品的功能的service.
 */
@Service(UpdateActionUtils.UPDATE_FUNCTION)
public class UpdateFunctionService implements Updater<Product, UpdateAction> {

  /**
   * Logger.
   */
  private static final Logger LOG = LoggerFactory.getLogger(UpdateFunctionService.class);

  /**
   * ProductTypeApplication.
   */
  @Autowired
  private transient ProductTypeApplication productTypeApplication;

  /**
   * 执行update的方法。
   *
   * @param product the Product
   * @param updateAction the UpdateFunction
   */
  @Override
  public void handle(Product product, UpdateAction updateAction) {
    LOG.debug("Enter. productId: {}, updateAction: {}.", product, updateAction);

    UpdateFunction action = (UpdateFunction) updateAction;

    checkFunctionId(action.getId(), action.getFunctionId(), product);
    ProductFunction function =
        FunctionUtils.getProductFunction(product.getProductFunctions(), action.getId());

    ProductFunctionMapper.merge(function, action);

    LOG.debug("Exit.");
  }

  /**
   * 判断functionId是否合法：对应的ProductType是否存在该functionId，已存在的其它function是否有该id。
   *
   * @param id the id
   * @param functionId the functionId
   * @param product the Product
   */
  private void checkFunctionId(String id, String functionId, Product product) {
    LOG.debug("Enter.");
    ProductTypeView productType = productTypeApplication.get(product.getProductType());

    FunctionIdValidator.checkForUpdate(id, functionId, productType, product.getProductFunctions());

    LOG.debug("Exit.");
  }
}
