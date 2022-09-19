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
package com.dharbuzov.iso8583.client.config;

import com.dharbuzov.iso8583.config.ISOBaseProperties;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author Dmytro Harbuzov (dmytro.harbuzov@gmail.com).
 */
@Data
@Builder
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class ISOClientProperties extends ISOBaseProperties {

  public static long DEFAULT_REQUEST_TIMEOUT = 60000;

  private String name;

  private boolean keepAlive;

  private ISOReconnectProperties reconnect;

  private long requestTimeoutMs;

  public static class ISOClientPropertiesBuilder {

  }

  public long getRequestTimeoutMsOrDefault() {
    return requestTimeoutMs == 0L ? DEFAULT_REQUEST_TIMEOUT: requestTimeoutMs;
  }
}
