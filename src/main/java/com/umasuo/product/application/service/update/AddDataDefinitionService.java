package com.umasuo.product.application.service.update;

import com.umasuo.product.application.dto.ProductDataView;
import com.umasuo.product.application.dto.ProductTypeView;
import com.umasuo.product.application.dto.action.AddDataDefinition;
import com.umasuo.product.application.service.ProductQueryApplication;
import com.umasuo.product.application.service.ProductTypeApplication;
import com.umasuo.product.application.service.RestClient;
import com.umasuo.product.domain.model.Product;
import com.umasuo.product.infrastructure.update.UpdateAction;
import com.umasuo.product.infrastructure.update.UpdateActionUtils;
import com.umasuo.exception.AlreadyExistException;
import com.umasuo.model.Updater;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

  @Autowired
  private transient ProductQueryApplication productQueryApplication;

  @Override
  public void handle(Product product, UpdateAction updateAction) {
    LOG.debug("Enter.");
    AddDataDefinition action = (AddDataDefinition) updateAction;

    checkDataId(action.getDataId(), product);

    action.setProductId(product.getId());

    String dataDefinitionId = restClient.createDataDefinition(product.getDeveloperId(), action);

    product.getDataDefineIds().add(dataDefinitionId);
    LOG.debug("Exit.");
  }

  private void checkDataId(String dataId, Product product) {
    LOG.debug("Enter.");
    ProductTypeView productType = productTypeApplication.get(product.getProductType());

    List<ProductDataView> productDataViews =
        productQueryApplication.getDataDefinitions(product.getDeveloperId(), product.getId());

    boolean sameAsPlatformData =
        productType.getData().stream().anyMatch(data -> dataId.equals(data.getDataId()));

    boolean existDataId =
        productDataViews.stream().anyMatch(data -> dataId.equals(data.getDataId()));

    if (sameAsPlatformData || existDataId) {
      LOG.debug("Can not add exist dataId: {}.", dataId);
      throw new AlreadyExistException("DataId already exist");
    }
    LOG.debug("Exit. dataId not exist.");
  }
}
