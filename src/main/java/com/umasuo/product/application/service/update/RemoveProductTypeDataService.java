package com.umasuo.product.application.service.update;

import com.umasuo.exception.ParametersException;
import com.umasuo.model.Updater;
import com.umasuo.product.application.dto.action.RemoveDataDefinition;
import com.umasuo.product.application.dto.action.RemoveProductTypeData;
import com.umasuo.product.application.service.RestClient;
import com.umasuo.product.domain.model.Product;
import com.umasuo.product.domain.model.ProductType;
import com.umasuo.product.infrastructure.update.UpdateAction;
import com.umasuo.product.infrastructure.update.UpdateActionUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by umasuo on 17/6/1.
 */
@Service(value = UpdateActionUtils.REMOVE_PRODUCT_TYPE_DATA)
public class RemoveProductTypeDataService implements Updater<ProductType, UpdateAction> {

  /**
   * Logger.
   */
  private static final Logger LOG = LoggerFactory.getLogger(RemoveProductTypeDataService.class);

  @Autowired
  private transient RestClient restClient;

  @Override
  public void handle(ProductType entity, UpdateAction updateAction) {
    String removeId = ((RemoveProductTypeData) updateAction).getDataDefinitionId();

    List<String> dataDefinitions = entity.getDataIds();

    if (dataDefinitions == null) {
      LOG.debug("Product: {} dataDefinition is null, can not remove anything.", entity.getId());
      throw new ParametersException("Can not remove dataDefinition from null list");
    }

    if (!dataDefinitions.contains(removeId)) {
      LOG.debug("dataDefinition: {} is not belong product: {} all.", removeId, entity.getId());
    }

    entity.getDataIds().remove(removeId);

    restClient.deletePlatformData(entity.getId(), removeId);
  }

}
