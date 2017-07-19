package com.umasuo.product.application.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.umasuo.exception.AlreadyExistException;
import com.umasuo.product.application.dto.CopyRequest;
import com.umasuo.product.application.dto.ProductDataView;
import com.umasuo.product.application.dto.ProductDraft;
import com.umasuo.product.application.dto.ProductView;
import com.umasuo.product.application.dto.mapper.CommonFunctionMapper;
import com.umasuo.product.application.dto.mapper.ProductMapper;
import com.umasuo.product.domain.model.Product;
import com.umasuo.product.domain.model.ProductFunction;
import com.umasuo.product.domain.model.ProductType;
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
    if (productService.isExistName(developerId, draft.getName())) {
      logger.debug("Product name: {} has existed in developer: {}.", draft.getName(), developerId);
      throw new AlreadyExistException("Product name has existed");
    }

    // 2. 检查类型是否存在
    ProductType productType = productTypeService.getById(draft.getProductTypeId());
    ProductValidator.validateProductType(draft, productType);

    // 生成实体对象
    Product product = ProductMapper.viewToModel(draft, developerId);

    // 3. 拷贝功能, 同时检查功能是否属于该类型的（在创建阶段不允许添加新的功能和数据，只能在新建之后添加）
    if (draft.getFunctionIds() != null && !draft.getFunctionIds().isEmpty()) {
      copyFunctions(draft, productType, product);
    }

    product = productService.save(product);

    // 4. 调用数据服务拷贝数据, 检测数据是否属于该类型的, 如果有event bus，可以把这个工作交给event bus
    if (draft.getDataDefineIds() != null && !draft.getDataDefineIds().isEmpty()) {
      copyDataDefinitions(draft, developerId, productType, product);
      productService.save(product);
    }

    cacheApplication.deleteProducts(developerId);

    ProductView view = ProductMapper.modelToView(product);

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

    ProductView updatedProduct = ProductMapper.modelToView(product);

    updatedProduct.setDataDefinitions(ProductDataView.build(productDataViews));

    updatedProduct.getDataDefinitions().stream().forEach(
        data -> data.setDataSchema(JsonUtils.deserialize(data.getSchema(), JsonNode.class))
    );

    logger.debug("Exit: updated product: {}", updatedProduct);
    return updatedProduct;
  }

  /**
   * 把产品类别中定义的数据定义拷贝到新增的设备定义中。
   */
  private void copyDataDefinitions(ProductDraft draft, String developerId, ProductType productType,
      Product product) {
    ProductValidator.validateDataDefinition(draft.getDataDefineIds(), productType);

    CopyRequest copyRequest = CopyRequest.build(product.getId(), draft.getDataDefineIds(), null);

    // 调用Data-Definition的API，接收的是拷贝之后生成的id列表，这一步失败，不影响设备创建是否成功
    List<String> newDataDefinitionIds = restClient.copyDataDefinitions(developerId, copyRequest);

    product.setDataDefineIds(newDataDefinitionIds);
  }

  /**
   * 把产品类别中定义的功能拷贝到新增的设备定义中。
   */
  private void copyFunctions(ProductDraft draft, ProductType productType, Product product) {
    ProductValidator.validateFunction(draft.getFunctionIds(), productType);
    List<ProductFunction> functions = CommonFunctionMapper.copy(productType.getFunctions());
    product.setProductFunctions(functions);
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
