package com.umasuo.product.application.service.update;

import com.umasuo.model.Updater;
import com.umasuo.product.application.dto.action.UpdateProductType;
import com.umasuo.product.domain.model.ProductType;
import com.umasuo.product.infrastructure.update.UpdateAction;
import com.umasuo.product.infrastructure.update.UpdateActionUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Created by umasuo on 17/6/1.
 */
@Service(value = UpdateActionUtils.UPDATE_PRODUCT)
public class UpdateProductTypeService implements Updater<ProductType, UpdateAction> {

  /**
   * Logger.
   */
  private static final Logger LOG = LoggerFactory.getLogger(UpdateProductTypeService.class);

  @Override
  public void handle(ProductType entity, UpdateAction updateAction) {
    LOG.debug("Enter.");

    UpdateProductType action = (UpdateProductType) updateAction;

    entity.setName(action.getName());
    entity.setGroupName(action.getGroupName());

    LOG.debug("Exit.");
  }

}
