package com.umasuo.product.application.service.update;

import com.umasuo.product.application.dto.action.RemoveDataDefinition;
import com.umasuo.product.application.service.RestClient;
import com.umasuo.product.domain.model.Product;
import com.umasuo.product.infrastructure.update.UpdateAction;
import com.umasuo.product.infrastructure.update.UpdateActionUtils;
import com.umasuo.exception.ParametersException;
import com.umasuo.model.Updater;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by umasuo on 17/6/1.
 */
@Service(value = UpdateActionUtils.REMOVE_DATA_DEFINITION)
public class RemoveDataDefinitionService implements Updater<Product, UpdateAction> {

  /**
   * Logger.
   */
  private static final Logger LOG = LoggerFactory.getLogger(RemoveDataDefinitionService.class);

  @Autowired
  private transient RestClient restClient;

  @Override
  public void handle(Product entity, UpdateAction updateAction) {
    String removeId = ((RemoveDataDefinition) updateAction).getDataDefinitionId();

    List<String> dataDefinitions = entity.getDataDefineIds();

    if (dataDefinitions == null) {
      LOG.debug("Product: {} dataDefinition is null, can not remove anything.", entity.getId());
      throw new ParametersException("Can not remove dataDefinition from null list");
    }

    if (!dataDefinitions.contains(removeId)) {
      LOG.debug("dataDefinition: {} is not belong product: {} all.", removeId, entity.getId());
    }

    entity.getDataDefineIds().remove(removeId);

    restClient.deleteDataDefinition(entity.getDeveloperId(), entity.getId(), removeId);
  }

}
