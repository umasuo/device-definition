package com.umasuo.product.application.service.update;

import com.google.common.collect.Lists;
import com.umasuo.product.application.dto.ProductDataView;
import com.umasuo.product.application.dto.ProductTypeView;
import com.umasuo.product.application.dto.action.UpdateDataDefinition;
import com.umasuo.product.application.service.ProductQueryApplication;
import com.umasuo.product.application.service.ProductTypeApplication;
import com.umasuo.product.application.service.RestClient;
import com.umasuo.product.domain.model.Product;
import com.umasuo.product.infrastructure.update.UpdateAction;
import com.umasuo.product.infrastructure.update.UpdateActionUtils;
import com.umasuo.product.infrastructure.update.UpdateRequest;
import com.umasuo.exception.AlreadyExistException;
import com.umasuo.exception.NotExistException;
import com.umasuo.model.Updater;
import com.umasuo.product.infrastructure.validator.DataIdValidator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

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
  public void handle(Product product, UpdateAction updateAction) {
    LOG.debug("Enter.");
    UpdateDataDefinition action = (UpdateDataDefinition) updateAction;
    String dataDefinitionId = action.getDataDefinitionId();

    if (product.getDataDefineIds() == null ||
        !product.getDataDefineIds().contains(dataDefinitionId)) {
      LOG.debug("Can not find dataDefinition: {}.", dataDefinitionId);
      throw new NotExistException("DataDefinition not exist.");
    }

    checkDataId(dataDefinitionId, action.getDataId(), product);

    UpdateRequest request = new UpdateRequest();
    request.setVersion(action.getVersion());

    List<UpdateAction> actions = Lists.newArrayList();

    actions.add(updateAction);

    request.setActions(actions);

    restClient.updateDataDefinition(dataDefinitionId, product.getDeveloperId(), request);

    LOG.debug("Exit.");
  }

  private void checkDataId(String dataDefinitionId, String dataId, Product product) {
    LOG.debug("Enter.");

    ProductTypeView productType = productTypeApplication.get(product.getProductType());

    List<ProductDataView> productDataViews =
        productQueryApplication.getDataDefinitions(product.getDeveloperId(), product.getId());

    DataIdValidator.checkForUpdate(dataDefinitionId, dataId, productType, productDataViews);

    LOG.debug("Exit.");
  }
}
