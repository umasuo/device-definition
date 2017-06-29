package com.umasuo.device.definition.application.dto;

import lombok.Data;

import java.util.List;

/**
 * Created by Davis on 17/6/29.
 */
@Data
public class CopyRequest {

  private String deviceDefinitionId;

  private List<String> platformDataDefinitionIds;

  private List<String> developerDataDefinitionIds;
}
