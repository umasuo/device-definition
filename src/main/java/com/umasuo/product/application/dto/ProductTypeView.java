package com.umasuo.product.application.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Davis on 17/6/28.
 */
@Data
public class ProductTypeView implements Serializable {

  private static final long serialVersionUID = 8675853413590870401L;

  private String id;

  private String name;

  private String groupName;

  private List<CommonFunctionView> functions;

  private List<CommonDataView> data;

  private Integer version;
}
