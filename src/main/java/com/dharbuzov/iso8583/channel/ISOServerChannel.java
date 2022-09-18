package com.dharbuzov.iso8583.channel;

/**
 * @author Dmytro Harbuzov (dmytro.harbuzov@gmail.com).
 */
public interface ISOServerChannel extends ISOChannel {

  void start();

  void shutdown();

  boolean isRunning();

  @Override
  default boolean isActive() {
    return isRunning();
  }
}
