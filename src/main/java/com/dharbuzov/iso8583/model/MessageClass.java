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
 * The enum describes the ISO 8583 protocol available message classes.
 *
 * @author Dmyt ro Harbuzov (dmytro.harbuzov@gmail.com).
 */
@Getter
@AllArgsConstructor
public enum MessageClass {

  /**
   * `x1xx` - Authorization message.
   * <p>
   * <p>
   * Determine if funds are available, get an approval but do not post to account for
   * reconciliation. Dual message system (DMS), awaits file exchange for posting to the account.
   */
  AUTHORIZATION(1),

  /**
   * `x2xx` - Financial messages.
   * <p>
   * <p>
   * Determine if funds are available, get an approval and post directly to the account. Single
   * message system (SMS), no file exchange after this.
   */
  FINANCIAL(2),

  /**
   * `x3xx` - File actions message.
   * <p>
   * <p>
   * Used for hot-card, TMS and other exchanges.
   */
  FILE_ACTIONS(3),

  /**
   * `x4xx` 	Reversal and chargeback messages.
   * <p>
   * <p>
   * - Reversal (x4x0 or x4x1): Reverses the action of a previous authorization. - Chargeback (x4x2
   * or x4x3): Charges back a previously cleared financial message.
   */
  REVERSAL_CHARGEBACK(4),

  /**
   * `x5xx` - Reconciliation message.
   * <p>
   * Transmits settlement information message.
   */
  RECONCILIATION(5),

  /**
   * `x6xx` - Administrative message.
   * <p>
   * Transmits administrative advice. Often used for failure messages (e.g., message reject or
   * failure to apply).
   */
  ADMINISTRATIVE(6),

  /**
   * `x7xx` - Fee collection messages.
   */
  FEE_COLLECTION(7),

  /**
   * x8xx	Network management message Used for secure key exchange, logon, echo test and other
   * network functions.
   */
  NETWORK_MANAGEMENT(8);

  private int value;
}
