package com.umasuo.product.application.dto.mapper;

import com.google.common.collect.Lists;
import com.umasuo.product.application.dto.RequestRecordView;
import com.umasuo.product.domain.model.RequestRecord;
import com.umasuo.product.infrastructure.enums.RecordStatus;
import com.umasuo.product.infrastructure.enums.RequestStatus;
import com.umasuo.product.infrastructure.enums.RequestType;

import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * RequestRecord mapper class, used to convert RequestRecord to RequestRecordView.
 */
public final class RequestRecordMapper {

  /**
   * Private constructor.
   */
  private RequestRecordMapper() {
  }

  /**
   * Build RequestRecord by developerId and productId.
   *
   * @param developerId the developer id
   * @param productId the product id
   * @return the status request
   */
  public static RequestRecord build(String developerId, String productId, RequestType requestType) {
    RequestRecord requestRecord = new RequestRecord();

    requestRecord.setDeveloperId(developerId);
    requestRecord.setProductId(productId);
    requestRecord.setRecordStatus(RecordStatus.UNVIEW);
    requestRecord.setRequestStatus(RequestStatus.CREATED);
    requestRecord.setRequestType(requestType);

    return requestRecord;
  }

  /**
   * Convert list of RequestRecord to list of RequestRecordView.
   *
   * @param entities list of RequestRecord
   * @return list of RequestRecordView
   */
  public static List<RequestRecordView> toView(List<RequestRecord> entities) {
    List<RequestRecordView> views = Lists.newArrayList();

    if (!CollectionUtils.isEmpty(entities)) {
      entities.stream().forEach(entity -> views.add(toView(entity)));
    }

    return views;
  }

  /**
   * Convert RequestRecord to RequestRecordView.
   *
   * @param entity the RequestRecord
   * @return RequestRecordView
   */
  public static RequestRecordView toView(RequestRecord entity) {
    RequestRecordView view = new RequestRecordView();

    view.setId(entity.getId());
    view.setCreatedAt(entity.getCreatedAt());
    view.setLastModifiedAt(entity.getLastModifiedAt());
    view.setVersion(entity.getVersion());
    view.setDeveloperId(entity.getDeveloperId());
    view.setProductId(entity.getProductId());
    view.setRecordStatus(entity.getRecordStatus());
    view.setApplicationStatus(entity.getRequestStatus());
    view.setRequestType(entity.getRequestType());
    view.setRemark(entity.getRemark());

    return view;
  }
}
