package com.umasuo.product.application.service;

import com.umasuo.product.application.dto.ApplicationRecordView;
import com.umasuo.product.application.dto.mapper.ApplicationRecordMapper;
import com.umasuo.product.domain.model.ApplicationRecord;
import com.umasuo.product.domain.service.RequestService;
import com.umasuo.product.infrastructure.enums.ApplicationStatus;
import com.umasuo.product.infrastructure.enums.RecordStatus;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
   * RequestService.
   */
  @Autowired
  private transient RequestService requestService;

  /**
   * The ProductCommandApplication.
   */
  @Autowired
  private transient ProductCommandApplication productCommandApplication;

  /**
   * 新建一条请求记录.
   *
   * @param developerId the developer id
   * @param productId the product id
   * @return status request view
   */
  public ApplicationRecordView create(String developerId, String productId) {
    LOG.debug("Enter. developerId: {}, productId: {}.", developerId, productId);

    ApplicationRecord statusRequest = ApplicationRecordMapper.build(developerId, productId);

    requestService.save(statusRequest);

    ApplicationRecordView result = ApplicationRecordMapper.toView(statusRequest);

    LOG.debug("Exit.");

    return result;
  }

  /**
   * 取消请求.
   *
   * @param developerId the developer id
   * @param productId the product id
   */
  public void cancelRequest(String developerId, String productId) {
    LOG.debug("Enter. developerId: {}, productId: {}.", developerId, productId);

    requestService.cancelRequest(developerId, productId);

    LOG.debug("Exit.");

  }

  /**
   * Reply request.
   *
   * @param requestId the request id
   * @param recordStatus the status
   */
// 2. 处理开发的请求
  public void replyRequest(String requestId, RecordStatus recordStatus, ApplicationStatus applicationStatus) {
    LOG.debug("Enter. requestId: {}, recordStatus: {}, applicationStatus: {}.",
        requestId, recordStatus, applicationStatus);

    ApplicationRecord statusRequest = requestService.get(requestId);

    statusRequest.setRecordStatus(recordStatus);
    statusRequest.setApplicationStatus(applicationStatus);

    requestService.save(statusRequest);

    productCommandApplication.updateStatusByResponse(statusRequest.getProductId(), applicationStatus);

    LOG.debug("Exit.");
  }

  /**
   * Batch reply request.
   */
// 3. 批量处理开发者的请求
  public void batchReplyRequest() {
    // TODO: 17/7/19
  }

  /**
   * Get all application record.
   *
   * @return list of ApplicationRecordView
   */
  public List<ApplicationRecordView> getApplication() {
    LOG.debug("Enter.");

    List<ApplicationRecord> requests = requestService.getAll();

    List<ApplicationRecordView> result = ApplicationRecordMapper.toView(requests);

    LOG.debug("Exit. applicationRecord size: {}.", result.size());

    return result;
  }
}
