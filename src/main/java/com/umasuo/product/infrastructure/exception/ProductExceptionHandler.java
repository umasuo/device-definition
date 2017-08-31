package com.umasuo.product.infrastructure.exception;

import com.umasuo.exception.AlreadyExistException;
import com.umasuo.exception.NotExistException;
import com.umasuo.exception.handler.ExceptionHandler;
import com.umasuo.util.JsonUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * ExceptionHandler class.
 */
@Component
public class ProductExceptionHandler implements ExceptionHandler, HandlerExceptionResolver {

  /**
   * Logger.
   */
  private static final Logger LOG = LoggerFactory.getLogger(ProductExceptionHandler.class);

  /**
   * Resolve exception.
   *
   * @param request the HttpServletRequest
   * @param response the HttpServletResponse
   * @param handler the handler
   * @param ex the Exception
   * @return ModelAndView
   */
  @Override
  public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response,
      Object handler, Exception ex) {
    setResponse(request, response, handler, ex);
    addExceptionBody(response, ex);
    return new ModelAndView();
  }

  /**
   * Add customized message body to the response.
   *
   * @param response the HttpServletResponse
   * @param ex the Exception
   */
  private void addExceptionBody(HttpServletResponse response, Exception ex) {
    try {
      ExceptionBody body = getBody(ex);
      if (body != null) {
        response.getWriter().print(JsonUtils.serialize(body));
      }
    } catch (IOException e) {
      LOG.error("failed to write response JSON", e);
      throw new IllegalStateException(e);
    }
  }

  /**
   * Get customized message body by exception type.
   *
   * @param ex exception.
   * @return exception body.
   */
  private ExceptionBody getBody(Exception ex) {
    ExceptionBody body = null;
    if (ex instanceof NotExistException) {
      body = ExceptionBody.build(ExceptionBody.NOT_EXIST_CODE, ex.getMessage());
    }
    if (ex instanceof AlreadyExistException) {
      body = ExceptionBody.build(ExceptionBody.ALREADY_EXIST_CODE, ex.getMessage());
    }
    return body;
  }
}
