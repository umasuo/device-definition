package com.umasuo.product.application.rest;

import com.umasuo.product.application.dto.RequestRecordView;
import com.umasuo.product.application.dto.ApplicationResponse;
import com.umasuo.product.application.service.RequestApplication;
import com.umasuo.product.infrastructure.Router;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * Controller class for Request.
 */
@CrossOrigin
@RestController
public class RequestController {

  /**
   * Logger.
   */
  private static final Logger LOG = LoggerFactory.getLogger(RequestController.class);

  /**
   * The RequestApplication.
   */
  @Autowired
  private transient RequestApplication requestApplication;


  /**
   * Update status.
   *
   * @param id the id
   * @param resp the status
   */
  @PutMapping(Router.ADMIN_DEVELOPER_APPLICATION_WITH_ID)
  public void updateStatus(@PathVariable("id") String id, @RequestBody ApplicationResponse resp) {
    LOG.info("Enter. id: {}, resp: {}.", id, resp);

    requestApplication.replyRequest(id, resp.getRecordStatus(), resp.getApplicationStatus());

    LOG.debug("Exit.");
  }

  /**
   * Get all request for admin.
   *
   * @return list of RequestRecordView
   */
  @GetMapping(Router.ADMIN_DEVELOPER_APPLICATION_ROOT)
  public Map<String, List<RequestRecordView>> getDeveloperRequest() {
    LOG.info("Enter.");

    Map<String, List<RequestRecordView>> result = requestApplication.getAllRequests();

    LOG.info("Exit. applicationRecord size: {}.", result.size());

    return result;
  }
}
