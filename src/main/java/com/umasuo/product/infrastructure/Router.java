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
   * The id string.
   */
  public static final String ID = "/{id}";

  /**
   * Product root path: /v1/products.
   */
  public static final String PRODUCT_ROOT = VERSION + "/products";

  /**
   * Single product path: /v1/products/{id}.
   */
  public static final String PRODUCT_WITH_ID = PRODUCT_ROOT + ID;

  /**
   * Single product status path: /v1/products/{id}/status.
   */
  public static final String PRODUCT_STATUS = PRODUCT_WITH_ID + "/status";

  /**
   * ProductType root path: /v1/products/types.
   */
  public static final String PRODUCT_TYPE_ROOT = PRODUCT_ROOT + "/types";

  /**
   * Admin root path: /v1/admin/products
   */
  public static final String ADMIN_PRODUCT_ROOT = VERSION + "/admin/products";

  /**
   * Admin product path: /v1/admin/products/{id}
   */
  public static final String ADMIN_PRODUCT_WITH_ID = ADMIN_PRODUCT_ROOT + ID;

  /**
   * Developer application path for admin: /v1/admin/products/applications
   */
  public static final String ADMIN_DEVELOPER_APPLICATION_ROOT =
      ADMIN_PRODUCT_ROOT + "/applications";

  /**
   * One application for developer, path to admin: /v1/admin/products/applications/{id}
   */
  public static final String ADMIN_DEVELOPER_APPLICATION_WITH_ID =
      ADMIN_DEVELOPER_APPLICATION_ROOT + ID;

  /**
   * Admin product type root path: /v1/admin/products/types.
   */
  public static final String ADMIN_PRODUCT_TYPE_ROOT = ADMIN_PRODUCT_ROOT + "/types";

  /**
   * Admin one product type path: /v1/admin/products/types/{id}.
   */
  public static final String ADMIN_PRODUCT_TYPE_WITH_ID = ADMIN_PRODUCT_TYPE_ROOT + ID;

  /**
   * Admin product count path: /v1/admin/products/count.
   */
  public static final String ADMIN_PRODUCT_COUNT = ADMIN_PRODUCT_ROOT + "/count";
}
