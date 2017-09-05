package com.umasuo.product.application.dto.mapper;

import com.umasuo.product.application.dto.TestUnionView;
import com.umasuo.product.domain.model.TestUnion;

import org.apache.commons.lang3.RandomStringUtils;

import java.util.UUID;

/**
 * Mapper class for Union.
 */
public final class UnionMapper {

  /**
   * The length build secret key.
   */
  private static final int SECRET_KEY_LENGTH = 7;

  /**
   * Private constructor.
   */
  private UnionMapper() {
  }

  /**
   * Build a new TestUnion.
   *
   * @return TestUnion test union
   */
  public static TestUnion build() {
    TestUnion testUnion = new TestUnion();

    testUnion.setSecretKey(RandomStringUtils.randomAlphanumeric(SECRET_KEY_LENGTH));
    testUnion.setUnionId(UUID.randomUUID().toString());

    return testUnion;
  }

  /**
   * Convert TestUnion to TestUnionView.
   *
   * @param testUnion the test union
   * @return the test union view
   */
  public static TestUnionView toView(TestUnion testUnion) {
    TestUnionView view = new TestUnionView();

    if (testUnion != null) {
      view.setUnionId(testUnion.getUnionId());
      view.setSecretKey(testUnion.getSecretKey());
    }

    return view;
  }
}
