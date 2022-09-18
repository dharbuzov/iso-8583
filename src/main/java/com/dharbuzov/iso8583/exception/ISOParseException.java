package com.dharbuzov.iso8583.exception;

/**
 * @author Dmytro Harbuzov (dmytro.harbuzov@gmail.com).
 */
public class ISOParseException extends ISOException {

  public ISOParseException(String message) {
    super(message);
  }

  public ISOParseException(String formatMessage, Object... args) {
    super(formatMessage, args);
  }
}
