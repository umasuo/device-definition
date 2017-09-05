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
 * Controller class for Product.
 */
@CrossOrigin
@RestController
public class ProductController {

  /**
   * Logger.
   */
  private final static Logger LOG = LoggerFactory.getLogger(ProductController.class);

  /**
   * ProductCommandApplication.
   */
  @Autowired
  private transient ProductCommandApplication commandApplication;

  /**
   * ProductQueryApplication.
   */
  @Autowired
  private transient ProductQueryApplication queryApplication;

  /**
   * Create new product.
   *
   * @param developerId the developer id
   * @param draft Product draft
   * @return product view
   */
  @PostMapping(Router.PRODUCT_ROOT)
  public ProductView create(@RequestHeader("developerId") String developerId,
      @RequestBody @Valid ProductDraft draft) {
    LOG.info("Enter. developerId: {}, productDraft: {}.", developerId, draft);

    ProductView view = commandApplication.create(draft, developerId);

    LOG.info("Exit. productView: {}.", view);

    return view;
  }

  /**
   * Delete a product by it's id.
   *
   * @param id the id
   * @param developerId the developer id
   * @param version the version
   */
  @DeleteMapping(Router.PRODUCT_WITH_ID)
  public void delete(@PathVariable("id") String id, @RequestHeader String developerId,
      @RequestParam("version") Integer version) {
    LOG.info("Enter. id: {}, developerId: {}, version: {}.", id, developerId, version);

    commandApplication.delete(id, developerId, version);

    LOG.info("Exit.");
  }

  /**
   * Update Product.
   *
   * @param id the Product id
   * @param developerId the developer id
   * @param updateRequest the update request
   * @return the ProductView
   */
  @PutMapping(Router.PRODUCT_WITH_ID)
  public ProductView update(@PathVariable("id") String id, @RequestHeader String developerId,
      @RequestBody @Valid UpdateRequest updateRequest) {
    LOG.info("Enter. productId: {}, updateRequest: {}, developerId: {}.",
        id, updateRequest, developerId);

    ProductView result = commandApplication
        .update(id, developerId, updateRequest.getVersion(), updateRequest.getActions());

    LOG.trace("Updated product: {}.", result);
    LOG.info("Exit.");

    return result;
  }

  /**
   * Get product by it's id.
   *
   * @param id String
   * @param developerId the developer id
   * @return ProductView product view
   */
  @GetMapping(Router.PRODUCT_WITH_ID)
  public ProductView get(@PathVariable String id, @RequestHeader String developerId) {
    LOG.info("Enter. id: {}, developerId: {}.", id, developerId);

    ProductView view = queryApplication.get(id, developerId);

    LOG.info("Exit. view: {}.", view);
    return view;
  }

  /**
   * Query product by it's id and developerId.
   *
   * @param id the product id
   * @param developerId the developer id
   * @return ProductView
   */
  @GetMapping(Router.ADMIN_PRODUCT_WITH_ID)
  public ProductView getForAdmin(@PathVariable String id, @RequestParam String developerId) {
    LOG.info("Enter. id: {}, developerId: {}.", id, developerId);

    ProductView view = queryApplication.get(id, developerId);

    LOG.info("Exit. view: {}.", view);
    return view;
  }

  /**
   * Get all developer's product by developer id.
   *
   * @param developerId String
   * @return list build product view
   */
  @GetMapping(Router.PRODUCT_ROOT)
  public List<ProductView> getByDeveloperId(@RequestHeader String developerId) {
    LOG.info("Enter. developerId: {}.", developerId);

    List<ProductView> views = queryApplication.getAllByDeveloperId(developerId);

    LOG.info("Exit. viewsSize: {}.", views.size());
    LOG.trace("views: {}.", views);
    return views;
  }

  /**
   * Gets all open product.
   *
   * @param developerId the developer id
   * @param isOpen the is open
   * @return the all open product
   */
  @GetMapping(value = Router.PRODUCT_ROOT, params = {"isOpen", "developerId"})
  public List<ProductView> getAllOpenProduct(@RequestParam String developerId, @RequestParam
      Boolean isOpen) {
    LOG.info("Enter. developerId: {}.", developerId);
    //todo
    List<ProductView> views = queryApplication.getAllOpenProduct(developerId);

    LOG.info("Exit. viewsSize: {}.", views.size());
    LOG.trace("views: {}.", views);

    return views;
  }

  /**
   * Count products.
   *
   * @return the long
   */
  @GetMapping(value = Router.ADMIN_PRODUCT_COUNT)
  public Long countProducts() {
    LOG.info("Enter.");

    Long count = queryApplication.countProducts();

    LOG.info("Exit. product countProducts: {}.", count);

    return count;
  }
}
