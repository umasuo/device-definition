package com.umasuo.product.application.dto.action;

import com.umasuo.product.infrastructure.update.UpdateAction;
import com.umasuo.product.infrastructure.update.UpdateActionUtils;

import lombok.Data;

import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * 拷贝产品功能的action。
 */
@Data
public class CopyFunction implements UpdateAction {

  /**
   * 来自产品类型的功能定义ID。
   */
  @NotNull
  @Size(min = 1)
  private List<String> functionIds;

  /**
   * Get action name: removeDataDefinition.
   *
   * @return removeDataDefinition
   */
  @Override
  public String getActionName() {
    return UpdateActionUtils.COPY_FUNCTION;
  }
}
