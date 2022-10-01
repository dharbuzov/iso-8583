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
package com.dharbuzov.iso8583.factory;

import java.nio.charset.Charset;

import com.dharbuzov.iso8583.model.ISOMessage;
import com.dharbuzov.iso8583.model.MessageSource;
import com.dharbuzov.iso8583.model.MessageType;
import com.dharbuzov.iso8583.packager.ISOMessagePackager;

/**
 * Default implementation of packager factory.
 *
 * @author Dmytro Harbuzov (dmytro.harbuzov@gmail.com).
 */
public class ISODefaultPackagerFactory implements ISOPackagerFactory {

  /**
   * {@inheritDoc}
   */
  @Override
  public byte[] pack(ISOMessage msg) {
    return msg.getType().toMTIString().getBytes(Charset.defaultCharset());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ISOMessage unpack(byte[] msgBytes) {
    MessageType messageType = MessageType.fromMTIString(new String(msgBytes));
    return ISOMessage.builder().source(MessageSource.IN).type(messageType).build();
  }

  @Override
  public void addMessagePackager(ISOMessagePackager messagePackager) {

  }

  @Override
  public void removeMessagePackager(ISOMessagePackager messagePackager) {

  }
}
