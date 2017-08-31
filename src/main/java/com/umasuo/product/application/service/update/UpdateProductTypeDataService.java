package com.umasuo.product.application.service.update;

import com.google.common.collect.Lists;
import com.umasuo.exception.NotExistException;
import com.umasuo.model.Updater;
import com.umasuo.product.application.dto.action.UpdateProductTypeData;
import com.umasuo.product.application.service.RestClient;
import com.umasuo.product.domain.model.ProductType;
import com.umasuo.product.infrastructure.update.UpdateAction;
import com.umasuo.product.infrastructure.update.UpdateActionUtils;
import com.umasuo.product.infrastructure.update.UpdateRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 更新产品类别的数据的service.
 */
@Service(UpdateActionUtils.UPDATE_PRODUCT_TYPE_DATA)
public class UpdateProductTypeDataService implements Updater<ProductType, UpdateAction> {

  /**
   * Logger.
   */
  private static final Logger LOG = LoggerFactory.getLogger(UpdateProductTypeDataService.class);

  /**
   * RestClient.
   */
  @Autowired
  private transient RestClient restClient;

  /**
   * 执行update的方法。
   *
   * @param productType the ProductType
   * @param updateAction the UpdateProductTypeData
   */
  @Override
  public void handle(ProductType productType, UpdateAction updateAction) {
    LOG.debug("Enter. productType id: {}, updateAction: {}.", productType.getId(), updateAction);

    UpdateProductTypeData action = (UpdateProductTypeData) updateAction;
    String dataDefinitionId = action.getDataDefinitionId();

    if (productType.getDataIds() == null ||
        !productType.getDataIds().contains(dataDefinitionId)) {
      LOG.debug("Can not find dataDefinition: {}.", dataDefinitionId);
      throw new NotExistException("DataDefinition not exist.");
    }

    UpdateRequest request = new UpdateRequest();

    List<UpdateAction> actions = Lists.newArrayList(updateAction);

    // TODO: 17/8/29 应该从页面传进来
    request.setVersion(0);
    request.setActions(actions);

    restClient.updateProductTypeData(dataDefinitionId, request);

    LOG.debug("Exit.");
  }
}
