package com.umasuo.device.definition.application.service.update;

import com.umasuo.device.definition.application.dto.action.UpdateStatus;
import com.umasuo.device.definition.domain.model.Product;
import com.umasuo.device.definition.infrastructure.update.UpdateAction;
import com.umasuo.device.definition.infrastructure.update.UpdateActionUtils;
import com.umasuo.model.Updater;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Created by Davis on 17/7/4.
 */
@Service(UpdateActionUtils.SET_STATUS)
public class SetStatusService implements Updater<Product, UpdateAction> {

  /**
   * Logger.
   */
  private static final Logger LOG = LoggerFactory.getLogger(SetStatusService.class);

  @Override
  public void handle(Product device, UpdateAction updateAction) {
    LOG.debug("Enter. device: {}, updateAction: {}.", device, updateAction);

    UpdateStatus action = (UpdateStatus) updateAction;
    device.setStatus(action.getStatus());

    LOG.debug("Exit.");
  }
}
