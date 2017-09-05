package com.umasuo.product.application.service;

import com.umasuo.product.application.dto.ProductStatusRequest;
import com.umasuo.product.application.dto.RequestRecordView;
import com.umasuo.product.application.dto.mapper.RequestRecordMapper;
import com.umasuo.product.domain.model.Product;
import com.umasuo.product.domain.model.RequestRecord;
import com.umasuo.product.domain.service.ProductService;
import com.umasuo.product.domain.service.RequestRecordService;
import com.umasuo.product.infrastructure.enums.ProductStatus;
import com.umasuo.product.infrastructure.enums.RecordStatus;
import com.umasuo.product.infrastructure.enums.RequestStatus;
import com.umasuo.product.infrastructure.enums.RequestType;
import com.umasuo.product.infrastructure.validator.ProductValidator;
import com.umasuo.product.infrastructure.validator.VersionValidator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 给平台使用的，用于获取及处理开发者的请求。
 */
@Service
public class RequestApplication {

  /**
   * Logger.
   */
  private static final Logger LOG = LoggerFactory.getLogger(RequestApplication.class);

  /**
   * RequestRecordService.
   */
  @Autowired
  private transient RequestRecordService requestService;

  /**
   * ProductService.
   */
  @Autowired
  private transient ProductService productService;

  /**
   * CacheApplication.
   */
  @Autowired
  private transient CacheApplication cacheApplication;

  /**
   * The ProductCommandApplication.
   */
  @Autowired
  private transient ProductCommandApplication productCommandApplication;

  /**
   * 开发者提交申请。
   *
   * @param developerId the developer id
   * @param productId the product id
   * @param request the request
   */
  public void handleRequest(String developerId, String productId,
      ProductStatusRequest request) {
    LOG.debug("Enter. developerId: {}, productId: {}, request: {}.",
        developerId, productId, request);

    Product product = productService.get(productId);

    ProductValidator.checkDeveloper(developerId, product);

    VersionValidator.checkVersion(request.getVersion(), product.getVersion());

    switch (request.getType()) {
      case PUBLISH:
        publish(developerId, product);
        break;
      case CANCEL:
        cancel(developerId, product);
        break;
      case REVOKE:
        revoke(developerId, product);
        break;
      default:
        break;
    }

    productService.save(product);

    cacheApplication.deleteProducts(developerId);

    LOG.debug("Exit.");

  }

  /**
   * Admin审核开发者的请求.
   *
   * @param requestId the request id
   * @param recordStatus the status
   */
  public void replyRequest(String requestId, RecordStatus recordStatus,
      RequestStatus applicationStatus) {
    LOG.debug("Enter. requestId: {}, recordStatus: {}, requestStatus: {}.",
        requestId, recordStatus, applicationStatus);

    RequestRecord statusRequest = requestService.get(requestId);

    statusRequest.setRecordStatus(recordStatus);
    statusRequest.setRequestStatus(applicationStatus);

    requestService.save(statusRequest);

    productCommandApplication
        .updateStatusByResponse(statusRequest.getProductId(), applicationStatus);

    LOG.debug("Exit.");
  }

  /**
   * 获取所有的申请记录。
   *
   * @return Map<String, List<RequestRecordView>>
   */
  public Map<String, List<RequestRecordView>> getAllRequests() {
    LOG.debug("Enter.");

    List<RequestRecord> requestRecords = requestService.getAll();

    List<RequestRecordView> views = RequestRecordMapper.toView(requestRecords);

    Map<String, List<RequestRecordView>> result = views.stream().collect(Collectors.groupingBy(
        RequestRecordView::getProductId, Collectors.toCollection(ArrayList::new)));

    LOG.debug("Exit. product size: {}.", result.size());

    return result;
  }

  /**
   * 开发者申请发布产品。
   *
   * @param developerId the developerId
   * @param product the product
   */
  private void publish(String developerId, Product product) {
    LOG.debug("Enter. developerId: {}, productId: {}.", developerId, product.getId());

    ProductValidator.validateStatus(ProductStatus.DEVELOPING, product.getStatus());

    product.setStatus(ProductStatus.CHECKING);

    create(developerId, product.getId(), RequestType.PUBLISH);

    LOG.debug("Exit. publish done.");
  }

  /**
   * 开发者取消发布产品。
   *
   * @param developerId the developerId
   * @param product the product
   */
  private void cancel(String developerId, Product product) {
    LOG.debug("Enter. developerId: {}, productId: {}.", developerId, product.getId());

    ProductValidator.validateStatus(ProductStatus.CHECKING, product.getStatus());

    product.setStatus(ProductStatus.DEVELOPING);

    requestService.cancelRequest(developerId, product.getId());

    create(developerId, product.getId(), RequestType.CANCEL);

    LOG.debug("Exit. cancel done.");
  }

  /**
   * 开发者申请下架产品。
   *
   * @param product the Product
   */
  private void revoke(String developerId, Product product) {
    LOG.debug("Enter. productId: {}.", product.getId());
    ProductValidator.validateStatus(ProductStatus.PUBLISHED, product.getStatus());

    product.setStatus(ProductStatus.REVOKED);

    create(developerId, product.getId(), RequestType.REVOKE);

    LOG.debug("Exit. revoke done.");
  }

  /**
   * 新建一条请求记录.
   *
   * @param developerId the developer id
   * @param productId the product id
   * @return status request view
   */
  private RequestRecordView create(String developerId, String productId, RequestType requestType) {
    LOG.debug("Enter. developerId: {}, productId: {}.", developerId, productId);

    RequestRecord statusRequest = RequestRecordMapper.build(developerId, productId, requestType);

    requestService.save(statusRequest);

    RequestRecordView result = RequestRecordMapper.toView(statusRequest);

    LOG.debug("Exit.");

    return result;
  }
}
