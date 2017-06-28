package com.umasuo.device.definition.application.service;

import com.google.common.collect.Lists;
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

/**
 * Created by Davis on 17/6/28.
 */
@Service
public class ProductTypeApplication {

  /**
   * Logger.
   */
  private static final Logger LOG = LoggerFactory.getLogger(ProductTypeApplication.class);

  @Autowired
  private transient ProductTypeService productTypeService;

  @Autowired
  private transient RestClient restClient;

  public List<ProductTypeView> getAll() {
    LOG.debug("Enter.");

    List<ProductType> productTypes = productTypeService.getAll();


    List<String> dataDefinitionIds = getDataDefinitionIds(productTypes);

    List<CommonDataView> dataDefinitionViews = Lists.newArrayList();

    if (!dataDefinitionIds.isEmpty()) {
      dataDefinitionViews = restClient.getDataDefinitions(dataDefinitionIds);
    }

    List<ProductTypeView> result = ProductTypeMapper.toModel(productTypes, dataDefinitionViews);

    LOG.debug("Exit. productType size: {}.", result.size());

    return result;
  }

  private List<String> getDataDefinitionIds(List<ProductType> productTypes) {
    List<String> dataDefinitionIds = Lists.newArrayList();

    productTypes.stream().forEach(
        productType -> dataDefinitionIds.addAll(productType.getDataIds())
    );

    return dataDefinitionIds;
  }
}
