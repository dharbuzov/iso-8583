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
package com.dharbuzov.iso8583.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Miscellaneous padding utility methods. Mainly for internal use within the library.
 *
 * @author Dmytro Harbuzov (dmytro.harbuzov@gmail.com).
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class PaddingUtils {

  /**
   * Pads the input with zeros on the left.
   *
   * @param input  input integer
   * @param length desired length
   * @return padded string with zeros
   */
  public static String padLeftZeros(int input, int length) {
    final String inputStr = String.valueOf(input);
    return padLeftZeros(inputStr, length);
  }

  /**
   * Pads the input with zeros on the left.
   *
   * @param input  input integer
   * @param length desired length
   * @return padded string with zeros
   */
  public static String padLeftZeros(String input, int length) {
    final int inputLength = input.length();
    if (inputLength >= length) {
      return input;
    }
    final StringBuilder sb = new StringBuilder();
    while (sb.length() < length - inputLength) {
      sb.append('0');
    }
    sb.append(input);
    return sb.toString();
  }
}
