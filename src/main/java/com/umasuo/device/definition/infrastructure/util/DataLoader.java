package com.umasuo.device.definition.infrastructure.util;

import com.google.common.collect.Lists;
import com.umasuo.device.definition.domain.model.CommonFunction;
import com.umasuo.device.definition.domain.model.ProductType;
import com.umasuo.device.definition.infrastructure.repository.ProductTypeRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by Davis on 17/6/28.
 */
@Component
public class DataLoader implements ApplicationRunner{

  /**
   * Logger.
   */
  private static final Logger LOG = LoggerFactory.getLogger(DataLoader.class);

  @Autowired
  private ProductTypeRepository repository;

  @Override
  public void run(ApplicationArguments args) throws Exception {
    long count = repository.count();
    if (count > 0) {
      LOG.info("There is {} product types, don't need to insert data.", count);
    } else {
      LOG.info("There is no any product types, should insert data.");
      repository.save(getInitData());
    }
  }

  private List<ProductType> getInitData() {

    ProductType productType = new ProductType();

    productType.setName("冰箱");
    productType.setGroupName("大家电");

    CommonFunction cf1 = new CommonFunction();

    cf1.setFunctionId("cf001");
    cf1.setName("开关");
    cf1.setCommand("开关");

    CommonFunction cf2 = new CommonFunction();

    cf2.setFunctionId("cf002");
    cf2.setName("工作模式");
    cf2.setCommand("工作模式");

    productType.setFunctions(Lists.newArrayList(cf1, cf2));

    return Lists.newArrayList(productType);
  }
}
