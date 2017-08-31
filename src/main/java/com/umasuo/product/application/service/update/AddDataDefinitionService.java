package com.umasuo.product.application.service.update;

import com.umasuo.model.Updater;
import com.umasuo.product.application.dto.ProductDataView;
import com.umasuo.product.application.dto.ProductTypeView;
import com.umasuo.product.application.dto.action.AddDataDefinition;
import com.umasuo.product.application.service.ProductQueryApplication;
import com.umasuo.product.application.service.ProductTypeApplication;
import com.umasuo.product.application.service.RestClient;
import com.umasuo.product.domain.model.Product;
import com.umasuo.product.infrastructure.update.UpdateAction;
import com.umasuo.product.infrastructure.update.UpdateActionUtils;
import com.umasuo.product.infrastructure.validator.DataIdValidator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 添加产品的数据的service.
 */
@Service(UpdateActionUtils.ADD_DATA_DEFINITION)
public class AddDataDefinitionService implements Updater<Product, UpdateAction> {

  /**
   * Logger.
   */
  private static final Logger LOG = LoggerFactory.getLogger(AddDataDefinitionService.class);

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
   * @param product the product
   * @param updateAction the AddDataDefinition
   */
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

  /**
   * 判断dataId是否合法：对应的ProductType没有该dataId，已存在的Data没有该id。
   *
   * @param dataId the dataId
   * @param product the Product
   */
  private void checkDataId(String dataId, Product product) {
    LOG.debug("Enter.");
    ProductTypeView productType = productTypeApplication.get(product.getProductType());

    List<ProductDataView> productDataViews =
        productQueryApplication.getDataDefinitions(product.getDeveloperId(), product.getId());

    DataIdValidator.checkForAdd(dataId, productType, productDataViews);

    LOG.debug("Exit. dataId not exist.");
  }
}
