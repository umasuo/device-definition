package com.umasuo.product.infrastructure.validator;

import com.umasuo.exception.AlreadyExistException;
import com.umasuo.product.application.dto.ProductDataView;
import com.umasuo.product.application.dto.ProductTypeView;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * Created by Davis on 17/8/15.
 */
public final class DataIdValidator {

  /**
   * Logger.
   */
  private static final Logger LOG = LoggerFactory.getLogger(DataIdValidator.class);

  /**
   * Instantiates a new Data id validator.
   */
  private DataIdValidator() {
  }

  public static void checkForAdd(String dataId, ProductTypeView productType,
      List<ProductDataView> productDataViews) {
    boolean sameAsPlatformData = existPlatformDataId(dataId, productType);

    boolean existDataId =
        productDataViews.stream().anyMatch(data -> dataId.equals(data.getDataId()));

    existDataId(dataId, sameAsPlatformData, existDataId);
  }

  public static void checkForUpdate(String dataDefinitionId, String dataId,
      ProductTypeView productType, List<ProductDataView> productDataViews) {
    boolean sameAsPlatformData = existPlatformDataId(dataId, productType);

    boolean existDataId =
        productDataViews.stream().anyMatch(
            data -> dataId.equals(data.getDataId()) && !dataDefinitionId.equals(data.getId()));

    existDataId(dataId, sameAsPlatformData, existDataId);
  }

  private static boolean existPlatformDataId(String dataId, ProductTypeView productType) {
    boolean sameAsPlatformData = false;
    if (! CollectionUtils.isEmpty(productType.getData())) {
      sameAsPlatformData =
          productType.getData().stream().anyMatch(data -> dataId.equals(data.getDataId()));
    }
    return sameAsPlatformData;
  }

  private static void existDataId(String dataId, boolean sameAsPlatformData, boolean existDataId) {
    if (sameAsPlatformData || existDataId) {
      LOG.debug("Can not add exist dataId: {}.", dataId);
      throw new AlreadyExistException("DataId already exist");
    }
  }
}
