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
 * 更新产品类别的功能的service.
 */
@Service(UpdateActionUtils.UPDATE_PRODUCT_TYPE_FUNCTION)
public class UpdateProductTypeFunctionService implements Updater<ProductType, UpdateAction> {

  /**
   * Logger.
   */
  private static final Logger LOG = LoggerFactory.getLogger(UpdateProductTypeFunctionService.class);

  /**
   * 执行update的方法。
   *
   * @param productType the ProductType
   * @param updateAction the UpdateProductTypeFunction
   */
  @Override
  public void handle(ProductType productType, UpdateAction updateAction) {
    LOG.debug("Enter. productType id: {}, updateAction: {}.", productType.getId(), updateAction);

    UpdateProductTypeFunction action = (UpdateProductTypeFunction) updateAction;

    CommonFunction function = getProductFunction(productType.getFunctions(), action.getId());

    mergeFunction(function, action);

    LOG.debug("Exit.");
  }

  /**
   * Merge action into CommonFunction.
   *
   * @param function the CommonFunction
   * @param action the action
   */
  private void mergeFunction(CommonFunction function, UpdateProductTypeFunction action) {
    LOG.debug("Enter. function: {}, action: {}.", function, action);

    function.setName(action.getName());
    function.setDescription(action.getDescription());
    function.setTransferType(action.getTransferType());
    function.setDataType(action.getDataType());

    LOG.debug("Exit.");
  }

  /**
   * Filter one CommonFunction by id.
   *
   * @param functions list build CommonFunction
   * @param id the id
   * @return CommonFunction
   */
  private CommonFunction getProductFunction(List<CommonFunction> functions, String id) {
    LOG.debug("Enter. id: {}.", id);

    if (functions == null) {
      LOG.debug("Product type do not have any functions.");
      throw new NotExistException("Function not exist");
    }

    CommonFunction function =
        functions.stream().filter(func -> id.equals(func.getId())).findAny().orElse(null);

    if (function == null) {
      LOG.debug("Can not find function: {}.", id);
      throw new NotExistException("Function not exist");
    }

    LOG.debug("Exit.");

    return function;
  }
}
