package com.umasuo.device.definition.application.service;

import com.umasuo.device.definition.application.dto.DeviceDraft;
import com.umasuo.device.definition.application.dto.DeviceView;
import com.umasuo.device.definition.application.dto.mapper.DeviceMapper;
import com.umasuo.device.definition.domain.model.Device;
import com.umasuo.device.definition.domain.service.DeviceService;
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

  /**
   * create new device view.
   *
   * @param draft device draft
   * @return device view
   */
  public DeviceView create(DeviceDraft draft) {
    logger.debug("Enter. draft: {}.", draft);

    Device device = DeviceMapper.viewToModel(draft);
    Device deviceCreated = deviceService.create(device);

    DeviceView view = DeviceMapper.modelToView(device);

    logger.debug("Exit. deviceCreatedView: {}.", view);
    return view;
  }

  /**
   * get device by id.
   *
   * @param id String
   * @return
   */
  public DeviceView get(String id) {
    logger.debug("Enter. id: {}.", id);

    Device device = deviceService.get(id);
    DeviceView view = DeviceMapper.modelToView(device);

    logger.debug("Exit. device: {}.", view);
    return view;
  }

  /**
   * get all device define by developer id.
   *
   * @param id developer id
   * @return list of device view
   */
  public List<DeviceView> getAllByDeveloperId(String id) {
    logger.debug("Enter. developerId: {}.", id);

    List<Device> devices = deviceService.getByDeveloperId(id);
    List<DeviceView> views = DeviceMapper.modelToView(devices);

    logger.debug("Exit. devicesSize: {}.", views.size());
    logger.trace("Devices: {}.", views);
    return views;
  }
}
