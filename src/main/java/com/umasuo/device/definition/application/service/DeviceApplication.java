package com.umasuo.device.definition.application.service;

import com.umasuo.device.definition.application.dto.DeviceDraft;
import com.umasuo.device.definition.application.dto.DeviceView;
import com.umasuo.device.definition.application.dto.mapper.DeviceMapper;
import com.umasuo.device.definition.domain.model.Device;
import com.umasuo.device.definition.domain.service.DeviceService;
import com.umasuo.device.definition.infrastructure.enums.DeviceStatus;
import com.umasuo.device.definition.infrastructure.update.UpdateAction;
import com.umasuo.device.definition.infrastructure.update.UpdaterService;
import com.umasuo.exception.AlreadyExistException;
import com.umasuo.exception.ConflictException;
import com.umasuo.exception.ParametersException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by umasuo on 17/6/1.
 */
@Service
public class DeviceApplication {

  private final static Logger logger = LoggerFactory.getLogger(DeviceApplication.class);

  @Autowired
  private transient DeviceService deviceService;

  @Autowired
  private transient UpdaterService updaterService;

  /**
   * The DataDefinitionValidator.
   */
  @Autowired
  private transient DataDefinitionValidator dataDefinitionValidator;

  /**
   * save new device view.
   *
   * @param draft device draft
   * @return device view
   */
  public DeviceView create(DeviceDraft draft, String developerId) {
    logger.debug("Enter. draft: {}.", draft);

    if (draft.getDataDefineIds() != null && !draft.getDataDefineIds().isEmpty()) {
      dataDefinitionValidator.checkDataDefinitionExist(developerId, draft.getDataDefineIds());
    }

    if (deviceService.isExistName(developerId, draft.getName())) {
      logger.debug("Device name: {} has existed in developer: {}.", draft.getName(), developerId);
      throw new AlreadyExistException("Device name has existed");
    }

    Device device = DeviceMapper.viewToModel(draft, developerId);
    device.setStatus(DeviceStatus.UNPUBLISHED);
    Device deviceCreated = deviceService.save(device);

    DeviceView view = DeviceMapper.modelToView(deviceCreated);

    logger.debug("Exit. deviceCreatedView: {}.", view);
    return view;
  }

  /**
   * get device by id.
   *
   * @param id String
   */
  public DeviceView get(String id, String developerId) {
    logger.debug("Enter. id: {}, developerId: {}.", id, developerId);

    Device device = deviceService.get(id);
    if (!device.getDeveloperId().equals(developerId)) {
      logger.debug("Device: {} not belong to developer: {}.", id, developerId);
      throw new ParametersException("Device not belong to developer: " + developerId + ", " +
          "deviceId: " + id);
    }
    DeviceView view = DeviceMapper.modelToView(device);

    logger.debug("Exit. device: {}.", view);
    return view;
  }

  /**
   * get all device define by developer id.
   *
   * @param developerId developer id
   * @return list of device view
   */
  public List<DeviceView> getAllByDeveloperId(String developerId) {
    logger.debug("Enter. developerId: {}.", developerId);

    List<Device> devices = deviceService.getByDeveloperId(developerId);
    List<DeviceView> views = DeviceMapper.modelToView(devices);

    logger.debug("Exit. devicesSize: {}.", views.size());
    logger.trace("Devices: {}.", views);
    return views;
  }

  /**
   * get all open device define by developer id.
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
    if (!valueInDb.getDeveloperId().equals(developerId)) {
      logger.debug("Device: {} not belong to developer: {}.", id, developerId);
      throw new ParametersException("The device not belong to the developer: " + developerId + "," +
          " deviceId: " + id);
    }
    logger.debug("Data in db: {}", valueInDb);
    checkVersion(version, valueInDb.getVersion());

    actions.stream().forEach(action -> updaterService.handle(valueInDb, action));

    Device savedDevice = deviceService.save(valueInDb);
    DeviceView updatedDevice = DeviceMapper.modelToView(savedDevice);

    logger.debug("Exit: updated device: {}", updatedDevice);
    return updatedDevice;
  }

  /**
   * check the version.
   *
   * @param inputVersion Integer
   * @param existVersion Integer
   */
  private void checkVersion(Integer inputVersion, Integer existVersion) {
    if (!inputVersion.equals(existVersion)) {
      logger.debug("Device definition version is not correct.");
      throw new ConflictException("Device definition version is not correct.");
    }
  }
}
