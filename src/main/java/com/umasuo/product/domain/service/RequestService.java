package com.umasuo.product.domain.service;

import com.umasuo.exception.NotExistException;
import com.umasuo.product.domain.model.StatusRequest;
import com.umasuo.product.infrastructure.enums.RequestStatus;
import com.umasuo.product.infrastructure.repository.RequestRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Davis on 17/7/19.
 */
@Service
public class RequestService {

  /**
   * Logger.
   */
  private static final Logger LOG = LoggerFactory.getLogger(RequestService.class);

  @Autowired
  private transient RequestRepository repository;

  public StatusRequest save(StatusRequest request) {
    LOG.debug("Enter. request: {}.", request);

    StatusRequest savedRequest = repository.save(request);

    LOG.trace("Saved request: {}.", savedRequest);
    LOG.debug("Exit.");

    return savedRequest;
  }

  public StatusRequest get(String id) {
    LOG.debug("Enter. id: {}.", id);

    StatusRequest request = repository.findOne(id);

    if (request == null) {
      LOG.debug("Can not find request: {}.", id);
      throw new NotExistException("Request not exist");
    }

    LOG.debug("Exit. request: {}.", request);

    return request;
  }

  public void cancelRequest(String developerId, String productId) {
    LOG.debug("Enter. developerId: {}, productId: {}.", developerId, productId);

    int count = repository.changeRequestStatus(developerId, productId, RequestStatus.CANCELED);

    LOG.debug("Exit. cancel count: {}.", count);
  }
}
