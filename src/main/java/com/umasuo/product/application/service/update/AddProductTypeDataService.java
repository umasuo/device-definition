package com.umasuo.product.application.service.update;

import com.umasuo.model.Updater;
import com.umasuo.product.application.dto.ProductDataView;
import com.umasuo.product.application.dto.ProductTypeView;
import com.umasuo.product.application.dto.action.AddDataDefinition;
import com.umasuo.product.application.dto.action.AddProductTypeData;
import com.umasuo.product.application.service.ProductQueryApplication;
import com.umasuo.product.application.service.ProductTypeApplication;
import com.umasuo.product.application.service.RestClient;
import com.umasuo.product.domain.model.Product;
import com.umasuo.product.domain.model.ProductType;
import com.umasuo.product.infrastructure.update.UpdateAction;
import com.umasuo.product.infrastructure.update.UpdateActionUtils;
import com.umasuo.product.infrastructure.validator.DataIdValidator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * Created by Davis on 17/7/7.
 */
@Service(UpdateActionUtils.ADD_PRODUCT_TYPE_DATA)
public class AddProductTypeDataService implements Updater<ProductType, UpdateAction> {

  /**
   * Logger.
   */
  private static final Logger LOG = LoggerFactory.getLogger(AddProductTypeDataService.class);

  @Autowired
  private transient RestClient restClient;

  @Autowired
  private transient ProductTypeApplication productTypeApplication;

  @Autowired
  private transient ProductQueryApplication productQueryApplication;

  @Override
  public void handle(ProductType productType, UpdateAction updateAction) {
    LOG.debug("Enter.");
    AddProductTypeData action = (AddProductTypeData) updateAction;

    action.setProductTypeId(productType.getId());

    String dataDefinitionId = restClient.addProductTypeData(action);

    productType.getDataIds().add(dataDefinitionId);
    LOG.debug("Exit.");
  }
}
