package com.umasuo.device.definition.application.service;

import com.umasuo.device.definition.application.dto.CommonDataView;
import com.umasuo.device.definition.application.dto.ProductTypeView;
import com.umasuo.device.definition.application.dto.mapper.ProductTypeMapper;
import com.umasuo.device.definition.domain.model.ProductType;
import com.umasuo.device.definition.domain.service.ProductTypeService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by Davis on 17/6/28.
 */
@Service
public class ProductTypeApplication {

  /**
   * Logger.
   */
  private static final Logger LOG = LoggerFactory.getLogger(ProductTypeApplication.class);

  /**
   * ProductType service.
   */
  @Autowired
  private transient ProductTypeService productTypeService;

  /**
   * RestClient.
   * 用于访问data-definition，获取产品类别对应的数据定义。
   */
  @Autowired
  private transient RestClient restClient;

  @Autowired
  private transient CacheApplication cacheApplication;

  /**
   * 查询所有的产品类型。
   */
  public List<ProductTypeView> getAll() {
    LOG.debug("Enter.");

    // 1. get from redis
    List<ProductTypeView> cacheProductTypes = cacheApplication.getAllProductType();

    if (cacheProductTypes.isEmpty()) {
      LOG.debug("Cache fail. Get from database.");
      List<ProductType> productTypes = productTypeService.getAll();

      // 调用data-definition的api获取对应id的CommonDataView
      Map<String, List<CommonDataView>> dataDefinitionViews = restClient.getPlatformDataDefinition();

      cacheProductTypes = ProductTypeMapper.toModel(productTypes, dataDefinitionViews);

      cacheApplication.batchCacheProductType(cacheProductTypes);
    }

    LOG.debug("Exit. productType size: {}.", cacheProductTypes.size());
    return cacheProductTypes;
  }
}
