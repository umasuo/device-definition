package com.umasuo.device.definition.infrastructure;

/**
 * Created by umasuo on 17/6/1.
 */
public class Router {

  public static final String VERSION = "/v1";

  public static final String PRODUCT_ROOT = VERSION + "/products";

  public static final String PRODUCT_WITH_ID = PRODUCT_ROOT + "/{id}";

  public static final String PRODUCT_STATUS = PRODUCT_WITH_ID + "/status";

  public static final String PRODUCT_TYPE_ROOT = PRODUCT_ROOT + "/types";
}
