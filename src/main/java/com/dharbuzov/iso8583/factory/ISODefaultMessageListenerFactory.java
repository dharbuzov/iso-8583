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

import com.dharbuzov.iso8583.channel.ISOReplyChannel;
import com.dharbuzov.iso8583.listener.ISOMessageListener;
import com.dharbuzov.iso8583.model.ISOMessage;
import com.dharbuzov.iso8583.order.ISOOrderedContainer;

/**
 * Default implementation of {@link ISOMessageListenerFactory} listener factory. This class could be
 * easily extended based on specific needs.
 *
 * @author Dmytro Harbuzov (dmytro.harbuzov@gmail.com).
 */
public class ISODefaultMessageListenerFactory extends ISOOrderedContainer<ISOMessageListener>
    implements ISOMessageListenerFactory {

  @Override
  public void onMessage(ISOReplyChannel replyChannel, ISOMessage message) {
    for (ISOMessageListener messageListener : this.orderedSet) {
      if (messageListener.isApplicable(message)) {
        messageListener.onMessage(replyChannel, message);
      }
    }
  }

  @Override
  public void addMessageListener(ISOMessageListener messageListener) {
    addToOrderedSet(messageListener);
  }

  @Override
  public void removeMessageListener(ISOMessageListener messageListener) {
    removeFromOrderedSet(messageListener);
  }
}
