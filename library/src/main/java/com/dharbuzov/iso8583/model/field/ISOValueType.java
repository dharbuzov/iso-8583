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
package com.dharbuzov.iso8583.model.field;

/**
 * Enum which represents possible value types defined by ISO-8583 specification.
 *
 * @author Dmytro Harbuzov (dmytro.harbuzov@gmail.com).
 */
public enum ISOValueType {
  /**
   * The message type indicator is a four-digit numeric field which indicates the overall function
   * of the message.
   */
  MESSAGE_TYPE,
  /**
   * A bit map to indicate which fields are included in the message or composite field.
   **/
  BITMAP,
  /**
   * A fixed-length alphanumeric value.
   */
  ALPHA,
  /**
   * A fixed-length numeric value.
   */
  NUMERIC,
  /**
   * A variable length alphanumeric value with a 2-digit header length.
   */
  LLVAR,
  /**
   * A variable length alphanumeric value with a 3-digit header length.
   */
  LLLVAR,
  /**
   * Variable length byte array with 4-digit header length.
   */
  LLLLVAR,
  /**
   * Holds the binary data.
   */
  BINARY,
  /**
   * Similar to LLVAR but holds byte arrays instead of strings.
   */
  LLBIN,
  /**
   * Similar to LLLVAR but holds byte arrays instead of strings.
   */
  LLLBIN,
  /**
   * Variable length byte array with 4-digit header length.
   */
  LLLLBIN
}
