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

  private String host;

  private int port;

  /**
   * Gets the inet socket address based on provided host and port.
   *
   * @return inet socket address
   */
  public InetSocketAddress getInetSocketAddress() {
    return new InetSocketAddress(StringUtils.isEmpty(host) ? "localhost" : host, port);
  }
}
