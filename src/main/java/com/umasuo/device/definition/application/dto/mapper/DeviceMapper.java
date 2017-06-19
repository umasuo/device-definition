package com.umasuo.device.definition.application.dto.mapper;

import com.umasuo.device.definition.application.dto.DeviceDraft;
import com.umasuo.device.definition.application.dto.DeviceView;
import com.umasuo.device.definition.domain.model.Device;

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
    device.setType(draft.getType());
    device.setDataDefineIds(draft.getDataDefineIds());
    if (draft.getOpenable() != null) {
      device.setOpenable(draft.getOpenable());
    }
    return device;
  }

  /**
   * convert domain model to view.
   */
  public static DeviceView modelToView(Device device) {
    DeviceView view = new DeviceView();
    view.setId(device.getId());
    view.setCreatedAt(device.getCreatedAt());
    view.setLastModifiedAt(device.getLastModifiedAt());
    view.setVersion(device.getVersion());
    view.setDeveloperId(device.getDeveloperId());
    view.setIcon(device.getIcon());
    view.setStatus(device.getStatus());
    view.setName(device.getName());
    view.setDataDefineIds(device.getDataDefineIds());
    view.setType(device.getType());
    view.setOpenable(device.getOpenable());
    return view;
  }

  /**
   * convert list of model to list of views.
   */
  public static List<DeviceView> modelToView(List<Device> deviceList) {
    List<DeviceView> views = new ArrayList<>();
    deviceList.stream().forEach(
        device -> views.add(modelToView(device))
    );
    return views;
  }
}
