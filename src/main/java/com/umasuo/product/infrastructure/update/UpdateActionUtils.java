package com.umasuo.product.infrastructure.update;

/**
 * update action util.
 */
public class UpdateActionUtils {

  /**
   * add data definition.
   */
  public static final String ADD_DATA_DEFINITION = "addDataDefinition";

  public static final String COPY_DATA_DEFINITION = "copyDataDefinition";

  public static final String REMOVE_DATA_DEFINITION = "removeDataDefinition";

  // TODO: 17/7/7 暂时在data definition实现
  public static final String UPDATE_DATA_DEFINITION = "updateDataDefinition";

  public static final String COPY_FUNCTION = "copyFunction";

  public static final String REMOVE_FUNCTION = "removeFunction";

  public static final String ADD_FUNCTION = "addFunction";

  public static final String UPDATE_FUNCTION = "updateFunction";

  public static final String UPDATE_PRODUCT = "updateProduct";

  // TODO: 17/7/7 去掉这个，修改产品发布状态是有平台来做的
  public static final String SET_STATUS = "updateStatus";
}
