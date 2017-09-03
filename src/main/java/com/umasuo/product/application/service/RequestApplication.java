package com.umasuo.product.application.service;

import com.umasuo.product.application.dto.RequestRecordView;
import com.umasuo.product.application.dto.mapper.RequestRecordMapper;
import com.umasuo.product.domain.model.RequestRecord;
import com.umasuo.product.domain.service.RequestRecordService;
import com.umasuo.product.infrastructure.enums.RequestStatus;
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
   * RequestRecordService.
   */
  @Autowired
  private transient RequestRecordService requestService;

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
  public RequestRecordView create(String developerId, String productId) {
    LOG.debug("Enter. developerId: {}, productId: {}.", developerId, productId);

    RequestRecord statusRequest = RequestRecordMapper.build(developerId, productId);

    requestService.save(statusRequest);

    RequestRecordView result = RequestRecordMapper.toView(statusRequest);

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
  public void replyRequest(String requestId, RecordStatus recordStatus, RequestStatus applicationStatus) {
    LOG.debug("Enter. requestId: {}, recordStatus: {}, requestStatus: {}.",
        requestId, recordStatus, applicationStatus);

    RequestRecord statusRequest = requestService.get(requestId);

    statusRequest.setRecordStatus(recordStatus);
    statusRequest.setRequestStatus(applicationStatus);

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
   * @return list of RequestRecordView
   */
  public List<RequestRecordView> getApplication() {
    LOG.debug("Enter.");

    List<RequestRecord> requests = requestService.getAll();

    List<RequestRecordView> result = RequestRecordMapper.toView(requests);

    LOG.debug("Exit. applicationRecord size: {}.", result.size());

    return result;
  }
}
