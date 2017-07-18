package com.umasuo.product.application.service.update;

import com.umasuo.product.application.dto.action.UpdateProduct;
import com.umasuo.product.domain.model.Product;
import com.umasuo.product.infrastructure.update.UpdateAction;
import com.umasuo.product.infrastructure.update.UpdateActionUtils;
import com.umasuo.model.Updater;

import org.springframework.stereotype.Service;

/**
 * Created by umasuo on 17/6/1.
 */
@Service(value = UpdateActionUtils.UPDATE_PRODUCT)
public class UpdateProductService implements Updater<Product, UpdateAction> {

  @Override
  public void handle(Product entity, UpdateAction updateAction) {
    UpdateProduct action = (UpdateProduct) updateAction;
    entity.setName(action.getName());
    entity.setIcon(action.getIcon());
    entity.setCommunicationType(action.getType());
    entity.setOpenable(action.getOpenable());
    entity.setDescription(action.getDescription());
    entity.setWifiModule(action.getWifiModule());
    entity.setModel(action.getModel());
    entity.setFirmwareVersion(action.getFirmwareVersion());
  }

}
