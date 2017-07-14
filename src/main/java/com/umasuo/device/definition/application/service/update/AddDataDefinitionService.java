package com.umasuo.device.definition.application.service.update;

import com.umasuo.device.definition.application.dto.ProductTypeView;
import com.umasuo.device.definition.application.dto.action.AddDataDefinition;
import com.umasuo.device.definition.application.service.ProductTypeApplication;
import com.umasuo.device.definition.application.service.RestClient;
import com.umasuo.device.definition.domain.model.Product;
import com.umasuo.device.definition.infrastructure.update.UpdateAction;
import com.umasuo.device.definition.infrastructure.update.UpdateActionUtils;
import com.umasuo.exception.AlreadyExistException;
import com.umasuo.model.Updater;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Davis on 17/7/7.
 */
@Service(UpdateActionUtils.ADD_DATA_DEFINITION)
public class AddDataDefinitionService implements Updater<Product, UpdateAction>{

  /**
   * Logger.
   */
  private static final Logger LOG = LoggerFactory.getLogger(AddDataDefinitionService.class);

  @Autowired
  private transient RestClient restClient;

  @Autowired
  private transient ProductTypeApplication productTypeApplication;

  @Override
  public void handle(Product device, UpdateAction updateAction) {
    LOG.debug("Enter.");
    AddDataDefinition action = (AddDataDefinition) updateAction;

    checkDataId(action.getDataId(), device);

    action.setProductId(device.getId());

    String dataDefinitionId = restClient.createDataDefinition(device.getDeveloperId(), action);

    device.getDataDefineIds().add(dataDefinitionId);
    LOG.debug("Exit.");
  }

  private void checkDataId(String dataId, Product device) {
    LOG.debug("Enter.");
    ProductTypeView productType = productTypeApplication.get(device.getProductType());

    boolean existDataId =
        productType.getData().stream().anyMatch(data -> dataId.equals(data.getDataId()));

    if (existDataId) {
      LOG.debug("Can not add exist dataId: {}.", dataId);
      throw new AlreadyExistException("DataId already exist");
    }
    LOG.debug("Exit. dataId not exist.");
  }
}
