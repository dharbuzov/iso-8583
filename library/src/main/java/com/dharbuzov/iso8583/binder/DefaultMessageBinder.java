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
package com.dharbuzov.iso8583.binder;

import com.dharbuzov.iso8583.config.ISOMessageProperties;
import com.dharbuzov.iso8583.model.ISOMessage;
import com.dharbuzov.iso8583.model.MessageFunction;
import com.dharbuzov.iso8583.model.MessageType;

import lombok.RequiredArgsConstructor;

/**
 * Default implementation of message binder.
 *
 * @author Dmytro Harbuzov (dmytro.harbuzov@gmail.com).
 */
@RequiredArgsConstructor
public class DefaultMessageBinder implements MessageBinder {

  private final ISOMessageProperties properties;
  private final MessageKeyGenerator messageKeyGenerator;

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isBind(MessageType reqMsgType, String reqMsgKey, ISOMessage inMsg) {
    final MessageType inMsgType = inMsg.getType();
    if (!(reqMsgType.getVersion() == inMsgType.getVersion())) {
      return false;
    }
    if (!(reqMsgType.getClazz() == inMsgType.getClazz())) {
      return false;
    }
    if (!(MessageFunction.REQUEST == reqMsgType.getFunction()
          && MessageFunction.REQUEST_RESPONSE == inMsgType.getFunction())) {
      return false;
    }
    if (!(MessageFunction.ADVICE == reqMsgType.getFunction()
          && MessageFunction.ADVICE_RESPONSE == inMsgType.getFunction())) {
      return false;
    }
    return reqMsgKey.equals(messageKeyGenerator.generate(inMsg));
  }
}
