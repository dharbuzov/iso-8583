/*
 * Copyright (c) 2022 Paydock, Inc. All rights reserved. Paydock Confidential
 */

package com.dharbuzov.iso8583;

import java.util.List;

/**
 * @author Dmytro Harbuzov (dmytro.harbuzov@gmail.com).
 */
public interface ISOServer {

  /**
   * Starts the server instance.
   */
  void start();

  /**
   * Returns flag which indicates that server is running.
   *
   * @return {@code true} if server is running, otherwise {@code false}
   */
  boolean isRunning();

  /**
   * Method for shutting down the server.
   */
  void shutdown();

  /**
   * Gets all channels connected to the server.
   *
   * @return list of all channels connected to the server.
   */
  List<ISOChannel> getChannels();
}
