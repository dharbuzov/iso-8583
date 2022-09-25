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
package com.dharbuzov.iso8583.listener;

/**
 * {@code ISOPrecedenceListener} is an interface that can be implemented by listeners that should be
 * notified in <em>ordered<em/> manner during event or message occurrence.
 *
 * @author Dmytro Harbuzov (dmytro.harbuzov@gmail.com).
 */
public interface ISOOrderedListener {

  int HIGHEST_PRECEDENCE = Integer.MIN_VALUE;
  int LOWEST_PRECEDENCE = Integer.MAX_VALUE;

  /**
   * Get the order value of this listener.
   * <p>Higher values are interpreted as lower priority. As a consequence,
   * the object with the lowest value has the highest priority.
   * <p>Same order values will result in arbitrary sort positions for the
   * affected listeners.
   * @return the order value
   * @see #HIGHEST_PRECEDENCE
   * @see #LOWEST_PRECEDENCE
   */
  default int getOrder() {
    return LOWEST_PRECEDENCE;
  }
}
