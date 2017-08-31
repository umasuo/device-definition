package com.umasuo.product.infrastructure.validator;

import com.umasuo.exception.AlreadyExistException;
import com.umasuo.product.application.dto.ProductDataView;
import com.umasuo.product.application.dto.ProductTypeView;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * 用于判断DataId是否合法.
 */
public final class DataIdValidator {

  /**
   * Logger.
   */
  private static final Logger LOG = LoggerFactory.getLogger(DataIdValidator.class);

  /**
   * Private constructor.
   */
  private DataIdValidator() {
  }

  /**
   * 在产品添加数据定义的时候使用，判断要添加的dataId是否合法：对应的ProductType没有该dataId，已存在的data没有该id。
   *
   * @param dataId the dataId
   * @param productType the ProductType
   * @param productDataViews list of ProductData
   */
  public static void checkForAdd(String dataId, ProductTypeView productType,
      List<ProductDataView> productDataViews) {
    LOG.debug("Enter. dataId: {}.", dataId);

    boolean sameAsPlatformData = existPlatformDataId(dataId, productType);

    boolean existDataId =
        productDataViews.stream().anyMatch(data -> dataId.equals(data.getDataId()));

    existDataId(dataId, sameAsPlatformData, existDataId);
    LOG.debug("Exit.");
  }


  /**
   * 在产品更新数据定义的时候使用，判断要添加的dataId是否合法：对应的ProductType没有该dataId，已存在的其他data没有该id。
   *
   * @param dataDefinitionId the data definition id
   * @param dataId the data id
   * @param productType the product type
   * @param productDataViews the product data views
   */
  public static void checkForUpdate(String dataDefinitionId, String dataId,
      ProductTypeView productType, List<ProductDataView> productDataViews) {
    LOG.debug("Enter. dataId: {}.", dataId);
    boolean sameAsPlatformData = existPlatformDataId(dataId, productType);

    boolean existDataId =
        productDataViews.stream().anyMatch(
            data -> dataId.equals(data.getDataId()) && !dataDefinitionId.equals(data.getId()));

    existDataId(dataId, sameAsPlatformData, existDataId);
    LOG.debug("Exit.");
  }


  /**
   * Exist platform data id boolean.
   *
   * @param dataId the data id
   * @param productType the product type
   * @return the boolean
   */
  public static boolean existPlatformDataId(String dataId, ProductTypeView productType) {
    LOG.debug("Enter. dataId: {}.", dataId);
    boolean sameAsPlatformData = false;

    if (!CollectionUtils.isEmpty(productType.getData())) {
      sameAsPlatformData =
          productType.getData().stream().anyMatch(data -> dataId.equals(data.getDataId()));
    }

    LOG.debug("Exit. result: {}.", sameAsPlatformData);

    return sameAsPlatformData;
  }

  /**
   * 根据ProductType的dataId情况和已存在的DataDefinition的情况，判断dataId是否合法。
   *
   * @param dataId the dataId
   * @param sameAsPlatformData ProductType的dataId情况
   * @param existDataId DataDefinition的情况
   */
  private static void existDataId(String dataId, boolean sameAsPlatformData, boolean existDataId) {
    if (sameAsPlatformData || existDataId) {
      LOG.debug("Can not add exist dataId: {}.", dataId);
      throw new AlreadyExistException("DataId already exist");
    }
  }
}
