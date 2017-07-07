package com.umasuo.device.definition.application.service.update;

import com.umasuo.device.definition.application.dto.CopyRequest;
import com.umasuo.device.definition.application.dto.action.CopyDataDefinition;
import com.umasuo.device.definition.application.service.DataDefinitionValidator;
import com.umasuo.device.definition.application.service.RestClient;
import com.umasuo.device.definition.domain.model.Device;
import com.umasuo.device.definition.infrastructure.update.UpdateAction;
import com.umasuo.device.definition.infrastructure.update.UpdateActionUtils;
import com.umasuo.exception.ParametersException;
import com.umasuo.model.Updater;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by umasuo on 17/6/1.
 * todo 暂时是对ProductType的数据定义
 */
@Service(value = UpdateActionUtils.COPY_DATA_DEFINITION)
public class CopyDataDefinitionService implements Updater<Device, UpdateAction> {

  /**
   * Logger.
   */
  private static final Logger LOG = LoggerFactory.getLogger(CopyDataDefinitionService.class);

  /**
   * The DataDefinitionValidator.
   */
  @Autowired
  private transient DataDefinitionValidator dataDefinitionValidator;

  @Autowired
  private transient RestClient restClient;

  @Override
  public void handle(Device entity, UpdateAction action) {
    List<String> dataDefinitionIds = ((CopyDataDefinition) action).getDataDefinitionIds();

    CopyRequest copyRequest = CopyRequest.build(entity.getId(), dataDefinitionIds, null);
    List<String> newDataDefinitionIds =
        restClient.copyDataDefinitions(entity.getDeveloperId(), copyRequest);

    if (newDataDefinitionIds.isEmpty()) {
      LOG.debug("Something wrong when copy dataDefinition.");
      throw new ParametersException("Can not copy dataDefinition");
    }

    entity.getDataDefineIds().addAll(newDataDefinitionIds);
  }

}
