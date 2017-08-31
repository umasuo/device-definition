package com.umasuo.product.infrastructure.exception;

/**
 * Exception body.
 * Return customized code and message to the client.
 */
public class ExceptionBody {

  /**
   * Code for NotExistException.
   */
  public static final int NOT_EXIST_CODE = 10001;

  /**
   * Code for AlreadyExistException.
   */
  public static final int ALREADY_EXIST_CODE = 10002;

  /**
   * CODE.
   */
  private int code;

  /**
   * Message
   */
  private String message;

  /**
   * Gets code.
   *
   * @return the code
   */
  public int getCode() {
    return code;
  }

  /**
   * Sets code.
   *
   * @param code the code
   */
  public void setCode(int code) {
    this.code = code;
  }

  /**
   * Gets message.
   *
   * @return the message
   */
  public String getMessage() {
    return message;
  }

  /**
   * Sets message.
   *
   * @param message the message
   */
  public void setMessage(String message) {
    this.message = message;
  }

  /**
   * Build one ExceptionBody.
   *
   * @param code the Code
   * @param message the Message
   * @return ExceptionBody exception body
   */
  public static ExceptionBody build(int code, String message) {
    ExceptionBody body = new ExceptionBody();
    body.code = code;
    body.message = message;
    return body;
  }
}
