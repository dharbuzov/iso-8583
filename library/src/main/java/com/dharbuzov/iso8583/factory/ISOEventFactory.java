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

/**
 * An Event factory responsible for handling the incoming {@link Event} events in the library.
 *
 * @author Dmytro Harbuzov (dmytro.harbuzov@gmail.com).
 */
public interface ISOEventFactory {

  /**
   * Notifies the occurred event in the library.
   *
   * @param event event to notify
   */
  void notifyEvent(Event event);

  /**
   * Adds event listener.
   *
   * @param eventListener event listener to add
   */
  void addEventListener(ISOEventListener eventListener);

  /**
   * Removes event listener.
   *
   * @param eventListener event listener to remove
   */
  void removeEventListener(ISOEventListener eventListener);

  /**
   * Removes all event listeners from the factory.
   */
  void removeEventListeners();
}
