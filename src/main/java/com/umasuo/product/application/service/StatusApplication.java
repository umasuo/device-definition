package com.umasuo.product.application.service;

import com.umasuo.product.application.dto.ProductStatusRequest;
import com.umasuo.product.domain.model.Product;
import com.umasuo.product.domain.service.ProductService;
import com.umasuo.product.infrastructure.enums.ProductStatus;
import com.umasuo.product.infrastructure.validator.ProductValidator;
import com.umasuo.product.infrastructure.validator.VersionValidator;

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

    VersionValidator.checkVersion(request.getVersion(), product.getVersion());

    switch (request.getType()) {
      case PUBLISH:
        publish(developerId, productId, product);
        break;
      case CANCEL:
        cancel(developerId, productId, product);
        break;
      case REVOKE:
        revoke(product);
        break;
      default:
        break;
    }

    productService.save(product);

    cacheApplication.deleteProducts(developerId);

    LOG.debug("Exit.");
  }

  private void revoke(Product product) {
    LOG.debug("Enter. productId: {}.", product.getId());
    ProductValidator.validateStatus(ProductStatus.PUBLISHED, product.getStatus());

    product.setStatus(ProductStatus.REVOKED);

    LOG.debug("Exit.");
  }

  private void cancel(String developerId, String productId, Product product) {
    LOG.debug("Enter. developerId: {}, productId: {}.", developerId, productId);

    ProductValidator.validateStatus(ProductStatus.CHECKING, product.getStatus());

    product.setStatus(ProductStatus.DEVELOPING);

    requestApplication.cancelRequest(developerId, productId);

    LOG.debug("Exit.");
  }

  private void publish(String developerId, String productId, Product product) {
    LOG.debug("Enter. developerId: {}, productId: {}.", developerId, productId);

    ProductValidator.validateStatus(ProductStatus.DEVELOPING, product.getStatus());

    product.setStatus(ProductStatus.CHECKING);

    requestApplication.create(developerId, productId);

    LOG.debug("Exit.");
  }
}
