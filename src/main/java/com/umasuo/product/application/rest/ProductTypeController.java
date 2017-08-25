package com.umasuo.product.application.rest;

import static com.umasuo.product.infrastructure.Router.ADMIN_PRODUCT_TYPE_ROOT;
import static com.umasuo.product.infrastructure.Router.ADMIN_PRODUCT_TYPE_WITH_ID;

import com.umasuo.product.application.dto.ProductTypeDraft;
import com.umasuo.product.application.dto.ProductTypeView;
import com.umasuo.product.application.dto.ProductView;
import com.umasuo.product.application.service.ProductTypeApplication;
import com.umasuo.product.infrastructure.Router;
import com.umasuo.product.infrastructure.update.UpdateRequest;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import javax.validation.Valid;

/**
 * Created by Davis on 17/6/28.
 */
@CrossOrigin
@RestController
public class ProductTypeController {

  /**
   * Logger.
   */
  private static final Logger LOG = LoggerFactory.getLogger(ProductTypeController.class);

  /**
   * ProductType application.
   */
  @Autowired
  private transient ProductTypeApplication productTypeApplication;

  @PostMapping(ADMIN_PRODUCT_TYPE_ROOT)
  public ProductTypeView create(@RequestBody ProductTypeDraft productTypeDraft) {
    LOG.info("Enter. productTypeDraft: {}.", productTypeDraft);

    ProductTypeView result = productTypeApplication.create(productTypeDraft);

    LOG.trace("new productType: {}.", result);
    LOG.info("Exit.");
    return result;
  }

  @DeleteMapping(ADMIN_PRODUCT_TYPE_WITH_ID)
  public void delete(@PathVariable("id") String id, @RequestParam("version") Integer version) {
    LOG.info("Enter. product type id: {}, version: {}.", id, version);

    productTypeApplication.delete(id, version);

    LOG.info("Exit.");
  }

  @PutMapping(ADMIN_PRODUCT_TYPE_WITH_ID)
  public ProductTypeView update(@PathVariable("id") String id,
      @RequestBody @Valid UpdateRequest updateRequest) {
    LOG.info("Enter. product type id: {}, updateRequest: {}.", id, updateRequest);

    ProductTypeView result =
        productTypeApplication.update(id, updateRequest.getVersion(), updateRequest.getActions());

    LOG.trace("Updated productType: {}.", result);
    LOG.info("Exit.");

    return result;
  }

  @GetMapping(ADMIN_PRODUCT_TYPE_WITH_ID)
  public ProductTypeView getOne(@PathVariable("id") String id) {
    LOG.info("Enter. product type id: {}.", id);

    ProductTypeView result = productTypeApplication.get(id);

    LOG.trace("ProductType: {}.", result);
    LOG.info("Exit.");

    return result;
  }

  /**
   * 查询所有的产品类型，用于新建产品时选择类型和对应的功能，数据。
   * 开放接口，开发者可以访问。
   */
  @GetMapping(value = { Router.PRODUCT_TYPE_ROOT, Router.ADMIN_PRODUCT_TYPE_ROOT})
  public List<ProductTypeView> getAll() {
    LOG.info("Enter.");

    List<ProductTypeView> result = productTypeApplication.getAll();

    LOG.info("Exit. productType size: {}.", result.size());

    return result;
  }
}
