package com.umasuo.product.domain.model;

import lombok.Data;

import javax.persistence.Embeddable;

/**
 * Created by Davis on 17/7/19.
 */
@Data
@Embeddable
public class TestUnion {

  private String unionId;

  private String secretKey;
}
