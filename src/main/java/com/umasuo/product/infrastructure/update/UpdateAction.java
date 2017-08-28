package com.umasuo.product.infrastructure.update;

import static com.umasuo.product.infrastructure.update.UpdateActionUtils.*;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.umasuo.product.application.dto.action.AddDataDefinition;
import com.umasuo.product.application.dto.action.AddFunction;
import com.umasuo.product.application.dto.action.AddProductTypeFunction;
import com.umasuo.product.application.dto.action.CopyDataDefinition;
import com.umasuo.product.application.dto.action.CopyFunction;
import com.umasuo.product.application.dto.action.RemoveDataDefinition;
import com.umasuo.product.application.dto.action.RemoveFunction;
import com.umasuo.product.application.dto.action.RemoveProductTypeFunction;
import com.umasuo.product.application.dto.action.UpdateProductTypeFunction;
import com.umasuo.product.application.dto.action.UpdateDataDefinition;
import com.umasuo.product.application.dto.action.UpdateFunction;
import com.umasuo.product.application.dto.action.UpdateProduct;
import com.umasuo.product.application.dto.action.UpdateProductType;
import com.umasuo.product.application.dto.action.UpdateStandardFunction;

import java.io.Serializable;

/**
 * configurations for common update actions that will be used in more thant one service
 * and this action also extends other action configure in each service.
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "action")
@JsonSubTypes({
    //update action for product.
    @JsonSubTypes.Type(value = CopyDataDefinition.class, name = COPY_DATA_DEFINITION),
    @JsonSubTypes.Type(value = AddDataDefinition.class, name = ADD_DATA_DEFINITION),
    @JsonSubTypes.Type(value = RemoveDataDefinition.class, name = REMOVE_DATA_DEFINITION),
    @JsonSubTypes.Type(value = UpdateDataDefinition.class, name = UPDATE_DATA_DEFINITION),
    @JsonSubTypes.Type(value = UpdateProduct.class, name = UPDATE_PRODUCT),
    @JsonSubTypes.Type(value = CopyFunction.class, name = COPY_FUNCTION),
    @JsonSubTypes.Type(value = RemoveFunction.class, name = REMOVE_FUNCTION),
    @JsonSubTypes.Type(value = AddFunction.class, name = ADD_FUNCTION),
    @JsonSubTypes.Type(value = UpdateFunction.class, name = UPDATE_FUNCTION),
    @JsonSubTypes.Type(value = UpdateStandardFunction.class, name = UPDATE_STANDARD_FUNCTION),
    //update action for product type.
    @JsonSubTypes.Type(value = UpdateProductType.class, name = UPDATE_PRODUCT_TYPE),
    @JsonSubTypes.Type(value = AddProductTypeFunction.class, name = ADD_PRODUCT_TYPE_FUNCTION),
    @JsonSubTypes.Type(value = UpdateProductTypeFunction.class, name = UPDATE_PRODUCT_TYPE_FUNCTION),
    @JsonSubTypes.Type(value = RemoveProductTypeFunction.class, name = REMOVE_PRODUCT_TYPE_FUNCTION),

    @JsonSubTypes.Type(value = AddDataDefinition.class, name = ADD_PRODUCT_TYPE_DATA),
    @JsonSubTypes.Type(value = UpdateDataDefinition.class, name = UPDATE_PRODUCT_TYPE_DATA),
    @JsonSubTypes.Type(value = RemoveDataDefinition.class, name = REMOVE_PRODUCT_TYPE_DATA),
})
public interface UpdateAction extends Serializable {

  /**
   * get action name.
   *
   * @return name in string.
   */
  String getActionName();
}
