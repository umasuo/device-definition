package com.umasuo.device.definition.domain.service;

import com.google.common.collect.Lists;
import com.umasuo.device.definition.application.dto.ProductTypeView;
import com.umasuo.device.definition.application.dto.mapper.ProductTypeMapper;
import com.umasuo.device.definition.domain.model.ProductType;
import com.umasuo.device.definition.infrastructure.repository.ProductTypeRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Davis on 17/6/28.
 */
@Service
public class ProductTypeService {

  /**
   * Logger.
   */
  private static final Logger LOG = LoggerFactory.getLogger(ProductTypeService.class);

  @Autowired
  private transient ProductTypeRepository repository;

  /**
   * 查询所有的产品类型。
   *
   * @return
   */
  public List<ProductType> getAll() {
    LOG.debug("Enter.");

    List<ProductType> productTypes = repository.findAll();


    LOG.debug("Exit. productType size: {}.", productTypes.size());

    return productTypes;
  }
}
