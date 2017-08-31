package com.umasuo.product.application.service.update;

import com.google.common.collect.Lists;
import com.umasuo.exception.NotExistException;
import com.umasuo.model.Updater;
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
import com.umasuo.product.infrastructure.validator.DataIdValidator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 更新产品的数据的service.
 */
@Service(UpdateActionUtils.UPDATE_DATA_DEFINITION)
public class UpdateDataService implements Updater<Product, UpdateAction> {

  /**
   * Logger.
   */
  private static final Logger LOG = LoggerFactory.getLogger(UpdateDataService.class);

  /**
   * RestClient.
   */
  @Autowired
  private transient RestClient restClient;

  /**
   * ProductTypeApplication.
   */
  @Autowired
  private transient ProductTypeApplication productTypeApplication;

  /**
   * ProductQueryApplication.
   */
  @Autowired
  private transient ProductQueryApplication productQueryApplication;

  /**
   * 执行update的方法。
   *
   * @param product the Product
   * @param updateAction the UpdateDataDefinition
   */
  @Override
  public void handle(Product product, UpdateAction updateAction) {
    LOG.debug("Enter. productId: {}, updateAction: {}.", product.getId(), updateAction);

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

  /**
   * 判断dataId是否合法：对应的ProductType没有该dataId，已存在的data没有该id。
   *
   * @param dataDefinitionId the dataDefinitionId
   * @param dataId the dataId
   * @param product the Product
   */
  private void checkDataId(String dataDefinitionId, String dataId, Product product) {
    LOG.debug("Enter.");

    ProductTypeView productType = productTypeApplication.get(product.getProductType());

    List<ProductDataView> productDataViews =
        productQueryApplication.getDataDefinitions(product.getDeveloperId(), product.getId());

    DataIdValidator.checkForUpdate(dataDefinitionId, dataId, productType, productDataViews);

    LOG.debug("Exit.");
  }
}
