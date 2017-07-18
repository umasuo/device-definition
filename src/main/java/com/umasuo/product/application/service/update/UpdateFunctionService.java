package com.umasuo.product.application.service.update;

import com.umasuo.product.application.dto.ProductTypeView;
import com.umasuo.product.application.dto.action.UpdateFunction;
import com.umasuo.product.application.dto.mapper.ProductFunctionMapper;
import com.umasuo.product.application.service.ProductTypeApplication;
import com.umasuo.product.domain.model.Product;
import com.umasuo.product.domain.model.ProductFunction;
import com.umasuo.product.infrastructure.update.UpdateAction;
import com.umasuo.product.infrastructure.update.UpdateActionUtils;
import com.umasuo.exception.AlreadyExistException;
import com.umasuo.exception.NotExistException;
import com.umasuo.model.Updater;

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
    ProductFunction function = getFunction(product.getProductFunctions(), action.getId());

    ProductFunctionMapper.merge(function, action);
    LOG.debug("Exit.");
  }

  private ProductFunction getFunction(List<ProductFunction> productFunctions, String id) {
    LOG.debug("Enter.");
    if (productFunctions == null) {
      LOG.debug("Product do not have any functions.");
      throw new NotExistException("Function not exist");
    }

    ProductFunction function =
        productFunctions.stream().filter(f -> id.equals(f.getId())).findAny().orElse(null);

    if (function == null) {
      LOG.debug("Can not find function: {}.", id);
      throw new NotExistException("Function not exist");
    }

    LOG.debug("Exit.");
    return function;
  }

  private void checkFunctionId(String id, String functionId, Product product) {
    LOG.debug("Enter.");

    ProductTypeView productType = productTypeApplication.get(product.getProductType());

    boolean sameAsPlatformFunction =
        productType.getFunctions().stream()
            .anyMatch(function -> functionId.equals(function.getFunctionId()));

    boolean existFunctionId = product.getProductFunctions().stream().anyMatch(
        function -> functionId.equals(function.getFunctionId()) && !id.equals(function.getId()));

    if (sameAsPlatformFunction || existFunctionId) {
      LOG.debug("FunctionId: {} is already exist.", functionId);
      throw new AlreadyExistException("FunctionId already exist");
    }

    LOG.debug("Exit.");
  }
}
