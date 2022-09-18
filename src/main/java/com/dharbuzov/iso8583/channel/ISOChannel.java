package com.dharbuzov.iso8583.channel;

/**
 * @author Dmytro Harbuzov (dmytro.harbuzov@gmail.com).
 */
public interface ISOChannel {

  /**
   * Returns the flag which indicates that channel is active.
   *
   * @return {@code true} if channel is active, otherwise {@code false}
   */
  boolean isActive();
}
