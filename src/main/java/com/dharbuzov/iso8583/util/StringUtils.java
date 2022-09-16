/*
 * Copyright (c) 2022 Paydock, Inc. All rights reserved. Paydock Confidential
 */

package com.dharbuzov.iso8583.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * @author Dmytriy Harbuzov (dmitriy.harbuzov@paydock.com).
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
