package com.umasuo.product.application.service.update;

import com.umasuo.model.Updater;
import com.umasuo.product.application.dto.action.UpdateProduct;
import com.umasuo.product.domain.model.Product;
import com.umasuo.product.infrastructure.update.UpdateAction;
import com.umasuo.product.infrastructure.update.UpdateActionUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * 更新产品信息的service.
 */
@Service(value = UpdateActionUtils.UPDATE_PRODUCT)
public class UpdateProductService implements Updater<Product, UpdateAction> {

  /**
   * Logger.
   */
  private static final Logger LOG = LoggerFactory.getLogger(UpdateProductService.class);

  /**
   * 执行update的方法。
   *
   * @param entity the Product
   * @param updateAction the UpdateProduct
   */
  @Override
  public void handle(Product entity, UpdateAction updateAction) {
    LOG.debug("Enter. productId: {}, updateAction: {}.", entity.getId(), updateAction);

    UpdateProduct action = (UpdateProduct) updateAction;
    entity.setName(action.getName());
    entity.setIcon(action.getIcon());
    entity.setCommunicationType(action.getType());
    entity.setOpenable(action.getOpenable());
    entity.setDescription(action.getDescription());
    entity.setWifiModule(action.getWifiModule());
    entity.setModel(action.getModel());
    entity.setFirmwareVersion(action.getFirmwareVersion());

    LOG.debug("Exit.");
  }

}
