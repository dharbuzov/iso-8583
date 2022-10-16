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
   * Returns flag which indicates that the message type is request.
   *
   * @return {@code true} if message is request, otherwise {@code false}
   */
  public boolean isRequest() {
    return !isResponse();
  }

  /**
   * Returns flag which indicates that the message type is response.
   *
   * @return {@code true} if message is response, otherwise {@code false}
   */
  public boolean isResponse() {
    return getFunction() != null && (MessageFunction.REQUEST_RESPONSE == getFunction()
                                     || MessageFunction.ADVICE_RESPONSE == getFunction());
  }

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
   * Factory method to create message type based on all 4 fields.
   *
   * @param version  message version
   * @param clazz    message class
   * @param function message function
   * @param origin   message origin
   * @return created message type
   */
  public static MessageType from(MessageVersion version, MessageClass clazz,
      MessageFunction function, MessageOrigin origin) {
    return MessageType.builder().version(version).clazz(clazz).function(function).origin(origin)
        .build();
  }
}
