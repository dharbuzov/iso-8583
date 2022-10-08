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

import java.util.stream.Stream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import com.dharbuzov.iso8583.exception.ISOException;

/**
 * @author Dmytro Harbuzov (dmytro.harbuzov@gmail.com).
 */
public class ValidationUtilsTest {

  @ParameterizedTest(name = "{index} - {0} is a valid generic message type")
  @ValueSource(
      strings = {"0***", "00**", "000*", "0000", "1***", "11**", "111*", "1111", "2***", "22**",
          "222*", "2222", "3***", "33**", "333*", "3333", "4***", "44**", "444*", "4444", "5***",
          "55**", "555*", "5555", "6***", "66**", "666*", "6666", "7***", "77**", "777*", "7777",
          "8***", "88**", "888*", "8888", "9***", "99**", "999*", "9999",})
  void validateGenericMessageTypeStringSuccessTest(String messageType) {
    ValidationUtils.validateGenericMessageTypeString(messageType);
  }

  private static Stream<Arguments> validateGenericTypeStringFailureParameters() {
    return Stream.of(Arguments.of("AAAA",
            "Position '0'. Char 'A' is not valid for Message Type, valid are '1..9' or '*'"),
        Arguments.of("1AAA",
            "Position '1'. Char 'A' is not valid for Message Type, valid are '1..9' or '*'"),
        Arguments.of("11AA",
            "Position '2'. Char 'A' is not valid for Message Type, valid are '1..9' or '*'"),
        Arguments.of("111A",
            "Position '3'. Char 'A' is not valid for Message Type, valid are '1..9' or '*'"));
  }

  @ParameterizedTest
  @MethodSource("validateGenericTypeStringFailureParameters")
  void validateGenericMessageTypeStringFailureTest(String messageType, String errorMessage) {
    try {
      ValidationUtils.validateGenericMessageTypeString(messageType);
      Assertions.fail();
    } catch (ISOException ex) {
      Assertions.assertEquals(ex.getMessage(), errorMessage);
    }
  }
}
