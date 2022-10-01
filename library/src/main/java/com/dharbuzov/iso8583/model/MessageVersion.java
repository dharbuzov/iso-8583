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

import java.util.Arrays;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * The enum describes the ISO 8583 protocol available versions.
 * <p>
 * The placements of fields in different versions of the standard varies; for example, the currency
 * elements of the 1987 and 1993 versions of the standard are no longer used in the 2003 version,
 * which holds currency as a sub-element of any financial amount element.
 *
 * @author Dmytro Harbuzov (dmytro.harbuzov@gmail.com).
 */
@Getter
@AllArgsConstructor
public enum MessageVersion {
  /**
   * ISO 8583:1987.
   */
  V1987(0),

  /**
   * ISO 8583:1993.
   */
  V1993(1),

  /**
   * ISO 8583:2003.
   */
  V2003(2),

  /**
   * Reserved ISO use.
   */
  RESERVED_3(3),

  /**
   * Reserved ISO use.
   */
  RESERVED_4(4),

  /**
   * Reserved ISO use.
   */
  RESERVED_5(5),

  /**
   * Reserved ISO use.
   */
  RESERVED_6(6),

  /**
   * Reserved ISO use.
   */
  RESERVED_7(7),

  /**
   * Reserved ISO use.
   */
  RESERVED_8(8),

  /**
   * Reserved ISO use.
   */
  RESERVED_9(9);

  private int value;

  /**
   * Returns version based on char value.
   *
   * @param ch char value
   * @return iso 8583 version value
   */
  public static MessageVersion fromChar(char ch) {
    final int value = Character.getNumericValue(ch);
    return Arrays.stream(MessageVersion.values()).filter(enumVal -> enumVal.getValue() == value)
        .findFirst().orElseThrow(() -> new RuntimeException(
            String.format("Could not detect iso8583 version from char '%s'", ch)));
  }
}
