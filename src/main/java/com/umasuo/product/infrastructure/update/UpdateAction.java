package com.umasuo.product.infrastructure.update;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.umasuo.product.application.dto.action.AddDataDefinition;
import com.umasuo.product.application.dto.action.AddFunction;
import com.umasuo.product.application.dto.action.CopyDataDefinition;
import com.umasuo.product.application.dto.action.CopyFunction;
import com.umasuo.product.application.dto.action.RemoveDataDefinition;
import com.umasuo.product.application.dto.action.RemoveFunction;
import com.umasuo.product.application.dto.action.UpdateDataDefinition;
import com.umasuo.product.application.dto.action.UpdateFunction;
import com.umasuo.product.application.dto.action.UpdateProduct;

import java.io.Serializable;

/**
 * configurations for common update actions that will be used in more thant one service
 * and this action also extends other action configure in each service.
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property =
    "action")
@JsonSubTypes({
    @JsonSubTypes.Type(value = CopyDataDefinition.class,
        name = UpdateActionUtils.COPY_DATA_DEFINITION),
    @JsonSubTypes.Type(value = AddDataDefinition.class,
        name = UpdateActionUtils.ADD_DATA_DEFINITION),
    @JsonSubTypes.Type(value = RemoveDataDefinition.class,
        name = UpdateActionUtils.REMOVE_DATA_DEFINITION),
    @JsonSubTypes.Type(value = UpdateDataDefinition.class,
        name = UpdateActionUtils.UPDATE_DATA_DEFINITION),
    @JsonSubTypes.Type(value = UpdateProduct.class, name = UpdateActionUtils.UPDATE_PRODUCT),
    @JsonSubTypes.Type(value = CopyFunction.class, name = UpdateActionUtils.COPY_FUNCTION),
    @JsonSubTypes.Type(value = RemoveFunction.class, name = UpdateActionUtils.REMOVE_FUNCTION),
    @JsonSubTypes.Type(value = AddFunction.class, name = UpdateActionUtils.ADD_FUNCTION),
    @JsonSubTypes.Type(value = UpdateFunction.class, name = UpdateActionUtils.UPDATE_FUNCTION)
})
public interface UpdateAction extends Serializable {

  /**
   * get action name.
   *
   * @return name in string.
   */
  String getActionName();
}
