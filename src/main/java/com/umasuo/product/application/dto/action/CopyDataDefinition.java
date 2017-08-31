package com.umasuo.product.application.dto.action;

import com.umasuo.product.infrastructure.update.UpdateAction;
import com.umasuo.product.infrastructure.update.UpdateActionUtils;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 拷贝产品数据的action。
 */
@Data
public class CopyDataDefinition implements UpdateAction, Serializable {

  /**
   * The serialVersionUID.
   */
  private static final long serialVersionUID = 2863847844677656573L;

  /**
   * The dataDifinition id.
   */
  private List<String> dataDefinitionIds;

  /**
   * Get action name: copyDataDefinition.
   *
   * @return copyDataDefinition
   */
  @Override
  public String getActionName() {
    return UpdateActionUtils.COPY_DATA_DEFINITION;
  }
}
