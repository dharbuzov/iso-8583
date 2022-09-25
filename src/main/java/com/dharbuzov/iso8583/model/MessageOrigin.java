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
 * The enum describes the ISO 8583 protocol available message origins.
 * <p>
 * Position four of the MTI defines the location of the message source within the payment chain.
 *
 * @author Dmytro Harbuzov (dmytro.harbuzov@gmail.com).
 */
@Getter
@AllArgsConstructor
public enum MessageOrigin {
  /**
   * xxx0	Acquirer
   */
  ACQUIRER(0),

  /**
   * xxx1	Acquirer repeat
   */
  ACQUIRER_REPEAT(1),

  /**
   * xxx2	Issuer
   */
  ISSUER(2),

  /**
   * xxx3	Issuer repeat
   */
  ISSUER_REPEAT(3),

  /**
   * xxx4	Other
   */
  OTHER(4),

  /**
   * xxx5	Other repeat
   */
  OTHER_REPEAT(5),

  /**
   * xxx6	Reserved for ISO use
   * <p>
   */
  RESERVED_6(6),

  /**
   * xxx7	Reserved for ISO use
   * <p>
   */
  RESERVED_7(7),

  /**
   * xxx8	Reserved for ISO use
   * <p>
   */
  RESERVED_8(8),

  /**
   * xxx9	Reserved for ISO use
   * <p>
   */
  RESERVED_9(9);

  private int value;
}
