package com.umasuo.product.application.service.update;

import com.umasuo.exception.AlreadyExistException;
import com.umasuo.exception.ParametersException;
import com.umasuo.model.Updater;
import com.umasuo.product.application.dto.action.CopyFunction;
import com.umasuo.product.application.dto.mapper.ProductFunctionMapper;
import com.umasuo.product.domain.model.CommonFunction;
import com.umasuo.product.domain.model.Product;
import com.umasuo.product.domain.model.ProductFunction;
import com.umasuo.product.domain.model.ProductType;
import com.umasuo.product.domain.service.ProductTypeService;
import com.umasuo.product.infrastructure.update.UpdateAction;
import com.umasuo.product.infrastructure.update.UpdateActionUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * 拷贝功能到产品中的service.
 */
@Service(UpdateActionUtils.COPY_FUNCTION)
public class CopyFunctionService implements Updater<Product, UpdateAction> {

  /**
   * Logger.
   */
  private static final Logger LOG = LoggerFactory.getLogger(CopyFunctionService.class);

  /**
   * ProductTypeService.
   */
  @Autowired
  private transient ProductTypeService productTypeService;

  /**
   * 执行update的方法。
   *
   * @param product the Product
   * @param updateAction the CopyFunction
   */
  @Override
  public void handle(Product product, UpdateAction updateAction) {
    LOG.debug("Enter. product: {}, updateAction: {}.", product, updateAction);

    List<String> functionIds = ((CopyFunction) updateAction).getFunctionIds();

    ProductType productType = productTypeService.getById(product.getProductType());

    List<CommonFunction> commonFunctions = getFunctions(productType, functionIds);

    checkExistFunction(commonFunctions, product);

    List<ProductFunction> functions = ProductFunctionMapper.copy(commonFunctions);

    product.getProductFunctions().addAll(functions);

    LOG.debug("Exit.");
  }

  /**
   * 判断要拷贝的function是否已经存在。
   *
   * @param commonFunctions the function
   * @param product the Product
   */
  private void checkExistFunction(List<CommonFunction> commonFunctions, Product product) {
    LOG.debug("Enter. function size: {}, productId: {}.", commonFunctions.size(), product.getId());
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

    LOG.debug("Exit.");
  }

  /**
   * 根据functionId获取对应的Function。
   *
   * @param productType the ProductType
   * @param functionIds list build function id
   * @return list build CommonFunction
   */
  private List<CommonFunction> getFunctions(ProductType productType, List<String> functionIds) {
    LOG.debug("Enter. productType id: {}, functionIds: {}.", productType, functionIds);

    List<String> existFunctionIds = productType.getFunctions().stream().map(
        function -> function.getId()).collect(Collectors.toList());

    if (!existFunctionIds.containsAll(functionIds)) {
      LOG.debug("Function: {} is not all in productType: {}.", functionIds, productType.getId());
      throw new ParametersException("FunctionIds is not all in productType");
    }

    List<CommonFunction> result = productType.getFunctions().stream()
        .filter(function -> functionIds.contains(function.getId()))
        .collect(Collectors.toList());

    LOG.debug("Exit.");
    return result;
  }
}
