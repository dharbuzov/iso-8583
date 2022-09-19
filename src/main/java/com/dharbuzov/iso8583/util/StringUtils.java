

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
