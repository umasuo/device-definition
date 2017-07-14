package com.umasuo.device.definition.application.service.update;

import com.google.common.collect.Lists;
import com.umasuo.device.definition.application.dto.action.UpdateDataDefinition;
import com.umasuo.device.definition.application.service.RestClient;
import com.umasuo.device.definition.domain.model.Product;
import com.umasuo.device.definition.infrastructure.update.UpdateAction;
import com.umasuo.device.definition.infrastructure.update.UpdateActionUtils;
import com.umasuo.device.definition.infrastructure.update.UpdateRequest;
import com.umasuo.model.Updater;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Davis on 17/7/12.
 */
@Service(UpdateActionUtils.UPDATE_DATA_DEFINITION)
public class UpdateDataService implements Updater<Product, UpdateAction>{

  /**
   * Logger.
   */
  private static final Logger LOG = LoggerFactory.getLogger(UpdateDataService.class);

  @Autowired
  private transient RestClient restClient;

  @Override
  public void handle(Product device, UpdateAction updateAction) {
    LOG.debug("Enter.");
    UpdateDataDefinition action = (UpdateDataDefinition) updateAction;
    String dataDefinitionId = action.getDataDefinitionId();

    if (device.getDataDefineIds() == null ||
        !device.getDataDefineIds().contains(dataDefinitionId)) {
      LOG.debug("Can not find dataDefinition: {}.", dataDefinitionId);
    }

    UpdateRequest request = new UpdateRequest();
    request.setVersion(action.getVersion());

    List<UpdateAction> actions = Lists.newArrayList();

    actions.add(updateAction);

    request.setActions(actions);

    restClient.updateDataDefinition(dataDefinitionId, device.getDeveloperId(), request);
  }
}
