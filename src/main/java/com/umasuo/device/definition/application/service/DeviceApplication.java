package com.umasuo.device.definition.application.service;

import com.umasuo.device.definition.application.dto.CopyRequest;
import com.umasuo.device.definition.application.dto.DeviceDraft;
import com.umasuo.device.definition.application.dto.DeviceView;
import com.umasuo.device.definition.application.dto.mapper.CommonFunctionMapper;
import com.umasuo.device.definition.application.dto.mapper.DeviceMapper;
import com.umasuo.device.definition.domain.model.Device;
import com.umasuo.device.definition.domain.model.DeviceFunction;
import com.umasuo.device.definition.domain.model.ProductType;
import com.umasuo.device.definition.domain.service.DeviceService;
import com.umasuo.device.definition.domain.service.ProductTypeService;
import com.umasuo.device.definition.infrastructure.update.UpdateAction;
import com.umasuo.device.definition.infrastructure.update.UpdaterService;
import com.umasuo.device.definition.infrastructure.validator.DeviceValidator;
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
public class DeviceApplication {

  /**
   * Logger.
   */
  private final static Logger logger = LoggerFactory.getLogger(DeviceApplication.class);

  /**
   * DeviceService.
   */
  @Autowired
  private transient DeviceService deviceService;

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
  public DeviceView create(DeviceDraft draft, String developerId) {
    logger.debug("Enter. developerId: {}, draft: {}.", developerId, draft);

    // 1. 检查名字是否重复
    if (deviceService.isExistName(developerId, draft.getName())) {
      logger.debug("Device name: {} has existed in developer: {}.", draft.getName(), developerId);
      throw new AlreadyExistException("Device name has existed");
    }

    // 2. 检查类型是否存在
    ProductType productType = productTypeService.getById(draft.getProductTypeId());
    DeviceValidator.validateProductType(draft, productType);

    // 生成实体对象
    Device device = DeviceMapper.viewToModel(draft, developerId);

    // 3. 拷贝功能, 同时检查功能是否属于该类型的（在创建阶段不允许添加新的功能和数据，只能在新建之后添加）
    if (draft.getFunctionIds() != null && !draft.getFunctionIds().isEmpty()) {
      copyFunctions(draft, productType, device);
    }

    //TODO 检查数据是否存在

    device = deviceService.save(device);

    // 4. 调用数据服务拷贝数据, 检测数据是否属于该类型的, 如果有event bus，可以把这个工作交给event bus
    if (draft.getDataDefineIds() != null && !draft.getDataDefineIds().isEmpty()) {
      copyDataDefinitions(draft, developerId, productType, device);
      deviceService.save(device);
    }

    cacheApplication.deleteDeveloperProducts(developerId);

    DeviceView view = DeviceMapper.modelToView(device);

    logger.debug("Exit. deviceView: {}.", view);
    return view;
  }

  /**
   * get device by id.
   *
   * @param id String
   */
  public DeviceView get(String id, String developerId) {
    logger.debug("Enter. id: {}, developerId: {}.", id, developerId);

    DeviceView result = cacheApplication.getProductById(developerId, id);

    if (result == null) {
      logger.debug("Cache fail, get from database.");

      List<Device> products = deviceService.getByDeveloperId(developerId);
      List<DeviceView> productViews = DeviceMapper.modelToView(products);
      cacheApplication.cacheProduct(developerId, productViews);

      result = productViews.stream().filter(view -> id.equals(view.getId())).findAny().orElse(null);
    }

    logger.debug("Exit. deviceView: {}.", result);
    return result;
  }

  /**
   * get all device define by developer id.
   *
   * @param developerId developer id
   * @return list of device view
   */
  public List<DeviceView> getAllByDeveloperId(String developerId) {
    logger.debug("Enter. developerId: {}.", developerId);

    List<DeviceView> result = cacheApplication.getDeveloperProduct(developerId);
    if (result.isEmpty()) {
      logger.debug("Cache fail, get from database.");
      List<Device> devices = deviceService.getByDeveloperId(developerId);
      result = DeviceMapper.modelToView(devices);
      cacheApplication.cacheProduct(developerId, result);
    }

    logger.trace("Devices: {}.", result);
    logger.debug("Exit. devicesSize: {}.", result.size());
    return result;
  }

  /**
   * estClient
   * get all open device define by developer id.
   * 接口比较少用，暂时不需要使用缓存。
   *
   * @param id developer id
   * @return list of device view
   */
  public List<DeviceView> getAllOpenDevice(String id) {
    logger.debug("Enter. developerId: {}.", id);

    List<Device> devices = deviceService.getAllOpenDevice(id);
    List<DeviceView> views = DeviceMapper.modelToView(devices);

    logger.debug("Exit. devicesSize: {}.", views.size());
    logger.trace("Devices: {}.", views);
    return views;
  }

  /**
   * update device with actions.
   */
  public DeviceView update(String id, String developerId, Integer version, List<UpdateAction>
      actions) {
    logger.debug("Enter: id: {}, version: {}, actions: {}", id, version, actions);

    Device valueInDb = deviceService.get(id);

    DeviceValidator.checkDeveloper(developerId, valueInDb);

    logger.debug("Data in db: {}", valueInDb);

    DeviceValidator.checkStatus(valueInDb);

    DeviceValidator.checkVersion(version, valueInDb.getVersion());

    actions.stream().forEach(action -> updaterService.handle(valueInDb, action));

    Device savedDevice = deviceService.save(valueInDb);

    cacheApplication.deleteDeveloperProducts(developerId);

    DeviceView updatedDevice = DeviceMapper.modelToView(savedDevice);

    logger.debug("Exit: updated device: {}", updatedDevice);
    return updatedDevice;
  }

  /**
   * 把产品类别中定义的数据定义拷贝到新增的设备定义中。
   */
  private void copyDataDefinitions(DeviceDraft draft, String developerId, ProductType productType,
      Device device) {
    DeviceValidator.validateDataDefinition(draft.getDataDefineIds(), productType);

    CopyRequest copyRequest = CopyRequest.build(device.getId(), draft.getDataDefineIds(), null);

    // 调用Data-Definition的API，接收的是拷贝之后生成的id列表，这一步失败，不影响设备创建是否成功
    List<String> newDataDefinitionIds = restClient.copyDataDefinitions(developerId, copyRequest);

    device.setDataDefineIds(newDataDefinitionIds);
  }

  /**
   * 把产品类别中定义的功能拷贝到新增的设备定义中。
   */
  private void copyFunctions(DeviceDraft draft, ProductType productType, Device device) {
    DeviceValidator.validateFunction(draft.getFunctionIds(), productType);
    List<DeviceFunction> functions = CommonFunctionMapper.copy(productType.getFunctions());
    device.setDeviceFunctions(functions);
  }

  public void delete(String id, String developerId, Integer version) {
    logger.debug("Enter. id: {}, developerId: {}, version: {}.", id, developerId, version);

    Device valueInDb = deviceService.get(id);

    DeviceValidator.checkDeveloper(developerId, valueInDb);

    logger.debug("Data in db: {}", valueInDb);

    DeviceValidator.checkStatus(valueInDb);

    DeviceValidator.checkVersion(version, valueInDb.getVersion());

    deviceService.delete(id);

    logger.debug("Exit.");
  }

}
