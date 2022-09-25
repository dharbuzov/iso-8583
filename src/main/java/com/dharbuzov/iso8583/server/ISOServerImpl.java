/*
 * Copyright (c) 2022 Paydock, Inc. All rights reserved. Paydock Confidential
 */

package com.dharbuzov.iso8583.server;

import java.util.List;

import com.dharbuzov.iso8583.ISOChannel;
import com.dharbuzov.iso8583.ISOServer;

/**
 * @author Dmytro Harbuzov (dmytro.harbuzov@gmail.com).
 */
public class ISOServerImpl implements ISOServer {

  @Override
  public void start() {

  }

  @Override
  public boolean isRunning() {
    return false;
  }

  @Override
  public void shutdown() {

  }

  @Override
  public List<ISOChannel> getChannels() {
    return null;
  }
}
