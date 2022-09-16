/*
 * Copyright (c) 2022 Paydock, Inc. All rights reserved. Paydock Confidential
 */

package com.dharbuzov.iso8583.exception;

/**
 * @author Dmytriy Harbuzov (dmitriy.harbuzov@paydock.com).
 */
public class ISOException extends RuntimeException {

  public ISOException(String message) {
    super(message);
  }

  public ISOException(String formatMessage, Object... args) {
    super(String.format(formatMessage, args));
  }
}
