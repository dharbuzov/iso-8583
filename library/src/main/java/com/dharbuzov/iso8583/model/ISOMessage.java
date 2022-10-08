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

import static com.dharbuzov.iso8583.model.schema.ISOMessageSchema.FIELDS_SIZE;

import com.dharbuzov.iso8583.exception.ISOException;
import com.dharbuzov.iso8583.util.ValidationUtils;

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

  /**
   * The field which describers the source of message, incoming or outgoing.
   */
  @Getter
  @Setter
  private MessageSource source;

  /**
   * The message header string to be sent as ISO header, header goes after message length but before
   * the message type.
   */
  @Getter
  @Setter
  private String headerStr;

  /**
   * The message header byte array to be sent as ISO header, header goes after message length but
   * before the message type.
   */
  @Getter
  @Setter
  private byte[] headerBytes;

  /**
   * The message type indicator is a four-digit numeric field which indicates the overall function
   * of the message. A message type indicator includes the ISO 8583 version, the Message Class, the
   * Message Function and the Message Origin
   */
  @Getter
  @Setter
  private MessageType type;

  @Getter
  private final ISOField[] fields = new ISOField[FIELDS_SIZE];

  /**
   * The last character byte of message which indicates the termination of message bytes in the
   * message streaming.
   */
  @Getter
  @Setter
  private int etx = -1;

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
  }

  /**
   * Gets field by the position.
   *
   * @param position position of the field in message
   * @return field
   * @throws ISOException if position is less than 0 or more than 129
   */
  public ISOField getField(int position) throws ISOException {
    ValidationUtils.validateFieldPosition(position);
    return fields[position];
  }

  /**
   * Sets the field value to the specific position.
   *
   * @param position position of the field in message
   * @param field    field to set
   * @throws ISOException if the position is less than 0 or more than 129
   */
  public void setField(int position, ISOField field) throws ISOException {
    ValidationUtils.validateFieldPosition(position);
    fields[position] = field;
  }

  /**
   * Sets the field value to the specific position.
   *
   * @param position position of the field in message
   * @param value    string value to set
   * @throws ISOException if the position is less than 0 or more than 129
   */
  public void setField(int position, String value) throws ISOException {
    ValidationUtils.validateFieldPosition(position);
    final ISOField field = new ISOField(position, value);
    fields[position] = field;
  }

  /**
   * Sets the field value to the specific position.
   *
   * @param position position of the field in message
   * @param value    byte array value to set
   * @throws ISOException if the position is less than 0 or more than 129
   */
  public void setField(int position, byte[] value) throws ISOException {
    ValidationUtils.validateFieldPosition(position);
    final ISOField field = new ISOField(position, value);
    fields[position] = field;
  }

  /**
   * Removes field from the message.
   *
   * @param position position of the field
   * @throws ISOException if the position is less than 0 or more than 129
   */
  public void removeField(int position) throws ISOException {
    ValidationUtils.validateFieldPosition(position);
    fields[position] = null;
  }
}
