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

import com.dharbuzov.iso8583.exception.ISOException;
import com.dharbuzov.iso8583.exception.ISOPackageException;
import com.dharbuzov.iso8583.util.StringUtils;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * The class wrapper for Message Type Indicator (MTI). The message type indicator is a four-digit
 * numeric field which indicates the overall function of the message. A message type indicator
 * includes the ISO 8583 version, the Message Class, the Message Function and the Message Origin.
 *
 * @author Dmytro Harbuzov (dmytro.harbuzov@gmail.com).
 */
@Data
@Builder
@ToString
@EqualsAndHashCode
public class MessageType {

  /**
   * ISO 8583 version. First digit.
   */
  private MessageVersion version;

  /**
   * ISO 8583 message class. Second digit.
   */
  private MessageClass clazz;

  /**
   * ISO 8583 message function. Third digit.
   */
  private MessageFunction function;

  /**
   * ISO 8583 message origin. Fourth digit.
   */
  private MessageOrigin origin;

  /**
   * Converts current type to response.
   */
  public void setResponseType() {
    final MessageFunction function = this.function;
    switch (function) {
      case REQUEST:
        this.function = MessageFunction.REQUEST_RESPONSE;
        break;
      case ADVICE:
        this.function = MessageFunction.ADVICE_RESPONSE;
        break;
      default:
        throw new ISOException("Can't set the proper response type, for message function: '%s'",
            function);
    }
  }

  /**
   * Returns message type as a MTI string like 'xxxx'.
   *
   * @return mti string
   */
  public String toMTIString() {
    return String.format("%s%s%s%s", version.getValue(), clazz.getValue(), function.getValue(),
        origin.getValue());
  }

  /**
   * Returns message type constructed from mti string.
   *
   * @param mtiStr mti string to get message type from
   * @return message type instance
   */
  public static MessageType fromMTIString(String mtiStr) {
    if (StringUtils.isEmpty(mtiStr)) {
      throw new ISOPackageException("MTI header is missing!");
    }
    if (mtiStr.length() != 4) {
      throw new ISOPackageException("MTI header should have length equals to 4!");
    }
    final char[] mtiChars = mtiStr.toCharArray();
    return MessageType.builder().version(MessageVersion.fromChar(mtiChars[0]))
        .clazz(MessageClass.fromChar(mtiChars[1])).function(MessageFunction.fromChar(mtiChars[2]))
        .origin(MessageOrigin.fromChar(mtiChars[3])).build();
  }
}
