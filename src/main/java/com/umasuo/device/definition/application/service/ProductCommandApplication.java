package com.umasuo.device.definition.application.service;

import com.umasuo.device.definition.application.dto.CopyRequest;
import com.umasuo.device.definition.application.dto.ProductDataView;
import com.umasuo.device.definition.application.dto.ProductDraft;
import com.umasuo.device.definition.application.dto.ProductView;
import com.umasuo.device.definition.application.dto.action.UpdateStatus;
import com.umasuo.device.definition.application.dto.mapper.CommonFunctionMapper;
import com.umasuo.device.definition.application.dto.mapper.DeviceMapper;
import com.umasuo.device.definition.domain.model.DeviceFunction;
import com.umasuo.device.definition.domain.model.Product;
import com.umasuo.device.definition.domain.model.ProductType;
import com.umasuo.device.definition.domain.service.ProductService;
import com.umasuo.device.definition.domain.service.ProductTypeService;
import com.umasuo.device.definition.infrastructure.update.UpdateAction;
import com.umasuo.device.definition.infrastructure.update.UpdaterService;
import com.umasuo.device.definition.infrastructure.validator.ProductValidator;
import com.umasuo.exception.AlreadyExistException;

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
  private transient ProductService deviceService;

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
   * save new device view.
   *
   * @param draft device draft
   * @return device view
   */
  @Transactional
  public ProductView create(ProductDraft draft, String developerId) {
    logger.debug("Enter. developerId: {}, draft: {}.", developerId, draft);

    // 1. 检查名字是否重复
    if (deviceService.isExistName(developerId, draft.getName())) {
      logger.debug("Product name: {} has existed in developer: {}.", draft.getName(), developerId);
      throw new AlreadyExistException("Product name has existed");
    }

    // 2. 检查类型是否存在
    ProductType productType = productTypeService.getById(draft.getProductTypeId());
    ProductValidator.validateProductType(draft, productType);

    // 生成实体对象
    Product device = DeviceMapper.viewToModel(draft, developerId);

    // 3. 拷贝功能, 同时检查功能是否属于该类型的（在创建阶段不允许添加新的功能和数据，只能在新建之后添加）
    if (draft.getFunctionIds() != null && !draft.getFunctionIds().isEmpty()) {
      copyFunctions(draft, productType, device);
    }

    device = deviceService.save(device);

    // 4. 调用数据服务拷贝数据, 检测数据是否属于该类型的, 如果有event bus，可以把这个工作交给event bus
    if (draft.getDataDefineIds() != null && !draft.getDataDefineIds().isEmpty()) {
      copyDataDefinitions(draft, developerId, productType, device);
      deviceService.save(device);
    }

    cacheApplication.deleteProducts(developerId);

    ProductView view = DeviceMapper.modelToView(device);

    logger.debug("Exit. deviceView: {}.", view);
    return view;
  }

  /**
   * update device with actions.
   */
  public ProductView update(String id, String developerId, Integer version, List<UpdateAction>
      actions) {
    logger.debug("Enter: id: {}, version: {}, actions: {}", id, version, actions);

    Product valueInDb = deviceService.get(id);

    ProductValidator.checkDeveloper(developerId, valueInDb);

    logger.debug("Data in db: {}", valueInDb);

    ProductValidator.checkStatus(valueInDb);

    ProductValidator.checkVersion(version, valueInDb.getVersion());

    actions.stream().forEach(action -> updaterService.handle(valueInDb, action));

    Product savedDevice = deviceService.save(valueInDb);

    cacheApplication.deleteProducts(developerId);

    List<ProductDataView> productDataViews = restClient.getProductData(developerId, id);

    ProductView updatedDevice = DeviceMapper.modelToView(savedDevice);

    updatedDevice.setDataDefinitions(productDataViews);

    logger.debug("Exit: updated device: {}", updatedDevice);
    return updatedDevice;
  }

  /**
   * 把产品类别中定义的数据定义拷贝到新增的设备定义中。
   */
  private void copyDataDefinitions(ProductDraft draft, String developerId, ProductType productType,
      Product device) {
    ProductValidator.validateDataDefinition(draft.getDataDefineIds(), productType);

    CopyRequest copyRequest = CopyRequest.build(device.getId(), draft.getDataDefineIds(), null);

    // 调用Data-Definition的API，接收的是拷贝之后生成的id列表，这一步失败，不影响设备创建是否成功
    List<String> newDataDefinitionIds = restClient.copyDataDefinitions(developerId, copyRequest);

    device.setDataDefineIds(newDataDefinitionIds);
  }

  /**
   * 把产品类别中定义的功能拷贝到新增的设备定义中。
   */
  private void copyFunctions(ProductDraft draft, ProductType productType, Product device) {
    ProductValidator.validateFunction(draft.getFunctionIds(), productType);
    List<DeviceFunction> functions = CommonFunctionMapper.copy(productType.getFunctions());
    device.setDeviceFunctions(functions);
  }

  public void delete(String id, String developerId, Integer version) {
    logger.debug("Enter. id: {}, developerId: {}, version: {}.", id, developerId, version);

    Product product = deviceService.get(id);

    ProductValidator.checkDeveloper(developerId, product);

    logger.debug("Data in db: {}", product);

    ProductValidator.checkStatus(product);

    ProductValidator.checkVersion(version, product.getVersion());

    deviceService.delete(id);

    cacheApplication.deleteProducts(developerId);

    restClient.deleteAllDataDefinition(developerId, product.getId());

    logger.debug("Exit.");
  }

  public ProductView updateStatus(String developerId, String id, UpdateStatus request) {
    logger.debug("Enter. developerId: {}, productId: {}, status: {}.", developerId, id, request);

    Product product = deviceService.get(id);

    ProductValidator.checkVersion(request.getVersion(), product.getVersion());

    product.setStatus(request.getStatus());

    deviceService.save(product);

    ProductView result = DeviceMapper.modelToView(product);

    cacheApplication.deleteProducts(developerId);

    logger.trace("Updated product: {}.", result);
    logger.debug("Exit.");

    return result;
  }
}
