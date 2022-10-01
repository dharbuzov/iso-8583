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

import com.dharbuzov.iso8583.channel.ChannelType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * The base properties which are applicable for the client as well as server.
 *
 * @author Dmytro Harbuzov (dmytro.harbuzov@gmail.com).
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public abstract class ISOBaseProperties {

  private ISOMessageProperties messages;

  private ISOConnProperties connection;
  private ChannelType channelType;

  private ISOThreadProperties thread;

  /**
   * Gets the defined channel type of default one.
   *
   * @return channel type of default {@link ChannelType#NETTY}
   */
  public ChannelType getChannelTypeOrDefault() {
    if (this.channelType == null) {
      return ChannelType.defaultChannelType();
    }
    return this.channelType;
  }
}
