package com.umasuo.product.domain.service;

import com.umasuo.exception.NotExistException;
import com.umasuo.product.domain.model.ApplicationRecord;
import com.umasuo.product.infrastructure.enums.RecordStatus;
import com.umasuo.product.infrastructure.repository.ApplicationRecordRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service class for Request.
 */
@Service
public class RequestService {

  /**
   * Logger.
   */
  private static final Logger LOG = LoggerFactory.getLogger(RequestService.class);

  /**
   * ApplicationRecordRepository.
   */
  @Autowired
  private transient ApplicationRecordRepository repository;

  /**
   * Save status request.
   *
   * @param request the request
   * @return the status request
   */
  public ApplicationRecord save(ApplicationRecord request) {
    LOG.debug("Enter. request: {}.", request);

    ApplicationRecord savedRequest = repository.save(request);

    LOG.trace("Saved request: {}.", savedRequest);
    LOG.debug("Exit.");

    return savedRequest;
  }


  /**
   * Get status request.
   *
   * @param id the id
   * @return the status request
   */
  public ApplicationRecord get(String id) {
    LOG.debug("Enter. id: {}.", id);

    ApplicationRecord request = repository.findOne(id);

    if (request == null) {
      LOG.debug("Can not find request: {}.", id);
      throw new NotExistException("Request not exist");
    }

    LOG.debug("Exit. request: {}.", request);

    return request;
  }

  /**
   * Cancel request.
   *
   * @param developerId the developer id
   * @param productId the product id
   */
  public void cancelRequest(String developerId, String productId) {
    LOG.debug("Enter. developerId: {}, productId: {}.", developerId, productId);

    int count = repository.changeRequestStatus(developerId, productId, RecordStatus.FINISHED);

    LOG.debug("Exit. cancel countProducts: {}.", count);
  }

  /**
   * Get all request order by lastModifiedAt.
   *
   * @return list of ApplicationRecord
   */
  public List<ApplicationRecord> getAll() {
    LOG.debug("Enter.");

    List<ApplicationRecord> requests = repository.findAll();

    LOG.debug("Exit. request size: {}.", requests.size());

    return requests;
  }
}
