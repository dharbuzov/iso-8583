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
package com.dharbuzov.iso8583.server;

import com.dharbuzov.iso8583.channel.ISOServerChannel;
import com.dharbuzov.iso8583.server.config.ISOServerProperties;

/**
 * Default implementation of {@link ISOServer} which works properly. This class could be easily
 * extended based on specific needs.
 *
 * @author Dmytro Harbuzov (dmytro.harbuzov@gmail.com).
 */
public class ISODefaultServer implements ISOServer {

  protected final ISOServerProperties serverProperties;
  protected final ISOServerChannel serverChannel;

  public ISODefaultServer(ISOServerProperties serverProperties, ISOServerChannel serverChannel) {
    this.serverProperties = serverProperties;
    this.serverChannel = serverChannel;
  }

  @Override
  public void start() {
    this.serverChannel.start();
  }

  @Override
  public boolean isRunning() {
    return serverChannel.isRunning();
  }

  @Override
  public void shutdown() {
    serverChannel.shutdown();
  }
}
