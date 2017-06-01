package com.umasuo.device.definition.infrastructure.update;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.umasuo.device.definition.application.dto.action.AddDataDefinition;
import com.umasuo.device.definition.application.dto.action.RemoveDataDefinition;
import com.umasuo.device.definition.application.dto.action.UpdateDevice;

import java.io.Serializable;

/**
 * configurations for common update actions that will be used in more thant one service
 * and this action also extends other action configure in each service.
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property =
    "action")
@JsonSubTypes( {
    @JsonSubTypes.Type(value = AddDataDefinition.class, name = UpdateActionUtils
        .ADD_DATA_DEFINITION),
    @JsonSubTypes.Type(value = RemoveDataDefinition.class, name = UpdateActionUtils
        .REMOVE_DATA_DEFINITION),
    @JsonSubTypes.Type(value = UpdateDevice.class, name = UpdateActionUtils
        .UPDATE_DATA_DEFINITION)
})
public interface UpdateAction extends Serializable {
  /**
   * get action name.
   *
   * @return name in string.
   */
  String getActionName();
}
