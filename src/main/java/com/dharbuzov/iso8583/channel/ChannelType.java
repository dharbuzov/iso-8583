package com.dharbuzov.iso8583.channel;

/**
 * @author Dmytro Harbuzov (dmytro.harbuzov@gmail.com).
 */
public enum ChannelType {
  NETTY;

  /**
   * Returns the default channel type.
   *
   * @return {@link ChannelType#NETTY}
   */
  public static ChannelType defaultChannelType() {
    return ChannelType.NETTY;
  }
}
