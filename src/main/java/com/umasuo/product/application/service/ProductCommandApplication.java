package com.umasuo.product.application.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.umasuo.product.application.dto.ProductDataView;
import com.umasuo.product.application.dto.ProductDraft;
import com.umasuo.product.application.dto.ProductView;
import com.umasuo.product.application.dto.mapper.ProductMapper;
import com.umasuo.product.domain.model.Product;
import com.umasuo.product.domain.service.ProductService;
import com.umasuo.product.domain.service.ProductTypeService;
import com.umasuo.product.infrastructure.update.UpdateAction;
import com.umasuo.product.infrastructure.update.UpdaterService;
import com.umasuo.product.infrastructure.util.JsonUtils;
import com.umasuo.product.infrastructure.validator.ProductValidator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by umasuo on 17/6/1.
 */
@Service
public class ProductCommandApplication {

  /**
   * Logger.
   */
  private final static Logger logger = LoggerFactory.getLogger(ProductCommandApplication.class);

  /**
   * ProductService.
   */
  @Autowired
  private transient ProductService productService;

  /**
   * UpdaterService.
   */
  @Autowired
  private transient UpdaterService updaterService;

  /**
   * ProductType service.
   */
  @Autowired
  private transient ProductTypeService productTypeService;

  /**
   * RestClient.
   */
  @Autowired
  private transient RestClient restClient;

  @Autowired
  private transient CacheApplication cacheApplication;

  /**
   * save new product view.
   *
   * @param draft product draft
   * @return product view
   */
  @Transactional
  public ProductView create(ProductDraft draft, String developerId) {
    logger.debug("Enter. developerId: {}, draft: {}.", developerId, draft);

    // 1. 检查名字是否重复
    productService.isExistName(developerId, draft.getName());

    // 2. 检查类型是否存在
    productTypeService.exists(draft.getProductTypeId());

    // 3. 生成实体对象
    Product product = ProductMapper.toModel(draft, developerId);

    product = productService.save(product);

    cacheApplication.deleteProducts(developerId);

    ProductView view = ProductMapper.toView(product);

    logger.debug("Exit. productView: {}.", view);
    return view;
  }

  /**
   * update product with actions.
   */
  public ProductView update(String id, String developerId, Integer version, List<UpdateAction>
      actions) {
    logger.debug("Enter: id: {}, version: {}, actions: {}", id, version, actions);

    Product valueInDb = productService.get(id);

    ProductValidator.checkDeveloper(developerId, valueInDb);

    logger.debug("Data in db: {}", valueInDb);

    ProductValidator.checkStatus(valueInDb);

    ProductValidator.checkVersion(version, valueInDb.getVersion());

    actions.stream().forEach(action -> updaterService.handle(valueInDb, action));

    Product product = productService.save(valueInDb);

    cacheApplication.deleteProducts(developerId);

    List<ProductDataView> productDataViews = restClient.getProductData(developerId, id);

    ProductView updatedProduct = ProductMapper.toView(product);

    updatedProduct.setDataDefinitions(productDataViews);

    updatedProduct.getDataDefinitions().stream().forEach(
        data -> data.setDataSchema(JsonUtils.deserialize(data.getSchema(), JsonNode.class))
    );

    logger.debug("Exit: updated product: {}", updatedProduct);
    return updatedProduct;
  }

  public void delete(String id, String developerId, Integer version) {
    logger.debug("Enter. id: {}, developerId: {}, version: {}.", id, developerId, version);

    Product product = productService.get(id);

    ProductValidator.checkDeveloper(developerId, product);

    logger.debug("Data in db: {}", product);

    ProductValidator.checkStatus(product);

    ProductValidator.checkVersion(version, product.getVersion());

    productService.delete(id);

    cacheApplication.deleteProducts(developerId);

    restClient.deleteAllDataDefinition(developerId, product.getId());

    logger.debug("Exit.");
  }
}
