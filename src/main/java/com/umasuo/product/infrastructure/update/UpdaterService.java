package com.umasuo.product.infrastructure.update;

import com.umasuo.model.Updater;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

/**
 * Updater service.
 */
@Service
public class UpdaterService implements Updater<Object, UpdateAction> {

  /**
   * ApplicationContext for get update services.
   */
  private transient ApplicationContext context;

  /**
   * Constructor.
   *
   * @param context ApplicationContext
   */
  public UpdaterService(ApplicationContext context) {
    this.context = context;
  }

  /**
   * Get mapper.
   *
   * @param action UpdateAction class
   * @return ZoneUpdateMapper
   */
  private Updater getUpdateService(UpdateAction action) {
    return (Updater) context.getBean(action.getActionName());
  }

  /**
   * Handler.
   *
   * @param obj object need to update
   * @param action the UpdateAction
   */
  @Override
  public void handle(Object obj, UpdateAction action) {
    Updater updater = getUpdateService(action);
    updater.handle(obj, action);
  }
}
