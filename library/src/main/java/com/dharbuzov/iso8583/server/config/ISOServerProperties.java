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
package com.dharbuzov.iso8583.server.config;

import com.dharbuzov.iso8583.config.ISOBaseProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

/**
 * The properties to configure the server.
 *
 * @author Dmytro Harbuzov (dmytro.harbuzov@gmail.com).
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class ISOServerProperties extends ISOBaseProperties {

  // At least 1 thead to handle incoming messages
  public static final int DEFAULT_NUMBER_OF_THREADS = 1;

  private int numberOfThreads;

  /**
   * Gets configured number of threads or default.
   *
   * @return number of threads of default {@link ISOServerProperties#DEFAULT_NUMBER_OF_THREADS}
   */
  public int getNumberOfThreadsOrDefault() {
    if (numberOfThreads < 1) {
      return DEFAULT_NUMBER_OF_THREADS;
    }
    return numberOfThreads;
  }

  /**
   * Overrides lombok {@link Builder} class, by adding additional methods.
   public static class ISOServerPropertiesBuilder {

   public
   }*/
}
