package com.umasuo.product.application.rest;

import com.umasuo.product.application.service.RequestApplication;
import com.umasuo.product.infrastructure.Router;
import com.umasuo.product.infrastructure.enums.RequestStatus;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller class for Request.
 */
@CrossOrigin
@RestController
public class RequestController {

  /**
   * Logger.
   */
  private static final Logger LOG = LoggerFactory.getLogger(RequestController.class);

  /**
   * The RequestApplication.
   */
  @Autowired
  private transient RequestApplication requestApplication;


  /**
   * Update status.
   *
   * @param id the id
   * @param status the status
   */
  @PutMapping(Router.PRODUCT_REQUEST_WITH_ID)
  public void updateStatus(@PathVariable("id") String id, @RequestBody RequestStatus status) {
    LOG.info("Enter. id: {}, status: {}.", id, status);

    requestApplication.replyRequest(id, status);

    LOG.debug("Exit.");
  }
}
