package com.umasuo.product.application.dto;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.collect.Lists;

import lombok.Data;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by Davis on 17/6/28.
 */
@Data
public class CommonDataView implements Serializable{

  private static final long serialVersionUID = -8861282658167694700L;

  private String id;

  private String dataId;

  private String name;

  /**
   * version used for update date check.
   */
  private Integer version;

  /**
   * the data structure.
   */
  private String schema;

  private JsonNode dataSchema;

  private String description;

  public static List<CommonDataView> build(List<CommonDataView> objects) {
    List<CommonDataView> result = Lists.newArrayList();

    for (Object view : objects) {
      LinkedHashMap map = (LinkedHashMap) view;

      result.add(build(map));
    }

    return result;
  }

  private static CommonDataView build(LinkedHashMap map) {
    CommonDataView result = new CommonDataView();

    result.setId(map.get("id").toString());
    result.setDataId(map.get("dataId").toString());
    result.setName(map.get("name").toString());
    result.setSchema(map.get("dataSchema").toString());
    result.setDataSchema(null);
    result.setDescription(String.valueOf(map.getOrDefault("description", null)));
    result.setVersion(Integer.valueOf(map.get("version").toString()));

    return result;
  }
}
