package com.umasuo.product.application.service;

import com.umasuo.product.application.dto.ProductStatusRequest;
import com.umasuo.product.domain.model.Product;
import com.umasuo.product.domain.service.ProductService;
import com.umasuo.product.infrastructure.enums.ProductStatus;
import com.umasuo.product.infrastructure.enums.RequestType;
import com.umasuo.product.infrastructure.validator.ProductValidator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Davis on 17/7/19.
 */
@Service
public class StatusApplication {

  /**
   * Logger.
   */
  private static final Logger LOG = LoggerFactory.getLogger(StatusApplication.class);

  @Autowired
  private transient ProductService productService;

  @Autowired
  private transient CacheApplication cacheApplication;

  @Autowired
  private transient RequestApplication requestApplication;

  /**
   * 开发者申请修改产品状态.
   */
  public void request(String developerId, String productId, ProductStatusRequest request) {
    LOG.debug("Enter. developerId: {}, productId: {}, request: {}.",
        developerId, productId, request);

    Product product = productService.get(productId);

    ProductValidator.checkDeveloper(developerId, product);

    ProductValidator.checkVersion(request.getVersion(), product.getVersion());

    if (RequestType.PUBLISH.equals(request.getType())) {
      // 申请published，则修改状态为checking， 新建一条request记录

      ProductValidator.checkStatusForPublish(product);

      product.setStatus(ProductStatus.CHECKING);

      requestApplication.create(developerId, productId);
    } else {
      // 申请为revoked，则直接修改状态为revoked，不需要平台处理，直接下架
      product.setStatus(ProductStatus.REVOKED);
    }

    productService.save(product);

    cacheApplication.deleteProducts(developerId);

    LOG.debug("Exit.");
  }
}
