package com.umasuo.device.definition.application.rest;

import static com.umasuo.device.definition.infrastructure.Router.PRODUCT_TYPE_ROOT;

import com.umasuo.device.definition.application.dto.ProductTypeView;
import com.umasuo.device.definition.application.service.ProductTypeApplication;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
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
  @GetMapping(PRODUCT_TYPE_ROOT)
  public List<ProductTypeView> getAll(@RequestHeader("developerId") String developerId) {
    LOG.info("Enter. developerId: {}.", developerId);

    List<ProductTypeView> result = productTypeApplication.getAll();

    LOG.info("Exit. productType size: {}.", result.size());

    return result;
  }
}
