package com.umasuo.product.application.dto.mapper;

import com.google.common.collect.Lists;
import com.umasuo.product.application.dto.ApplicationRecordView;
import com.umasuo.product.domain.model.ApplicationRecord;
import com.umasuo.product.infrastructure.enums.ApplicationStatus;
import com.umasuo.product.infrastructure.enums.RecordStatus;

import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * ApplicationRecord mapper class, used to convert ApplicationRecord to ApplicationRecordView.
 */
public final class ApplicationRecordMapper {

  /**
   * Private constructor.
   */
  private ApplicationRecordMapper() {
  }

  /**
   * Build ApplicationRecord by developerId and productId.
   *
   * @param developerId the developer id
   * @param productId the product id
   * @return the status request
   */
  public static ApplicationRecord build(String developerId, String productId) {
    ApplicationRecord statusRequest = new ApplicationRecord();

    statusRequest.setDeveloperId(developerId);
    statusRequest.setProductId(productId);
    statusRequest.setRecordStatus(RecordStatus.UNVIEW);
    statusRequest.setApplicationStatus(ApplicationStatus.CREATED);

    return statusRequest;
  }

  /**
   * Convert list of ApplicationRecord to list of ApplicationRecordView.
   *
   * @param entities list of ApplicationRecord
   * @return list of ApplicationRecordView
   */
  public static List<ApplicationRecordView> toView(List<ApplicationRecord> entities) {
    List<ApplicationRecordView> views = Lists.newArrayList();

    if (!CollectionUtils.isEmpty(entities)) {
      entities.stream().forEach(entity -> views.add(toView(entity)));
    }

    return views;
  }

  /**
   * Convert ApplicationRecord to ApplicationRecordView.
   *
   * @param entity the ApplicationRecord
   * @return ApplicationRecordView
   */
  public static ApplicationRecordView toView(ApplicationRecord entity) {
    ApplicationRecordView view = new ApplicationRecordView();

    view.setId(entity.getId());
    view.setCreatedAt(entity.getCreatedAt());
    view.setLastModifiedAt(entity.getLastModifiedAt());
    view.setVersion(entity.getVersion());
    view.setDeveloperId(entity.getDeveloperId());
    view.setProductId(entity.getProductId());
    view.setRecordStatus(entity.getRecordStatus());
    view.setApplicationStatus(entity.getApplicationStatus());
    view.setRemark(entity.getRemark());

    return view;
  }
}
