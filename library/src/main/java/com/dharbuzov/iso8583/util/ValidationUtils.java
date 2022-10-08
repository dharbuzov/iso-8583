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

import static com.dharbuzov.iso8583.model.schema.ISOMessageSchema.FIELDS_SIZE;

import java.util.Map;

import com.dharbuzov.iso8583.exception.ISOException;
import com.dharbuzov.iso8583.exception.ISOValidationException;
import com.dharbuzov.iso8583.model.schema.ISOFieldSchema;
import com.dharbuzov.iso8583.model.schema.ISOMessageSchema;
import com.dharbuzov.iso8583.model.schema.ISOSchema;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Miscellaneous utility methods related to validation required in ISO-8583 messages. Mainly for
 * internal use within the library.
 *
 * @author Dmytro Harbuzov (dmytro.harbuzov@gmail.com).
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ValidationUtils {

  /**
   * Validates that parameter object is not null.
   *
   * @param o object to validate
   * @throws ISOException if object is null
   */
  public static void validateNotNull(Object o) throws ISOException {
    if (o == null) {
      throw new ISOValidationException("Object is missing!");
    }
  }

  /**
   * Validates that parameter object is not null.
   *
   * @param o       object to validate
   * @param message error message when object is null
   * @throws ISOException if object is null
   */
  public static void validateNotNull(Object o, String message) throws ISOException {
    if (o == null) {
      throw new ISOValidationException(message);
    }
  }

  /**
   * Validate that length parameter is more than zero.
   *
   * @param length length value to validate
   * @throws ISOException if value is less than
   */
  public static void validateLength(int length, String message) throws ISOException {
    if (length < 1 || length > 999) {
      throw new ISOValidationException(message);
    }
  }

  /**
   * Validates that field's position is applicable by ISO 8583 specification.
   *
   * @param fieldPosition position of the field in message
   * @throws ISOException if position is less than 0 or more than 129
   */
  public static void validateSubFieldPosition(int fieldPosition) throws ISOException {
    if (fieldPosition <= 0 || fieldPosition > 999) {
      throw new ISOValidationException(
          "Wrong subfield position, '%s', should be more than '%s' and less than '%s'",
          fieldPosition, 0, 999);
    }
  }

  /**
   * Validates that field's position is applicable by ISO 8583 specification.
   *
   * @param fieldPosition position of the field in message
   * @throws ISOException if position is less than 0 or more than 129
   */
  public static void validateFieldPosition(int fieldPosition) throws ISOException {
    if (fieldPosition < 0 || fieldPosition > FIELDS_SIZE) {
      throw new ISOValidationException(
          "Wrong field position, '%s', should be more than '%s' and less than '%s'", fieldPosition,
          0, FIELDS_SIZE);
    }
  }

  /**
   * Validates that string representation of generic message type are valid. Such message type may
   * contain '*' which indicates that it's applicable for any subtype.
   *
   * @param messageTypeStr message type string to validate
   */
  public static void validateGenericMessageTypeString(String messageTypeStr) throws ISOException {
    if (StringUtils.isEmpty(messageTypeStr)) {
      throw new ISOValidationException("Message type string is missing!");
    }
    if (messageTypeStr.length() != 4) {
      throw new ISOValidationException("Message type string length should be equal to '4'");
    }
    final char[] chars = messageTypeStr.toCharArray();
    for (int i = 0; i < chars.length; i++) {
      if (chars[i] == '*') {
        continue;
      }
      int digit = chars[i];
      if (digit > 9) {
        throw new ISOValidationException(
            "Position '%s'. Char '%s' is not valid for Message Type, valid are '1..9' or '*'", i,
            chars[i]);
      }
    }
  }

  /**
   * Validates that string representation of message type are valid.
   *
   * @param messageTypeStr message type string to validate
   */
  public static void validateMessageTypeString(String messageTypeStr) throws ISOException {
    if (StringUtils.isEmpty(messageTypeStr)) {
      throw new ISOValidationException("Message type string is missing!");
    }
    if (messageTypeStr.length() != 4) {
      throw new ISOValidationException("Message type string length should be equal to '4'");
    }
    final char[] chars = messageTypeStr.toCharArray();
    for (int i = 0; i < chars.length; i++) {
      int digit = chars[i];
      if (digit > 9) {
        throw new ISOValidationException(
            "Position '%s'. Char '%s' is not valid for Message Type, valid are '1..9' or '*'", i,
            chars[i]);
      }
    }
  }

  /**
   * Validates the ISO-8583 general schema.
   *
   * @param schema schema to validate
   * @throws ISOException if any validation error occurred
   */
  public static void validateSchema(ISOSchema schema) throws ISOException {
    validateNotNull(schema, "ISOSchema is missing!");
    validateNotNull(schema.getPackager(),
        "ISOMessagePackager is missing for schema! Please add the general message packager!");
    final Map<String, ISOMessageSchema> messageSchemas = schema.getSchemas();
    validateNotNull(messageSchemas,
        "ISOMessageSchemas is missing for schema! Please add at least 1 general message schema for all types!");
    messageSchemas.forEach(ValidationUtils::validateSchema);
  }

  /**
   * Validates the ISO-8583 message schema.
   *
   * @param type   message type
   * @param schema message schema
   * @throws ISOException if any validation error occurred
   */
  public static void validateSchema(String type, ISOMessageSchema schema) throws ISOException {
    final ISOFieldSchema[] fields = schema.getFields();
    if (fields == null || fields.length == 0) {
      throw new ISOValidationException(
          "ISOFieldSchema schemas are missing, for type '%s'. Please define the field schemas!",
          type);
    }
    for (int i = 0; i < fields.length; i++) {
      ISOFieldSchema fieldSchema = fields[i];
      if (fieldSchema != null) {
        validateSchema(i, fieldSchema);
      }
    }
  }

  /**
   * Validates the ISO-8583 field schema.
   *
   * @param position field position
   * @param schema   field schema
   * @throws ISOException if any validation error occurred
   */
  public static void validateSchema(int position, ISOFieldSchema schema) throws ISOException {
    validateNotNull(schema, String.format("Position '%s'. Field schema is missing!", position));
    validateNotNull(schema.getFieldType(),
        String.format("Position '%s'. Field type is missing!", position));
    validateNotNull(schema.getValueType(),
        String.format("Position '%s'. Value type is missing!", position));
    validateLength(schema.getLength(), String.format(
        "Position '%s'. Field length is incorrect should be more than '0' and not more than '999'!",
        position));
  }
}
