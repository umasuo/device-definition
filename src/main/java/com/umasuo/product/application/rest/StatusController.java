package com.umasuo.product.application.rest;

import com.umasuo.product.application.dto.ProductStatusRequest;
import com.umasuo.product.application.service.StatusApplication;
import com.umasuo.product.infrastructure.Router;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * Created by Davis on 17/7/19.
 */
@CrossOrigin
@RestController
public class StatusController {

  /**
   * Logger.
   */
  private static final Logger LOG = LoggerFactory.getLogger(StatusController.class);

  /**
   * ProductCommandApplication.
   */
  @Autowired
  private transient StatusApplication statusApplication;

  @PutMapping(Router.PRODUCT_STATUS)
  public void request(@PathVariable("id") String id, @RequestHeader String developerId,
      @RequestBody @Valid ProductStatusRequest request) {
    LOG.info("Enter. developerId: {}, productId: {}, request: {}.", developerId, id, request);

    statusApplication.request(developerId, id, request);

    LOG.info("Exit.");
  }
}
