package com.umasuo.product.application.service.update;

import com.umasuo.exception.NotExistException;
import com.umasuo.exception.ParametersException;
import com.umasuo.model.Updater;
import com.umasuo.product.application.dto.action.RemoveDataDefinition;
import com.umasuo.product.application.service.RestClient;
import com.umasuo.product.domain.model.Product;
import com.umasuo.product.infrastructure.update.UpdateAction;
import com.umasuo.product.infrastructure.update.UpdateActionUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * 从产品中移除数据的service.
 */
@Service(value = UpdateActionUtils.REMOVE_DATA_DEFINITION)
public class RemoveDataDefinitionService implements Updater<Product, UpdateAction> {

  /**
   * Logger.
   */
  private static final Logger LOG = LoggerFactory.getLogger(RemoveDataDefinitionService.class);

  /**
   * RestClient.
   */
  @Autowired
  private transient RestClient restClient;

  /**
   * 执行update的方法。
   *
   * @param entity the product
   * @param updateAction the RemoveDataDefinition
   */
  @Override
  public void handle(Product entity, UpdateAction updateAction) {
    LOG.debug("Enter. product id: {}, updateAction: {}.", entity.getId(), updateAction);

    List<String> dataDefinitions = entity.getDataDefineIds();

    if (CollectionUtils.isEmpty(dataDefinitions)) {
      LOG.debug("Product: {} dataDefinition is null, can not remove anything.", entity.getId());
      throw new ParametersException("Can not remove dataDefinition from null list");
    }

    String removeId = ((RemoveDataDefinition) updateAction).getDataDefinitionId();

    if (!dataDefinitions.contains(removeId)) {
      LOG.debug("dataDefinition: {} is not belong product: {} all.", removeId, entity.getId());
      throw new NotExistException("DataDefinition is not exist in the product");
    }

    entity.getDataDefineIds().remove(removeId);

    restClient.deleteDataDefinition(entity.getDeveloperId(), entity.getId(), removeId);

    LOG.debug("Exit.");
  }
}
