

package com.dharbuzov.iso8583.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * @author Dmytro Harbuzov (dmytro.harbuzov@gmail.com).
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class StringUtils {

  public static boolean isEmpty(String str) {
    return str == null || str.isEmpty();
  }

  public static boolean isNotEmpty(String str) {
    return !isEmpty(str);
  }
}
