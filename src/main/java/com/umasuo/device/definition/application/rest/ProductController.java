package com.umasuo.device.definition.application.rest;

import com.umasuo.device.definition.application.dto.ProductDraft;
import com.umasuo.device.definition.application.dto.ProductView;
import com.umasuo.device.definition.application.dto.action.UpdateStatus;
import com.umasuo.device.definition.application.service.ProductCommandApplication;
import com.umasuo.device.definition.application.service.ProductQueryApplication;
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
public class ProductController {

  /**
   * Logger.
   */
  private final static Logger logger = LoggerFactory.getLogger(ProductController.class);

  /**
   * ProductCommandApplication.
   */
  @Autowired
  private transient ProductCommandApplication commandApplication;

  @Autowired
  private transient ProductQueryApplication queryApplication;

  /**
   * create new device.
   *
   * @param draft Product draft
   * @param developerId the developer id
   * @return device view
   */
  @PostMapping(Router.PRODUCT_ROOT)
  public ProductView create(@RequestHeader("developerId") String developerId,
      @RequestBody @Valid ProductDraft draft) {
    logger.info("Enter. developerId: {}, deviceDraft: {}.", developerId, draft);

    ProductView view = commandApplication.create(draft, developerId);

    logger.info("Exit. deviceView: {}.", view);

    return view;
  }

  @DeleteMapping(Router.PRODUCT_WITH_ID)
  public void delete(@PathVariable("id") String id, @RequestHeader String developerId,
      @RequestParam("version") Integer version) {
    logger.info("Enter. id: {}, developerId: {}, version: {}.", id, developerId, version);

    commandApplication.delete(id, developerId, version);

    logger.info("Exit.");
  }

  /**
   * Update Product view.
   *
   * @param id the Product id
   * @param updateRequest the update request
   * @param developerId the developer id
   * @return the ProductView
   */
  @PutMapping(Router.PRODUCT_WITH_ID)
  public ProductView update(@PathVariable("id") String id, @RequestHeader String developerId,
      @RequestBody @Valid UpdateRequest updateRequest) {
    logger.info("Enter. deviceId: {}, updateRequest: {}, developerId: {}.",
        id, updateRequest, developerId);

    ProductView result = commandApplication
        .update(id, developerId, updateRequest.getVersion(), updateRequest.getActions());

    logger.trace("Updated device: {}.", result);
    logger.info("Exit.");

    return result;
  }

  @PutMapping(Router.PRODUCT_STATUS)
  public ProductView updateStatus(@PathVariable("id") String id, @RequestHeader String developerId,
      @RequestBody @Valid UpdateStatus status) {
    logger.info("Enter. developerId: {}, productId: {}, status: {}.", developerId, id, status);

    ProductView result = commandApplication.updateStatus(developerId, id, status);

    logger.trace("Updated product: {}.", result);
    logger.info("Exit.");

    return result;
  }

  /**
   * get device definition by device id.
   *
   * @param id String
   * @param developerId the developer id
   * @return ProductView device view
   */
  @GetMapping(Router.PRODUCT_WITH_ID)
  public ProductView get(@PathVariable String id, @RequestHeader String developerId) {
    logger.info("Enter. id: {}.", id);

    ProductView view = queryApplication.get(id, developerId);

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
  public List<ProductView> getByDeveloperId(@RequestHeader String developerId) {
    logger.info("Enter. developerId: {}.", developerId);

    List<ProductView> views = queryApplication.getAllByDeveloperId(developerId);

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
  public List<ProductView> getAllOpenDevice(@RequestParam String developerId, @RequestParam
      Boolean isOpen) {
    logger.info("Enter. developerId: {}.", developerId);
    //todo
    List<ProductView> views = queryApplication.getAllOpenDevice(developerId);

    logger.info("Exit. viewsSize: {}.", views.size());
    logger.trace("views: {}.", views);

    return views;
  }
}