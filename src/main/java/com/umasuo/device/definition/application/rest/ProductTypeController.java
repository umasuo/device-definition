package com.umasuo.device.definition.application.rest;

import static com.umasuo.device.definition.infrastructure.Router.PRODUCT_TYPE_ROOT;

import com.umasuo.device.definition.application.dto.ProductTypeView;
import com.umasuo.device.definition.domain.service.ProductTypeService;

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

  @Autowired
  private transient ProductTypeService productTypeService;

  /**
   * 新增产品类别接口，对平台维护者开放。
   *
   * @return
   */
  public ProductTypeView create() {
    // TODO: 17/6/28  
    return null;
  }

  /**
   * 删除产品类别，对平台维护者开放。
   * 如果现有产品使用该类别，则不能删除。
   */
  public void delete() {
    // TODO: 17/6/28
  }

  /**
   * 修改产品类别，对平台维护者开放。
   *
   * @return
   */
  public ProductTypeView update() {
    // TODO: 17/6/28
    return null;
  }

  @GetMapping(PRODUCT_TYPE_ROOT)
  public List<ProductTypeView> getAll() {
    // TODO: 17/6/28
    LOG.info("Enter.");

    List<ProductTypeView> result = productTypeService.getAll();

    LOG.info("Exit. productType size: {}.", result.size());

    return result;
  }
}
