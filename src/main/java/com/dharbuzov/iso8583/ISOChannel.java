/*
 * Copyright (c) 2022 Paydock, Inc. All rights reserved. Paydock Confidential
 */

package com.dharbuzov.iso8583;

/**
 * @author Dmytro Harbuzov (dmytro.harbuzov@gmail.com).
 */
public interface ISOChannel {
  long DEFAULT_TIMEOUT_MILLIS = 10 * 3600;

  void connect();

  boolean isConnected();

  void send(byte[] bytes);

  byte[] receive();
}
