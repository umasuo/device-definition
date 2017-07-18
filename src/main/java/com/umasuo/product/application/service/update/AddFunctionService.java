package com.umasuo.product.application.service.update;

import com.umasuo.product.application.dto.action.AddFunction;
import com.umasuo.product.application.dto.mapper.ProductFunctionMapper;
import com.umasuo.product.domain.model.Product;
import com.umasuo.product.domain.model.ProductFunction;
import com.umasuo.product.domain.model.ProductType;
import com.umasuo.product.domain.service.ProductTypeService;
import com.umasuo.product.infrastructure.update.UpdateAction;
import com.umasuo.product.infrastructure.update.UpdateActionUtils;
import com.umasuo.exception.ConflictException;
import com.umasuo.model.Updater;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Davis on 17/7/4.
 */
@Service(UpdateActionUtils.ADD_FUNCTION)
public class AddFunctionService implements Updater<Product, UpdateAction> {

  /**
   * Logger.
   */
  private static final Logger LOG = LoggerFactory.getLogger(AddFunctionService.class);

  @Autowired
  private transient ProductTypeService productTypeService;

  @Override
  public void handle(Product product, UpdateAction updateAction) {
    LOG.debug("Enter. product: {}, updateAction: {}.", product, updateAction);

    AddFunction action = (AddFunction) updateAction;

    checkFunctionId(product, action.getFunctionId());

    ProductFunction productFunction = ProductFunctionMapper.build(action);

    product.getProductFunctions().add(productFunction);

    LOG.debug("Exit.");
  }

  private void checkFunctionId(Product product, String functionId) {
    LOG.debug("Enter.");

    boolean existFunctionId = product.getProductFunctions().stream()
        .anyMatch(function -> function.getFunctionId().equals(functionId));

    if (existFunctionId) {
      LOG.debug("Can not add function: {} in this product: {}.",
          functionId, product.getId());
      throw new ConflictException("FunctionId is exist");
    }

    ProductType productType = productTypeService.getById(product.getProductType());

    existFunctionId =
        productType.getFunctions().stream().anyMatch(p -> functionId.equals(p.getFunctionId()));

    if (existFunctionId) {
      LOG.debug("Can not add function: {} in this product: {} belongs to productType: {}.",
          functionId, product.getId(), product.getProductType());
      throw new ConflictException("FunctionId is exist");
    }

    LOG.debug("Exit.");
  }
}
