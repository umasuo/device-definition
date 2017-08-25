package com.umasuo.product.application.service.update;

import com.umasuo.exception.AlreadyExistException;
import com.umasuo.model.Updater;
import com.umasuo.product.application.dto.action.AddProductTypeFunction;
import com.umasuo.product.application.dto.mapper.CommonFunctionMapper;
import com.umasuo.product.application.service.ProductTypeApplication;
import com.umasuo.product.domain.model.CommonFunction;
import com.umasuo.product.domain.model.ProductType;
import com.umasuo.product.infrastructure.update.UpdateAction;
import com.umasuo.product.infrastructure.update.UpdateActionUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

/**
 * Created by Davis on 17/7/4.
 */
@Service(UpdateActionUtils.ADD_PRODUCT_TYPE_FUNCTION)
public class AddProductTypeFunctionService implements Updater<ProductType, UpdateAction> {

  /**
   * Logger.
   */
  private static final Logger LOG = LoggerFactory.getLogger(AddProductTypeFunctionService.class);

  @Autowired
  private transient ProductTypeApplication productTypeApplication;

  @Override
  public void handle(ProductType productType, UpdateAction updateAction) {
    LOG.debug("Enter. product: {}, updateAction: {}.", productType, updateAction);

    AddProductTypeFunction action = (AddProductTypeFunction) updateAction;

    checkFunctionId(productType, action.getFunctionId());

    CommonFunction function = CommonFunctionMapper.build(action);

    productType.getFunctions().add(function);

    LOG.debug("Exit.");
  }

  private void checkFunctionId(ProductType productType, String functionId) {
    LOG.debug("Enter.");

    boolean sameAsPlatformData = false;

    if (!CollectionUtils.isEmpty(productType.getFunctions())) {
      sameAsPlatformData = productType.getFunctions().stream()
          .anyMatch(function -> functionId.equals(function.getFunctionId()));
    }

    LOG.debug("Exit. ");

    if (sameAsPlatformData) {
      LOG.debug("functionId: {} is exist in productType: {}.", functionId, productType.getId());
      throw new AlreadyExistException("FunctionId is exist");
    }

    LOG.debug("Exit.");
  }
}
