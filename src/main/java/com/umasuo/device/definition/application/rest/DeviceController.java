package com.umasuo.device.definition.application.rest;

import static com.umasuo.device.definition.infrastructure.Router.BUILTIN_DEVICE_TYPE;

import com.umasuo.device.definition.application.dto.DataDefinitionView;
import com.umasuo.device.definition.application.dto.DeviceDraft;
import com.umasuo.device.definition.application.dto.DeviceTypeView;
import com.umasuo.device.definition.application.dto.DeviceView;
import com.umasuo.device.definition.application.dto.FunctionDefinitionView;
import com.umasuo.device.definition.application.service.DeviceApplication;
import com.umasuo.device.definition.infrastructure.Router;
import com.umasuo.device.definition.infrastructure.update.UpdateRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import javax.validation.Valid;

/**
 * Created by umasuo on 17/6/1.
 */
@RestController
@CrossOrigin
public class DeviceController {

  private final static Logger logger = LoggerFactory.getLogger(DeviceController.class);

  @Autowired
  private transient DeviceApplication deviceApplication;

  /**
   * create new device.
   *
   * @param draft Device draft
   * @param developerId the developer id
   * @return device view
   */
  @PostMapping(Router.DEVICE_DEFINITION_ROOT)
  public DeviceView create(@RequestBody @Valid DeviceDraft draft, @RequestHeader String
      developerId) {
    logger.info("Enter. deviceDraft: {}.", draft);

    DeviceView view = deviceApplication.create(draft, developerId);

    logger.info("Exit. deviceView: {}.", view);
    return view;
  }

  /**
   * Update Device view.
   *
   * @param id the Device id
   * @param updateRequest the update request
   * @param developerId the developer id
   * @return the DeviceView
   */
  @PutMapping(Router.DEVICE_DEFINITION_WITH_ID)
  public DeviceView update(@PathVariable("id") String id,
      @RequestBody @Valid UpdateRequest updateRequest,
      @RequestHeader String developerId) {
    logger.info("Enter. deviceId: {}, updateRequest: {}, developerId: {}.", id, updateRequest,
        developerId);

    DeviceView result =
        deviceApplication.update(id, developerId, updateRequest.getVersion(), updateRequest
            .getActions());

    logger.trace("Updated device: {}.", result);
    logger.info("Exit.");
    return result;
  }

  /**
   * get device definition by device id.
   *
   * @param id String
   * @param developerId the developer id
   * @return DeviceView device view
   */
  @GetMapping(Router.DEVICE_DEFINITION_WITH_ID)
  public DeviceView get(@PathVariable String id, @RequestHeader String developerId) {
    logger.info("Enter. id: {}.", id);

    DeviceView view = deviceApplication.get(id, developerId);

    logger.info("Exit. view: {}.", view);
    return view;
  }

  /**
   * get all developer's device definition by developer id.
   *
   * @param developerId String
   * @return list of device view
   */
  @GetMapping(Router.DEVICE_DEFINITION_ROOT)
  public List<DeviceView> getByDeveloperId(@RequestHeader String developerId) {
    logger.info("Enter. developerId: {}.", developerId);

    List<DeviceView> views = deviceApplication.getAllByDeveloperId(developerId);

    logger.info("Exit. viewsSize: {}.", views.size());
    logger.trace("views: {}.", views);
    return views;
  }

  /**
   * Gets all open device.
   *
   * @param developerId the developer id
   * @return the all open device
   */
  @GetMapping(Router.OPEN_DEVICE_DEFINITION)
  public List<DeviceView> getAllOpenDevice(@RequestParam String developerId) {
    logger.info("Enter. developerId: {}.", developerId);

    List<DeviceView> views = deviceApplication.getAllOpenDevice(developerId);

    logger.info("Exit. viewsSize: {}.", views.size());
    logger.trace("views: {}.", views);
    return views;
  }

  /**
   * 获取预设的设备类型.
   *
   * @param developerId the developer id
   * @return build in device type
   */
  @GetMapping(BUILTIN_DEVICE_TYPE)
  public List<DeviceTypeView> getBuiltInDeviceType(
      @RequestHeader("developerId") String developerId) {
    // TODO: 17/6/27 获取预设的设备类型
    return null;
  }

  public List<DataDefinitionView> getBuiltinDataDefinition(
      @RequestHeader("developerId") String developerId,
      @RequestParam String productTypeId) {
    // TODO: 17/6/27 根据设备类型获取数据定义
    return null;
  }

  public List<FunctionDefinitionView> getBuiltinFunctionDefinition(
      @RequestHeader("developerId") String developerId,
      @RequestParam String productTypeId) {
    // TODO: 17/6/27 根据设备类型获取功能定义
    return null;
  }
}