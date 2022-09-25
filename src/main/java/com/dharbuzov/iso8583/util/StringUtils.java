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
 * @author Dmytro Harbuzov (dmytro.harbuzov@gmail.com).
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class StringUtils {

  /**
   * Returns flag which indicates that the string is empty.
   *
   * @param str string to check
   * @return {@code true} if string is null or empty, otherwise {@code false}
   */
  public static boolean isEmpty(String str) {
    return str == null || str.isEmpty();
  }

  /**
   * Returns flag which indicates that the string is not empty.
   *
   * @param str string to check
   * @return {@code true} if string is not empty, otherwise {@code false}
   */
  public static boolean isNotEmpty(String str) {
    return !isEmpty(str);
  }
}
