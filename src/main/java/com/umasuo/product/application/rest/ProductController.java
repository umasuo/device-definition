package com.umasuo.product.application.rest;

import com.umasuo.product.application.dto.ProductDraft;
import com.umasuo.product.application.dto.ProductView;
import com.umasuo.product.application.service.ProductCommandApplication;
import com.umasuo.product.application.service.ProductQueryApplication;
import com.umasuo.product.infrastructure.Router;
import com.umasuo.product.infrastructure.update.UpdateRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import javax.validation.Valid;

/**
 * Created by umasuo on 17/6/1.
 */
@CrossOrigin
@RestController
public class ProductController {

  /**
   * Logger.
   */
  private final static Logger logger = LoggerFactory.getLogger(ProductController.class);

  /**
   * ProductCommandApplication.
   */
  @Autowired
  private transient ProductCommandApplication commandApplication;

  @Autowired
  private transient ProductQueryApplication queryApplication;

  /**
   * create new product.
   *
   * @param draft Product draft
   * @param developerId the developer id
   * @return product view
   */
  @PostMapping(Router.PRODUCT_ROOT)
  public ProductView create(@RequestHeader("developerId") String developerId,
      @RequestBody @Valid ProductDraft draft) {
    logger.info("Enter. developerId: {}, productDraft: {}.", developerId, draft);

    ProductView view = commandApplication.create(draft, developerId);

    logger.info("Exit. productView: {}.", view);

    return view;
  }

  @DeleteMapping(Router.PRODUCT_WITH_ID)
  public void delete(@PathVariable("id") String id, @RequestHeader String developerId,
      @RequestParam("version") Integer version) {
    logger.info("Enter. id: {}, developerId: {}, version: {}.", id, developerId, version);

    commandApplication.delete(id, developerId, version);

    logger.info("Exit.");
  }

  /**
   * Update Product view.
   *
   * @param id the Product id
   * @param updateRequest the update request
   * @param developerId the developer id
   * @return the ProductView
   */
  @PutMapping(Router.PRODUCT_WITH_ID)
  public ProductView update(@PathVariable("id") String id, @RequestHeader String developerId,
      @RequestBody @Valid UpdateRequest updateRequest) {
    logger.info("Enter. productId: {}, updateRequest: {}, developerId: {}.",
        id, updateRequest, developerId);

    ProductView result = commandApplication
        .update(id, developerId, updateRequest.getVersion(), updateRequest.getActions());

    logger.trace("Updated product: {}.", result);
    logger.info("Exit.");

    return result;
  }

  /**
   * get product definition by product id.
   *
   * @param id String
   * @param developerId the developer id
   * @return ProductView product view
   */
  @GetMapping(Router.PRODUCT_WITH_ID)
  public ProductView get(@PathVariable String id, @RequestHeader String developerId) {
    logger.info("Enter. id: {}.", id);

    ProductView view = queryApplication.get(id, developerId);

    logger.info("Exit. view: {}.", view);
    return view;
  }

  /**
   * get all developer's product definition by developer id.
   *
   * @param developerId String
   * @return list of product view
   */
  @GetMapping(Router.PRODUCT_ROOT)
  public List<ProductView> getByDeveloperId(@RequestHeader String developerId) {
    logger.info("Enter. developerId: {}.", developerId);

    List<ProductView> views = queryApplication.getAllByDeveloperId(developerId);

    logger.info("Exit. viewsSize: {}.", views.size());
    logger.trace("views: {}.", views);
    return views;
  }

  /**
   * Gets all open product.
   *
   * @param developerId the developer id
   * @return the all open product
   */

  @GetMapping(value = Router.PRODUCT_ROOT, params = {"isOpen", "developerId"})
  public List<ProductView> getAllOpenProduct(@RequestParam String developerId, @RequestParam
      Boolean isOpen) {
    logger.info("Enter. developerId: {}.", developerId);
    //todo
    List<ProductView> views = queryApplication.getAllOpenProduct(developerId);

    logger.info("Exit. viewsSize: {}.", views.size());
    logger.trace("views: {}.", views);

    return views;
  }

  @GetMapping("/v1/admin/products/count")
  public Long countProducts() {
    logger.info("Enter.");

    Long count = queryApplication.countProducts();

    logger.info("Exit. product countProducts: {}.", count);

    return count;
  }
}
