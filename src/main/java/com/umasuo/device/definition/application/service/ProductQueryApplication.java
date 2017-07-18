package com.umasuo.device.definition.application.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.collect.Lists;
import com.umasuo.device.definition.application.dto.ProductDataView;
import com.umasuo.device.definition.application.dto.ProductView;
import com.umasuo.device.definition.application.dto.mapper.DeviceMapper;
import com.umasuo.device.definition.domain.model.Product;
import com.umasuo.device.definition.domain.service.ProductService;
import com.umasuo.device.definition.infrastructure.util.JsonUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * Created by umasuo on 17/6/1.
 */
@Service
public class ProductQueryApplication {

  /**
   * Logger.
   */
  private final static Logger logger = LoggerFactory.getLogger(ProductQueryApplication.class);

  /**
   * ProductService.
   */
  @Autowired
  private transient ProductService deviceService;

  /**
   * RestClient.
   */
  @Autowired
  private transient RestClient restClient;

  @Autowired
  private transient CacheApplication cacheApplication;

  /**
   * get device by id.
   *
   * @param id String
   */
  public ProductView get(String id, String developerId) {
    logger.debug("Enter. id: {}, developerId: {}.", id, developerId);

    ProductView result = cacheApplication.getProductById(developerId, id);

    if (result == null) {
      logger.debug("Cache fail, get from database.");

      List<ProductView> productViews = fetchProducts(developerId);

      result = productViews.stream().filter(view -> id.equals(view.getId())).findAny().orElse(null);
    }

    logger.debug("Exit. deviceView: {}.", result);
    return result;
  }

  /**
   * get all device define by developer id.
   *
   * @param developerId developer id
   * @return list of device view
   */
  public List<ProductView> getAllByDeveloperId(String developerId) {
    logger.debug("Enter. developerId: {}.", developerId);

    List<ProductView> result = cacheApplication.getProducts(developerId);

    if (result.isEmpty()) {
      logger.debug("Cache fail, get from database.");
      result = fetchProducts(developerId);
    }

    result.stream().forEach(
        productView -> productView.getDataDefinitions().stream().forEach(
            data -> data.setSchema(JsonUtils.deserialize(data.getDataSchema(), JsonNode.class))
        )
    );

    logger.trace("Devices: {}.", result);
    logger.debug("Exit. devicesSize: {}.", result.size());
    return result;
  }

  public List<ProductDataView> getDataDefinitions(String developerId, String productId) {
    logger.debug("Enter. developerId: {}, productId: {}.", developerId, productId);

    List<ProductDataView> dataViews = Lists.newArrayList();
    ProductView product = get(productId, developerId);

    if (product != null && product.getDataDefinitions() != null) {
      dataViews = product.getDataDefinitions();
    }

    logger.debug("Exit. dataDefinition size: {}.", dataViews.size());

    return dataViews;
  }

  /**
   * Get all open device define by developer id.
   * 接口比较少用，暂时不需要使用缓存。
   *
   * @param id developer id
   * @return list of device view
   */
  public List<ProductView> getAllOpenDevice(String id) {
    logger.debug("Enter. developerId: {}.", id);

    List<Product> devices = deviceService.getAllOpenProduct(id);
    List<ProductView> views = DeviceMapper.modelToView(devices);

    logger.debug("Exit. devicesSize: {}.", views.size());
    logger.trace("Devices: {}.", views);
    return views;
  }

  private List<ProductView> fetchProducts(String developerId) {
    logger.debug("Enter. developerId: {}.", developerId);

    List<Product> devices = deviceService.getByDeveloperId(developerId);

    List<ProductView> result = DeviceMapper.modelToView(devices);

    List<String> productIds = devices.stream().map(Product::getId).collect(Collectors.toList());

    Map<String, List<ProductDataView>> productDataViews =
        restClient.getProductData(developerId, productIds);

    mergeProductData(result, productDataViews);

    cacheApplication.cacheProducts(developerId, result);

    logger.debug("Exit. product size: {}.", result);

    return result;
  }

  private void mergeProductData(List<ProductView> productViews,
      Map<String, List<ProductDataView>> productDataViews) {
    logger.debug("Enter.");

    productViews.stream().forEach(
        product -> product.setDataDefinitions(ProductDataView.build(productDataViews.get(product.getId()))));

    logger.debug("Exit.");
  }
}
