package com.umasuo.product.application.service.update;

import com.umasuo.exception.AlreadyExistException;
import com.umasuo.model.Updater;
import com.umasuo.product.application.dto.action.AddProductTypeFunction;
import com.umasuo.product.application.dto.mapper.CommonFunctionMapper;
import com.umasuo.product.domain.model.CommonFunction;
import com.umasuo.product.domain.model.ProductType;
import com.umasuo.product.infrastructure.update.UpdateAction;
import com.umasuo.product.infrastructure.update.UpdateActionUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

/**
 * 添加产品类别的功能的service.
 */
@Service(UpdateActionUtils.ADD_PRODUCT_TYPE_FUNCTION)
public class AddProductTypeFunctionService implements Updater<ProductType, UpdateAction> {

  /**
   * Logger.
   */
  private static final Logger LOG = LoggerFactory.getLogger(AddProductTypeFunctionService.class);

  /**
   * 执行update的方法。
   *
   * @param productType the ProductType.
   * @param updateAction the AddProductTypeFunction
   */
  @Override
  public void handle(ProductType productType, UpdateAction updateAction) {
    LOG.debug("Enter. product: {}, updateAction: {}.", productType, updateAction);

    AddProductTypeFunction action = (AddProductTypeFunction) updateAction;

    checkFunctionId(productType, action.getFunctionId());

    CommonFunction function = CommonFunctionMapper.build(action);

    productType.getFunctions().add(function);

    LOG.debug("Exit.");
  }

  /**
   * 判断functionId是否已经存在。
   *
   * @param productType the ProductType
   * @param functionId the functionId
   */
  private void checkFunctionId(ProductType productType, String functionId) {
    LOG.debug("Enter.");

    boolean existFunctionId = false;

    if (!CollectionUtils.isEmpty(productType.getFunctions())) {
      existFunctionId = productType.getFunctions().stream()
          .anyMatch(function -> functionId.equals(function.getFunctionId()));
    }

    if (existFunctionId) {
      LOG.debug("functionId: {} is exist in productType: {}.", functionId, productType.getId());
      throw new AlreadyExistException("FunctionId is exist");
    }

    LOG.debug("Exit.");
  }
}
