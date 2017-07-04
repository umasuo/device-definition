package com.umasuo.device.definition.application.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by Davis on 17/6/28.
 */
@Data
public class CommonDataView implements Serializable{

  private static final long serialVersionUID = -8861282658167694700L;

  private String id;

  private String dataId;

  private String name;
}
