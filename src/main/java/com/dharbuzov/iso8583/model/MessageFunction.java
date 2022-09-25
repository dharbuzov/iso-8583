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
package com.dharbuzov.iso8583.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * The enum describes the ISO 8583 protocol available functions.
 * <p>
 * Position three of the MTI specifies the message function which defines how the message should
 * flow within the system. Requests are end-to-end messages (e.g., from acquirer to issuer and back
 * with time-outs and automatic reversals in place), while advices are point-to-point messages
 * (e.g., from terminal to acquirer, from acquirer to network, from network to issuer, with
 * transmission guaranteed over each link, but not necessarily immediately).
 *
 * @author Dmytro Harbuzov (dmytro.harbuzov@gmail.com).
 */
@Getter
@AllArgsConstructor
public enum MessageFunction {
  /**
   * xx0x	Request	Request from acquirer to issuer to carry out an action; issuer may accept or
   * reject.
   */
  REQUEST(0),

  /**
   * xx1x	Request response	Issuer response to a request.
   */
  REQUEST_RESPONSE(1),

  /**
   * xx2x Advice
   * <p>
   * <p>
   * Advice that an action has taken place; receiver can only accept, not reject
   */
  ADVICE(2),

  /**
   * xx3x Advice response
   * <p>
   * <p>
   * Response to an advice
   */
  ADVICE_RESPONSE(3),

  /**
   * xx4x	Notification
   * <p>
   * <p>
   * Notification that an event has taken place; receiver can only accept, not reject
   */
  NOTIFICATION(4),

  /**
   * xx5x	Notification acknowledgement
   * <p>
   * <p>
   * Response to a notification
   */
  NOTIFICATION_ACK(5),

  /**
   * xx6x	Instruction	ISO 8583:2003
   */
  INSTRUCTION(6),

  /**
   * xx7x	Instruction acknowledgement
   */
  INSTRUCTION_ACK(7),

  /**
   * xx8x	Reserved for ISO use
   * <p>
   * <p>
   * Some implementations (such as MasterCard) use for positive acknowledgment.[4]
   */
  RESERVED_8(8),

  /**
   * xx9x	Some implementations (such as MasterCard) use for negative acknowledgement.
   */
  RESERVED_9(9);

  private int value;
}
