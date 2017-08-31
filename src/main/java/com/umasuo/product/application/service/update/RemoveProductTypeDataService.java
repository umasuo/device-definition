package com.umasuo.product.application.service.update;

import com.umasuo.exception.NotExistException;
import com.umasuo.exception.ParametersException;
import com.umasuo.model.Updater;
import com.umasuo.product.application.dto.action.RemoveProductTypeData;
import com.umasuo.product.application.service.RestClient;
import com.umasuo.product.domain.model.ProductType;
import com.umasuo.product.infrastructure.update.UpdateAction;
import com.umasuo.product.infrastructure.update.UpdateActionUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * 从产品类别中移除数据的service.
 */
@Service(value = UpdateActionUtils.REMOVE_PRODUCT_TYPE_DATA)
public class RemoveProductTypeDataService implements Updater<ProductType, UpdateAction> {

  /**
   * Logger.
   */
  private static final Logger LOG = LoggerFactory.getLogger(RemoveProductTypeDataService.class);

  /**
   * RestClient.
   */
  @Autowired
  private transient RestClient restClient;

  /**
   * 执行update的方法。
   *
   * @param entity the ProductType
   * @param updateAction the RemoveProductTypeData
   */
  @Override
  public void handle(ProductType entity, UpdateAction updateAction) {
    LOG.debug("Enter. productType id: {}, updateAction: {}.", entity.getId(), updateAction);

    List<String> dataDefinitions = entity.getDataIds();

    if (CollectionUtils.isEmpty(dataDefinitions)) {
      LOG.debug("Product: {} dataDefinition is null, can not remove anything.", entity.getId());
      throw new ParametersException("Can not remove dataDefinition from null list");
    }

    String removeId = ((RemoveProductTypeData) updateAction).getDataDefinitionId();

    if (!dataDefinitions.contains(removeId)) {
      LOG.debug("dataDefinition: {} is not belong product: {} all.", removeId, entity.getId());
      throw new NotExistException("DataDefinition is not exist in the product type");
    }

    entity.getDataIds().remove(removeId);

    restClient.deleteProductTypeData(entity.getId(), removeId);

    LOG.debug("Exit.");
  }

}
