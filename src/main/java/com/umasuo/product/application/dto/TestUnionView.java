package com.umasuo.product.application.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by Davis on 17/7/19.
 */
@Data
public class TestUnionView implements Serializable{

  private static final long serialVersionUID = -2570826887391517208L;

  private String unionId;

  private String secretKey;
}
