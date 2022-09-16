/*
 * Copyright (c) 2022 Paydock, Inc. All rights reserved. Paydock Confidential
 */

package com.dharbuzov.iso8583;

import com.dharbuzov.iso8583.model.ISOMessage;

/**
 * @author Dmytriy Harbuzov (dmitriy.harbuzov@paydock.com).
 */
public interface ISOClient {

  String DEFAULT_CLIENT_NAME_FORMAT = "%s-%s-%s";

  void connect();

  boolean isConnected();

  void sendAsync(ISOMessage message);

  ISOMessage send(ISOMessage message);

  ISOChannel getChannel();
}
