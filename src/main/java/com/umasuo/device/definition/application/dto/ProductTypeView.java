package com.umasuo.device.definition.application.dto;

import com.umasuo.device.definition.domain.model.CommonFunction;

import lombok.Data;

import java.util.List;

/**
 * Created by Davis on 17/6/28.
 */
@Data
public class ProductTypeView {

  private String id;

  private String name;

  private String groupName;

  private List<CommonFunctionView> functions;
}
