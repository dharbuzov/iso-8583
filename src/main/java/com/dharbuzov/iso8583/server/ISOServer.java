

package com.dharbuzov.iso8583.server;

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
}
