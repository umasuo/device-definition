package com.umasuo.product.infrastructure;

/**
 * Router for path.
 */
public class Router {

  /**
   * The constant VERSION.
   */
  public static final String VERSION = "/v1";

  /**
   * The constant PRODUCT_ROOT.
   */
  public static final String PRODUCT_ROOT = VERSION + "/products";

  /**
   * The constant PRODUCT_WITH_ID.
   */
  public static final String PRODUCT_WITH_ID = PRODUCT_ROOT + "/{id}";

  /**
   * The constant PRODUCT_STATUS.
   */
  public static final String PRODUCT_STATUS = PRODUCT_WITH_ID + "/status";

  /**
   * The constant PRODUCT_TYPE_ROOT.
   */
  public static final String PRODUCT_TYPE_ROOT = PRODUCT_ROOT + "/types";

  /**
   * The constant PRODUCT_REQUEST.
   */
  public static final String PRODUCT_REQUEST = PRODUCT_ROOT + "/request";

  /**
   * The constant PRODUCT_REQUEST_WITH_ID.
   */
  public static final String PRODUCT_REQUEST_WITH_ID = PRODUCT_REQUEST + "/{}";

  /**
   * The constant ADMIN_PRODUCT_TYPE_ROOT.
   */
  public static final String ADMIN_PRODUCT_TYPE_ROOT = VERSION + "/admin/products/types";

  /**
   * The constant ADMIN_PRODUCT_TYPE_WITH_ID.
   */
  public static final String ADMIN_PRODUCT_TYPE_WITH_ID = ADMIN_PRODUCT_TYPE_ROOT + "/{id}";

  /**
   * The constant ADMIN_PRODUCT_COUNT.
   */
  public static final String ADMIN_PRODUCT_COUNT = ADMIN_PRODUCT_TYPE_ROOT + "/count";
}
