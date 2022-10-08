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

/**
 * Enum which represents possible value types defined by ISO-8583 specification.
 *
 * @author Dmytro Harbuzov (dmytro.harbuzov@gmail.com).
 */
public enum ISOValueType {
  /**
   * In every ISO8583 message, there is a bit map to indicate which fields are included in the
   * message.
   **/
  BITMAP,
  /** A fixed-length alphanumeric value. */
  ALPHA,
  /** A fixed-length numeric value. */
  NUMERIC,
  /** A variable length alphanumeric value with a 2-digit header length. */
  LLVAR,
  /** A variable length alphanumeric value with a 3-digit header length. */
  LLLVAR,
  /** variable length byte array with 4-digit header length. */
  LLLLVAR,
  /** Holds the binary data. */
  BINARY,
  /** Similar to LLVAR but holds byte arrays instead of strings. */
  LLBIN,
  /** Similar to LLLVAR but holds byte arrays instead of strings. */
  LLLBIN,
  /** variable length byte array with 4-digit header length. */
  LLLLBIN
}
