package com.umasuo.product.application.service;

import com.umasuo.product.application.dto.StatusRequestView;
import com.umasuo.product.application.dto.mapper.RequestMapper;
import com.umasuo.product.domain.model.StatusRequest;
import com.umasuo.product.domain.service.RequestService;
import com.umasuo.product.infrastructure.enums.RequestStatus;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 给平台使用的，用于获取及处理开发者的请求。
 *
 * Created by Davis on 17/7/19.
 */
@Service
public class RequestApplication {

  /**
   * Logger.
   */
  private static final Logger LOG = LoggerFactory.getLogger(RequestApplication.class);

  @Autowired
  private transient RequestService requestService;


  public StatusRequestView create(String developerId, String productId) {
    LOG.debug("Enter. developerId: {}, productId: {}.", developerId, productId);

    StatusRequest statusRequest = RequestMapper.build(developerId, productId);

    requestService.save(statusRequest);

    StatusRequestView result = RequestMapper.toModel(statusRequest);

    LOG.debug("Exit.");

    return result;
  }

  // 1. 获取开发者的请求
  public List<StatusRequestView> getRequest() {
    // TODO: 17/7/19
    return null;
  }

  // 2. 处理开发的请求
  public void replyRequest(String requestId, RequestStatus status) {
    LOG.debug("Enter. requestId: {}, status: {}.", requestId, status);

    StatusRequest statusRequest = requestService.get(requestId);

    statusRequest.setStatus(status);

    requestService.save(statusRequest);

    LOG.debug("Exit.");
  }

  // 3. 批量处理开发者的请求
  public void batchReplyRequest() {
    // TODO: 17/7/19
  }
}