package com.umasuo.product.application.rest;

import com.umasuo.product.application.dto.ProductTypeView;
import com.umasuo.product.application.service.ProductTypeApplication;
import com.umasuo.product.infrastructure.Router;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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

  /**
   * 查询所有的产品类型，用于新建产品时选择类型和对应的功能，数据。
   * 开放接口，开发者可以访问。
   */
  @GetMapping(Router.PRODUCT_TYPE_ROOT)
  public List<ProductTypeView> getAll() {
    LOG.info("Enter.");

    List<ProductTypeView> result = productTypeApplication.getAll();

    LOG.info("Exit. productType size: {}.", result.size());

    return result;
  }
}
