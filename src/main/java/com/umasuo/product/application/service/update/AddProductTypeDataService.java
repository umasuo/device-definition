package com.umasuo.product.application.service.update;

import com.umasuo.model.Updater;
import com.umasuo.product.application.dto.action.AddProductTypeData;
import com.umasuo.product.application.service.RestClient;
import com.umasuo.product.domain.model.ProductType;
import com.umasuo.product.infrastructure.update.UpdateAction;
import com.umasuo.product.infrastructure.update.UpdateActionUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 添加产品类别的数据的service.
 */
@Service(UpdateActionUtils.ADD_PRODUCT_TYPE_DATA)
public class AddProductTypeDataService implements Updater<ProductType, UpdateAction> {

  /**
   * Logger.
   */
  private static final Logger LOG = LoggerFactory.getLogger(AddProductTypeDataService.class);

  /**
   * RestClient.
   */
  @Autowired
  private transient RestClient restClient;

  /**
   * 执行update的方法。
   *
   * @param productType the ProductType
   * @param updateAction the AddProductTypeData
   */
  @Override
  public void handle(ProductType productType, UpdateAction updateAction) {
    LOG.debug("Enter.");
    AddProductTypeData action = (AddProductTypeData) updateAction;

    action.setProductTypeId(productType.getId());

    String dataDefinitionId = restClient.createProductTypeData(action);

    productType.getDataIds().add(dataDefinitionId);
    LOG.debug("Exit.");
  }
}
