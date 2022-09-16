/*
 * Copyright (c) 2022 Paydock, Inc. All rights reserved. Paydock Confidential
 */

package com.dharbuzov.iso8583;

import com.dharbuzov.iso8583.model.ISOMessage;

/**
 * @author Dmytro Harbuzov (dmytro.harbuzov@gmail.com).
 */
public interface ISOClient {

  String DEFAULT_CLIENT_NAME_FORMAT = "%s-%s-%s";

  void connect();

  boolean isConnected();

  void sendAsync(ISOMessage message);

  ISOMessage send(ISOMessage message);

  ISOChannel getChannel();
}
