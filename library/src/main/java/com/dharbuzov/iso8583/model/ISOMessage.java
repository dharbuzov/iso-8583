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
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * The pojo class which represents the ISO 8583 message.
 *
 * @author Dmytro Harbuzov (dmytro.harbuzov@gmail.com).
 */
@Builder
@ToString
public class ISOMessage {

  public static final int FIELDS_SIZE = 129;

  @Getter
  @Setter
  private MessageSource source;

  /**
   * The message type indicator is a four-digit numeric field which indicates the overall function
   * of the message. A message type indicator includes the ISO 8583 version, the Message Class, the
   * Message Function and the Message Origin
   */
  @Getter
  @Setter
  private MessageType type;

  @Getter
  @Setter
  private String header;
  private final ISOField[] fields = new ISOField[FIELDS_SIZE];

  /**
   * Returns flag which indicates that the message is request.
   *
   * @return {@code true} if message is request, otherwise {@code false}
   */
  public boolean isRequest() {
    return !isResponse();
  }

  /**
   * Returns flag which indicates that the message is response.
   *
   * @return {@code true} if message is response, otherwise {@code false}
   */
  public boolean isResponse() {
    return type != null && type.getFunction() != null && (
        MessageFunction.REQUEST_RESPONSE == type.getFunction()
        || MessageFunction.ADVICE_RESPONSE == type.getFunction());
  }

  /**
   * Sets the current message as a response message.
   */
  public void setResponseType() {
    this.type.setResponseType();
    this.source = MessageSource.OUT;
  }

  /**
   * Gets field by the position.
   *
   * @param position position of the field in message
   * @return field
   * @throws ISOException if position is less than 0 or more than 129
   */
  public ISOField getField(int position) throws ISOException {
    validatePosition(position);
    return fields[position];
  }

  /**
   * Sets the field to specific position.
   *
   * @param position position of the field in message
   * @param field    field to set
   * @throws ISOException if the position is less than 0 or more than 129
   */
  public void setField(int position, ISOField field) throws ISOException {
    validatePosition(position);
    fields[position] = field;
  }

  /**
   * Validates that field's position is applicable by ISO 8583 specification.
   *
   * @param position position of the field to manipulate
   * @throws ISOException if position is less than 0 or more than 129
   */
  private void validatePosition(int position) throws ISOException {
    if (position <= 0 || position > FIELDS_SIZE) {
      throw new ISOException(
          "Wrong field position, '%s', should be more than '%s' and less than '%s'", position, 0,
          FIELDS_SIZE);
    }
  }
}
