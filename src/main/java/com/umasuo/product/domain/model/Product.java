package com.umasuo.product.domain.model;

import com.umasuo.product.infrastructure.enums.NetType;
import com.umasuo.product.infrastructure.enums.ProductStatus;

import lombok.Data;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Version;

/**
 * Entity class for Product.
 */
@Entity
@Table(name = "product")
@Data
@EntityListeners(AuditingEntityListener.class)
public class Product implements Serializable {

  /**
   * The serialVersionUID.
   */
  private static final long serialVersionUID = -6085138635403490262L;

  /**
   * The id.
   * Created by database when insert.
   */
  @Id
  @GeneratedValue(generator = "uuid")
  @GenericGenerator(name = "uuid", strategy = "uuid2")
  @Column(name = "id")
  private String id;

  /**
   * The Created at.
   */
  @CreatedDate
  @Column(name = "created_at")
  protected Long createdAt;

  /**
   * The Last modified at.
   */
  @LastModifiedDate
  @Column(name = "last_modified_at")
  protected Long lastModifiedAt;

  /**
   * Version used for update date check.
   */
  @Version
  private Integer version;

  /**
   * Which developer this kind build product belong to.
   */
  private String developerId;

  /**
   * Product status: developing, published, revoked.
   */
  private ProductStatus status;

  /**
   * Name build the product.
   */
  @Column(nullable = false)
  private String name;

  /**
   * Product icon.
   */
  private String icon;

  /**
   * 产品类型，新建产品时选择的系统预设产品类型，或者是自定义的类型。
   */
  private String productType;

  /**
   * 开发者自定义的设备型号。
   */
  private String model;

  /**
   * 产品的详细介绍。
   * 长度限制为65535。
   */
  @Column(length = 65535)
  private String description;

  /**
   * 产品的固件版本信息。
   */
  private String firmwareVersion;

  /**
   * 数据定义ID，需要提前定义好不同的数据类型.
   */
  @ElementCollection
  private List<String> dataDefineIds;

  /**
   * 产品的功能列表。
   * 使用orphanRemoval是为了可以在product这边把function删除。
   */
  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
  @OrderBy("functionId")
  private List<ProductFunction> productFunctions;

  /**
   * Product net type, identify by how the communicate with other services(app, cloud)
   */
  private NetType communicationType;

  /**
   * Wifi模组型号。
   */
  private String wifiModule;

  /**
   * Open status about this product.
   * True means this product can be find by other developers and false means not.
   */
  private Boolean openable = false;

  /**
   * 用于开发测试的UnionId。
   */
  @Embedded
  private TestUnion testUnion = new TestUnion();
}
