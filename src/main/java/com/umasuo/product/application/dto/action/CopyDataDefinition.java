package com.umasuo.product.application.dto.action;

import com.umasuo.product.infrastructure.update.UpdateAction;
import com.umasuo.product.infrastructure.update.UpdateActionUtils;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Created by umasuo on 17/6/1.
 */
@Data
public class CopyDataDefinition implements UpdateAction, Serializable {

  private static final long serialVersionUID = 2863847844677656573L;

  private List<String> dataDefinitionIds;

  @Override
  public String getActionName() {
    return UpdateActionUtils.COPY_DATA_DEFINITION;
  }
}
