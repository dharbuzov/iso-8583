package com.dharbuzov.iso8583.config;

import java.net.InetSocketAddress;

import com.dharbuzov.iso8583.util.StringUtils;

import lombok.Builder;
import lombok.Data;

/**
 * @author Dmytro Harbuzov (dmytro.harbuzov@gmail.com).
 */
@Data
@Builder
public class ISOConnProperties {

  private String host;

  private int port;

  public InetSocketAddress getInetSocketAddress() {
    return new InetSocketAddress(StringUtils.isEmpty(host) ? "localhost" : host, port);
  }
}
