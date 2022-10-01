/*
 * Copyright 2022.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.dharbuzov.iso8583.config;

import java.net.InetSocketAddress;
import java.util.Optional;

import com.dharbuzov.iso8583.util.StringUtils;

import lombok.Builder;
import lombok.Data;

/**
 * Represents the connection properties.
 *
 * @author Dmytro Harbuzov (dmytro.harbuzov@gmail.com).
 */
@Data
@Builder
public class ISOConnProperties {

  public static final String DEFAULT_HOST = "localhost";
  public static boolean DEFAULT_NO_DELAY = true;
  public static boolean DEFAULT_KEEP_ALIVE = true;

  private String host;

  private int port;

  private Boolean noDelay;

  private Boolean keepAlive;

  /**
   * Gets the inet socket address based on provided host and port.
   *
   * @return inet socket address
   */
  public InetSocketAddress getInetSocketAddress() {
    return new InetSocketAddress(StringUtils.isEmpty(host) ? DEFAULT_HOST : host, port);
  }

  /**
   * Returns the 'noDelay' property or default.
   *
   * @return noDelay flag or default {@link ISOConnProperties#DEFAULT_NO_DELAY}
   */
  public boolean isNoDelayOrDefault() {
    return Optional.ofNullable(noDelay).orElse(DEFAULT_NO_DELAY);
  }

  /**
   * Returns the 'keepAlive' property or default.
   *
   * @return keepAlive flag or default {@link ISOConnProperties#DEFAULT_KEEP_ALIVE}
   */
  public boolean isKeepAliveOrDefault() {
    return Optional.ofNullable(keepAlive).orElse(DEFAULT_KEEP_ALIVE);
  }
}
