package com.umasuo.device.definition.application.dto.mapper;

import com.umasuo.device.definition.application.dto.DeviceDraft;
import com.umasuo.device.definition.application.dto.ProductView;
import com.umasuo.device.definition.domain.model.Device;
import com.umasuo.device.definition.infrastructure.enums.ProductStatus;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by umasuo on 17/6/1.
 */
public final class DeviceMapper {

  private DeviceMapper() {
  }

  /**
   * convert from view to domain model
   */
  public static Device viewToModel(DeviceDraft draft, String developerId) {
    Device device = new Device();
    device.setDeveloperId(developerId);
    device.setIcon(draft.getIcon());
    device.setName(draft.getName());
    device.setProductType(draft.getProductTypeId());
    device.setCommunicationType(draft.getType());
    device.setDataDefineIds(draft.getDataDefineIds());
    if (draft.getOpenable() != null) {
      device.setOpenable(draft.getOpenable());
    }

    device.setStatus(ProductStatus.DEVELOPING);

    return device;
  }

  /**
   * convert domain model to view.
   */
  public static ProductView modelToView(Device device) {
    ProductView view = new ProductView();
    view.setId(device.getId());
    view.setProductTypeId(device.getProductType());
    view.setCreatedAt(device.getCreatedAt());
    view.setLastModifiedAt(device.getLastModifiedAt());
    view.setVersion(device.getVersion());
    view.setDeveloperId(device.getDeveloperId());
    view.setIcon(device.getIcon());
    view.setStatus(device.getStatus());
    view.setName(device.getName());
    view.setType(device.getCommunicationType());
    view.setOpenable(device.getOpenable());

    view.setModel(device.getModel());
    view.setFirmwareVersion(device.getFirmwareVersion());
    view.setWifiModule(device.getWifiModule());
    view.setDescription(device.getDescription());


    view.setFunctions(DeviceFunctionMapper.toModel(device.getDeviceFunctions()));

    return view;
  }

  /**
   * convert list of model to list of views.
   */
  public static List<ProductView> modelToView(List<Device> deviceList) {
    List<ProductView> views = new ArrayList<>();
    deviceList.stream().forEach(
        device -> views.add(modelToView(device))
    );
    return views;
  }
}
