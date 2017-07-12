package com.umasuo.device.definition.application.dto;

import com.google.common.collect.Lists;

import lombok.Data;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Stream;

/**
 * Created by Davis on 17/6/28.
 */
@Data
public class CommonDataView implements Serializable{

  private static final long serialVersionUID = -8861282658167694700L;

  private String id;

  private String dataId;

  private String name;

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

    result.setDataId(map.get("dataId").toString());
    result.setName(map.get("name").toString());
    result.setId(map.get("id").toString());

    return result;
  }
}
