package com.umasuo.device.definition.application.service.update;

import com.google.common.collect.Lists;
import com.umasuo.device.definition.application.dto.ProductDataView;
import com.umasuo.device.definition.application.dto.ProductTypeView;
import com.umasuo.device.definition.application.dto.action.UpdateDataDefinition;
import com.umasuo.device.definition.application.service.ProductQueryApplication;
import com.umasuo.device.definition.application.service.ProductTypeApplication;
import com.umasuo.device.definition.application.service.RestClient;
import com.umasuo.device.definition.domain.model.Product;
import com.umasuo.device.definition.infrastructure.update.UpdateAction;
import com.umasuo.device.definition.infrastructure.update.UpdateActionUtils;
import com.umasuo.device.definition.infrastructure.update.UpdateRequest;
import com.umasuo.exception.AlreadyExistException;
import com.umasuo.exception.NotExistException;
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
public class UpdateDataService implements Updater<Product, UpdateAction> {

  /**
   * Logger.
   */
  private static final Logger LOG = LoggerFactory.getLogger(UpdateDataService.class);

  @Autowired
  private transient RestClient restClient;


  @Autowired
  private transient ProductTypeApplication productTypeApplication;

  @Autowired
  private transient ProductQueryApplication productQueryApplication;


  @Override
  public void handle(Product device, UpdateAction updateAction) {
    LOG.debug("Enter.");
    UpdateDataDefinition action = (UpdateDataDefinition) updateAction;
    String dataDefinitionId = action.getDataDefinitionId();

    if (device.getDataDefineIds() == null ||
        !device.getDataDefineIds().contains(dataDefinitionId)) {
      LOG.debug("Can not find dataDefinition: {}.", dataDefinitionId);
      throw new NotExistException("DataDefinition not exist.");
    }

    checkDataId(dataDefinitionId, action.getDataId(), device);

    UpdateRequest request = new UpdateRequest();
    request.setVersion(action.getVersion());

    List<UpdateAction> actions = Lists.newArrayList();

    actions.add(updateAction);

    request.setActions(actions);

    restClient.updateDataDefinition(dataDefinitionId, device.getDeveloperId(), request);

    LOG.debug("Exit.");
  }

  private void checkDataId(String dataDefinitionId, String dataId, Product device) {
    LOG.debug("Enter.");

    ProductTypeView productType = productTypeApplication.get(device.getProductType());

    List<ProductDataView> productDataViews =
        productQueryApplication.getDataDefinitions(device.getDeveloperId(), device.getId());

    boolean sameAsPlatformData =
        productType.getData().stream().anyMatch(data -> dataId.equals(data.getDataId()));

    boolean existDataId =
        productDataViews.stream().anyMatch(
            data -> dataId.equals(data.getDataId()) && !dataDefinitionId.equals(data.getId()));

    if (sameAsPlatformData || existDataId) {
      LOG.debug("Can not add exist dataId: {}.", dataId);
      throw new AlreadyExistException("DataId already exist");
    }

    LOG.debug("Exit.");
  }
}
