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

import com.dharbuzov.iso8583.listener.ISOEventListener;
import com.dharbuzov.iso8583.model.event.Event;
import com.dharbuzov.iso8583.order.ISOOrderedContainer;

/**
 * Default implementation of {@link ISOEventFactory}. This class could be easily extended based on
 * specific needs.
 *
 * @author Dmytro Harbuzov (dmytro.harbuzov@gmail.com).
 */
public class ISODefaultEventFactory extends ISOOrderedContainer<ISOEventListener>
    implements ISOEventFactory {

  @Override
  public void notifyEvent(Event event) {
    for (ISOEventListener listener : this.orderedSet) {
      if (listener.isApplicable(event)) {
        listener.onEvent(event);
      }
    }
  }

  @Override
  public void addEventListener(ISOEventListener eventListener) {
    addToOrderedSet(eventListener);
  }

  @Override
  public void removeEventListener(ISOEventListener eventListener) {
    removeFromOrderedSet(eventListener);
  }
}