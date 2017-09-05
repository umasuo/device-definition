package com.umasuo.product.application.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.umasuo.product.application.dto.ProductDataView;
import com.umasuo.product.application.dto.ProductDraft;
import com.umasuo.product.application.dto.ProductView;
import com.umasuo.product.application.dto.mapper.ProductMapper;
import com.umasuo.product.domain.model.Product;
import com.umasuo.product.domain.model.ProductType;
import com.umasuo.product.domain.service.ProductService;
import com.umasuo.product.domain.service.ProductTypeService;
import com.umasuo.product.infrastructure.enums.RequestStatus;
import com.umasuo.product.infrastructure.enums.ProductStatus;
import com.umasuo.product.infrastructure.update.UpdateAction;
import com.umasuo.product.infrastructure.update.UpdaterService;
import com.umasuo.product.infrastructure.validator.ProductValidator;
import com.umasuo.product.infrastructure.validator.VersionValidator;
import com.umasuo.util.JsonUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 用于增删改Product.
 */
@Service
public class ProductCommandApplication {

  /**
   * Logger.
   */
  private final static Logger LOG = LoggerFactory.getLogger(ProductCommandApplication.class);

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

  /**
   * CacheApplication.
   */
  @Autowired
  private transient CacheApplication cacheApplication;

  /**
   * Create Product.
   *
   * @param draft product draft
   * @param developerId the developer id
   * @return product view
   */
  @Transactional
  public ProductView create(ProductDraft draft, String developerId) {
    LOG.debug("Enter. developerId: {}, draft: {}.", developerId, draft);

    // 1. 检查名字是否重复
    productService.isExistName(developerId, draft.getName());

    // 2. 获取产品类型，并检测其是否存在
    ProductType productType = productTypeService.getById(draft.getProductTypeId());
    productTypeService.exists(draft.getProductTypeId());

    // 3. 生成实体对象
    Product product = ProductMapper.toModel(draft, developerId, productType.getIcon());

    product = productService.save(product);

    cacheApplication.deleteProducts(developerId);

    ProductView view = ProductMapper.toView(product);

    LOG.debug("Exit. productView: {}.", view);
    return view;
  }

  /**
   * 删除Product。
   *
   * @param id product id
   * @param developerId the developer id
   * @param version the version
   */
  public void delete(String id, String developerId, Integer version) {
    LOG.debug("Enter. id: {}, developerId: {}, version: {}.", id, developerId, version);

    Product product = productService.get(id);

    checkForUpdateAndDelete(developerId, product, version);

    productService.delete(id);

    cacheApplication.deleteProducts(developerId);

    restClient.deleteAllDataDefinition(developerId, product.getId());

    LOG.debug("Exit.");
  }

  /**
   * Update product with actions.
   *
   * @param id the id
   * @param developerId the developer id
   * @param version the version
   * @param actions the actions
   * @return the product view
   */
  public ProductView update(String id, String developerId, Integer version, List<UpdateAction>
      actions) {
    LOG.debug("Enter: id: {}, version: {}, actions: {}.", id, version, actions);

    Product valueInDb = productService.get(id);

    checkForUpdateAndDelete(developerId, valueInDb, version);

    actions.stream().forEach(action -> updaterService.handle(valueInDb, action));

    Product product = productService.save(valueInDb);

    cacheApplication.deleteProducts(developerId);

    List<ProductDataView> productDataViews = restClient.getProductData(developerId, id);

    ProductView updatedProduct = ProductMapper.toView(product);

    updatedProduct.setDataDefinitions(productDataViews);

    updatedProduct.getDataDefinitions().stream().forEach(
        data -> data.setDataSchema(JsonUtils.deserialize(data.getSchema(), JsonNode.class))
    );

    LOG.debug("Exit: updated product: {}", updatedProduct);
    return updatedProduct;
  }

  /**
   * 根据admin的批复更新product的状态。
   *
   * @param productId the productId
   * @param status the status
   */
  public void updateStatusByResponse(String productId, RequestStatus status) {
    LOG.debug("Enter. productId: {}, status: {}.", productId, status);

    if (RequestStatus.CREATED.equals(status)) {
      LOG.debug("Admin have not handle the application about product: {}.", productId);
      return;
    }

    Product product = productService.get(productId);

    if (RequestStatus.AGREE.equals(status)) {
      product.setStatus(ProductStatus.PUBLISHED);
    } else if (RequestStatus.DISAGREE.equals(status)) {
      product.setStatus(ProductStatus.DEVELOPING);
    }

    productService.save(product);

    cacheApplication.deleteProducts(product.getDeveloperId());

    LOG.debug("Exit.");
  }

  /**
   * 在update和delete中，需要检查developer是否一致，version是否一致，status是否合法。
   */
  private void checkForUpdateAndDelete(String developerId, Product product, Integer version) {
    ProductValidator.checkDeveloper(developerId, product);

    ProductValidator.checkStatus(product);

    VersionValidator.checkVersion(version, product.getVersion());
  }
}
