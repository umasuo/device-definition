package com.umasuo.product.application.rest;

import com.umasuo.product.application.dto.ProductStatusRequest;
import com.umasuo.product.application.service.RequestApplication;
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
 * Controller class for Product status.
 */
@CrossOrigin
@RestController
public class ProductStatusController {

  /**
   * Logger.
   */
  private static final Logger LOG = LoggerFactory.getLogger(ProductStatusController.class);

  /**
   * Request application, to handle the request.
   */
  @Autowired
  private transient RequestApplication requestApplication;

  /**
   * 开发者申请修改产品状态.
   *
   * @param productId the product id
   * @param developerId the developer id
   * @param request the request
   */
  @PutMapping(Router.PRODUCT_STATUS)
  public void request(@PathVariable("id") String productId, @RequestHeader String developerId,
      @RequestBody @Valid ProductStatusRequest request) {
    LOG.info("Enter. developerId: {}, productId: {}, request: {}.", developerId, productId,
        request);

    requestApplication.handleRequest(developerId, productId, request);

    LOG.info("Exit.");
  }
}
