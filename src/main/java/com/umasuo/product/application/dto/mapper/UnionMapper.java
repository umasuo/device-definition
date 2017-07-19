package com.umasuo.product.application.dto.mapper;

import com.umasuo.product.application.dto.TestUnionView;
import com.umasuo.product.domain.model.TestUnion;

import org.apache.commons.lang3.RandomStringUtils;

import java.util.UUID;

/**
 * Created by Davis on 17/7/19.
 */
public class UnionMapper {

  private static final int SECRET_KEY_LENGTH = 7;

  public static TestUnion build() {
    TestUnion testUnion = new TestUnion();

    testUnion.setSecretKey(RandomStringUtils.randomAlphanumeric(SECRET_KEY_LENGTH));
    testUnion.setUnionId(UUID.randomUUID().toString());

    return testUnion;
  }

  public static TestUnionView toView(TestUnion testUnion) {
    TestUnionView view = new TestUnionView();

    view.setUnionId(testUnion.getUnionId());
    view.setSecretKey(testUnion.getSecretKey());

    return view;
  }
}
