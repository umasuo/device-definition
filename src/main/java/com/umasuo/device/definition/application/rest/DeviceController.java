package com.umasuo.device.definition.application.rest;

import com.umasuo.device.definition.application.dto.DeviceDraft;
import com.umasuo.device.definition.application.dto.DeviceView;
import com.umasuo.device.definition.application.service.DeviceApplication;
import com.umasuo.device.definition.infrastructure.Router;
import com.umasuo.device.definition.infrastructure.update.UpdateRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
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
@CrossOrigin
@RestController
public class DeviceController {

  /**
   * Logger.
   */
  private final static Logger logger = LoggerFactory.getLogger(DeviceController.class);

  /**
   * DeviceApplication.
   */
  @Autowired
  private transient DeviceApplication deviceApplication;

  /**
   * create new device.
   *
   * @param draft Device draft
   * @param developerId the developer id
   * @return device view
   */
  @PostMapping(Router.PRODUCT_ROOT)
  public DeviceView create(@RequestHeader("developerId") String developerId,
      @RequestBody @Valid DeviceDraft draft) {
    logger.info("Enter. developerId: {}, deviceDraft: {}.", developerId, draft);

    DeviceView view = deviceApplication.create(draft, developerId);

    logger.info("Exit. deviceView: {}.", view);

    return view;
  }

  @DeleteMapping(Router.PRODUCT_WITH_ID)
  public void delete(@PathVariable("id") String id, @RequestHeader String developerId,
      @RequestParam("version") Integer version) {
    logger.info("Enter. id: {}, developerId: {}, version: {}.", id, developerId, version);

    deviceApplication.delete(id, developerId, version);

    logger.info("Exit.");
  }

  /**
   * Update Device view.
   *
   * @param id the Device id
   * @param updateRequest the update request
   * @param developerId the developer id
   * @return the DeviceView
   */
  @PutMapping(Router.PRODUCT_WITH_ID)
  public DeviceView update(@PathVariable("id") String id, @RequestHeader String developerId,
      @RequestBody @Valid UpdateRequest updateRequest) {
    logger.info("Enter. deviceId: {}, updateRequest: {}, developerId: {}.",
        id, updateRequest, developerId);

    DeviceView result = deviceApplication
        .update(id, developerId, updateRequest.getVersion(), updateRequest.getActions());

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
  @GetMapping(Router.PRODUCT_WITH_ID)
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
  @GetMapping(Router.PRODUCT_ROOT)
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

  @GetMapping(value = Router.PRODUCT_ROOT, params = {"isOpen", "developerId"})
  public List<DeviceView> getAllOpenDevice(@RequestParam String developerId, @RequestParam
      Boolean isOpen) {
    logger.info("Enter. developerId: {}.", developerId);
    //todo
    List<DeviceView> views = deviceApplication.getAllOpenDevice(developerId);

    logger.info("Exit. viewsSize: {}.", views.size());
    logger.trace("views: {}.", views);

    return views;
  }
}
