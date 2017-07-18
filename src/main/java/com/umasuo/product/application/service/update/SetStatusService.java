package com.umasuo.product.application.service.update;

import com.umasuo.product.application.dto.action.UpdateStatus;
import com.umasuo.product.domain.model.Product;
import com.umasuo.product.infrastructure.update.UpdateAction;
import com.umasuo.product.infrastructure.update.UpdateActionUtils;
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
  public void handle(Product product, UpdateAction updateAction) {
    LOG.debug("Enter. product: {}, updateAction: {}.", product, updateAction);

    UpdateStatus action = (UpdateStatus) updateAction;
    product.setStatus(action.getStatus());

    LOG.debug("Exit.");
  }
}
