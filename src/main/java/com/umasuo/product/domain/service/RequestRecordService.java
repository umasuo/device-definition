package com.umasuo.product.domain.service;

import com.umasuo.exception.NotExistException;
import com.umasuo.product.domain.model.RequestRecord;
import com.umasuo.product.infrastructure.enums.RecordStatus;
import com.umasuo.product.infrastructure.repository.RequestRecordRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service class for RequestRecord.
 */
@Service
public class RequestRecordService {

  /**
   * Logger.
   */
  private static final Logger LOG = LoggerFactory.getLogger(RequestRecordService.class);

  /**
   * RequestRecordRepository.
   */
  @Autowired
  private transient RequestRecordRepository repository;

  /**
   * Save status request.
   *
   * @param request the request
   * @return the status request
   */
  public RequestRecord save(RequestRecord request) {
    LOG.debug("Enter. request: {}.", request);

    RequestRecord savedRequest = repository.save(request);

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
  public RequestRecord get(String id) {
    LOG.debug("Enter. id: {}.", id);

    RequestRecord request = repository.findOne(id);

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
   * @return list of RequestRecord
   */
  public List<RequestRecord> getAll() {
    LOG.debug("Enter.");

    List<RequestRecord> requests = repository.findAll();

    LOG.debug("Exit. request size: {}.", requests.size());

    return requests;
  }
}
