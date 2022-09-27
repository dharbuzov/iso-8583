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
package com.dharbuzov.iso8583.order;

import java.util.PriorityQueue;
import java.util.Queue;

/**
 * Base class which represents the container which might keep elements in ordered manner.
 *
 * @author Dmytro Harbuzov (dmytro.harbuzov@gmail.com).
 * @see ISOOrdered
 */
public abstract class ISOOrderedContainer<T extends ISOOrdered> {

  /* Ordered container */
  protected final Queue<T> queue = new PriorityQueue<>(new ISOOrdered.ISOOrderedComparator());

  /**
   * Adds element to the ordered queue.
   *
   * @param element element to add
   */
  protected void addToQueue(T element) {
    this.queue.add(element);
  }

  /**
   * Removes element from the ordered queue.
   *
   * @param element element to remove
   */
  protected void removeFromQueue(T element) {
    this.queue.remove(element);
  }
}
