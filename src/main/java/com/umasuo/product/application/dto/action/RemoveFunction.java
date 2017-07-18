package com.umasuo.product.application.dto.action;

import com.umasuo.product.infrastructure.update.UpdateAction;
import com.umasuo.product.infrastructure.update.UpdateActionUtils;

import lombok.Data;

import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by Davis on 17/7/4.
 */
@Data
public class RemoveFunction implements UpdateAction {

  @NotNull
  @Size(min = 1)
  private List<String> functionIds;

  @Override
  public String getActionName() {
    return UpdateActionUtils.REMOVE_FUNCTION;
  }
}
