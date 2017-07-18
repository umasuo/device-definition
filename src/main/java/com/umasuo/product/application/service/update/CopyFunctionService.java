package com.umasuo.product.application.service.update;

import com.umasuo.product.application.dto.action.CopyFunction;
import com.umasuo.product.application.dto.mapper.CommonFunctionMapper;
import com.umasuo.product.domain.model.CommonFunction;
import com.umasuo.product.domain.model.Product;
import com.umasuo.product.domain.model.ProductFunction;
import com.umasuo.product.domain.model.ProductType;
import com.umasuo.product.domain.service.ProductTypeService;
import com.umasuo.product.infrastructure.update.UpdateAction;
import com.umasuo.product.infrastructure.update.UpdateActionUtils;
import com.umasuo.exception.AlreadyExistException;
import com.umasuo.exception.ParametersException;
import com.umasuo.model.Updater;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Created by Davis on 17/7/4.
 */
@Service(UpdateActionUtils.COPY_FUNCTION)
public class CopyFunctionService implements Updater<Product, UpdateAction>{

  /**
   * Logger.
   */
  private static final Logger LOG = LoggerFactory.getLogger(CopyFunctionService.class);

  @Autowired
  private transient ProductTypeService productTypeService;


  @Override
  public void handle(Product product, UpdateAction updateAction) {
    LOG.debug("Enter. product: {}, updateAction: {}.", product, updateAction);

    List<String> functionIds = ((CopyFunction) updateAction).getFunctionIds();

    ProductType productType = productTypeService.getById(product.getProductType());

    List<CommonFunction> commonFunctions = getFunctions(productType, functionIds);

    checkExistFunction(commonFunctions, product);

    List<ProductFunction> functions = CommonFunctionMapper.copy(commonFunctions);

    product.getProductFunctions().addAll(functions);

    LOG.debug("Exit.");
  }

  private void checkExistFunction(List<CommonFunction> commonFunctions, Product product) {
    List<String> functionIds = commonFunctions.stream().map(
        function -> function.getFunctionId()
    ).collect(Collectors.toList());

    Predicate<ProductFunction> predicate =
        function -> functionIds.contains(function.getFunctionId());
    boolean existFunctionId = product.getProductFunctions().stream().anyMatch(predicate);
    if (existFunctionId) {
      LOG.debug("Function has been exist.");
      throw new AlreadyExistException("Function has bean exist");
    }
  }

  private List<CommonFunction> getFunctions(ProductType productType, List<String> functionIds) {
    List<String> existFunctionIds = productType.getFunctions().stream().map(
        function -> function.getId()).collect(Collectors.toList());

    if (!existFunctionIds.containsAll(functionIds)) {
      LOG.debug("Function: {} is not all in productType: {}.", functionIds, productType.getId());
      throw new ParametersException("FunctionIds is not all in productType");
    }

    List<CommonFunction> result = productType.getFunctions().stream()
        .filter(function -> functionIds.contains(function.getId()))
        .collect(Collectors.toList());

    return result;
  }
}
