package com.umasuo.device.definition.application.dto.action;

import com.umasuo.device.definition.infrastructure.update.UpdateAction;
import com.umasuo.device.definition.infrastructure.update.UpdateActionUtils;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.NotNull;

/**
 * Created by umasuo on 17/6/1.
 */
@Data
public class RemoveDataDefinition implements UpdateAction, Serializable {

  private static final long serialVersionUID = 2863847844677656573L;

  @NotNull
  private List<String> dataDefinitionIds;

  @Override
  public String getActionName() {
    return UpdateActionUtils.REMOVE_DATA_DEFINITION;
  }
}
