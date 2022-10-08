/*
 * Copyright (c) 2022 Paydock, Inc. All rights reserved. Paydock Confidential
 */

package com.dharbuzov.iso8583.model.schema;

import com.dharbuzov.iso8583.exception.ISOException;
import com.dharbuzov.iso8583.util.ValidationUtils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Class represents the declarative definition of ISO-8583 message schema and definition of each
 * field in the message.
 *
 * @author Dmytro Harbuzov (dmytro.harbuzov@gmail.com).
 */
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class ISOMessageSchema {

  public static final int FIELDS_SIZE = 129;

  private ISOFieldSchema[] fields = new ISOFieldSchema[FIELDS_SIZE];

  /**
   * Sets field's schema for desired position.
   *
   * @param position    field position in ISO-8583 message
   * @param fieldSchema field schema
   */
  public ISOMessageSchema field(int position, ISOFieldSchema fieldSchema) throws ISOException {
    ValidationUtils.validateFieldPosition(position);
    this.fields[position] = fieldSchema;
    return this;
  }

  /**
   * Gets field by position.
   *
   * @param position position to get from
   * @return found field schema
   */
  public ISOFieldSchema getField(int position) throws ISOException {
    ValidationUtils.validateFieldPosition(position);
    return this.fields[position];
  }

  /**
   * Returns a builder class.
   *
   * @return builder class
   */
  public static ISOMessageSchemaBuilder builder() {
    return new ISOMessageSchemaBuilder();
  }

  /**
   * Builder class.
   */
  public static class ISOMessageSchemaBuilder {
    private final ISOFieldSchema[] fields = new ISOFieldSchema[FIELDS_SIZE];

    /**
     * Sets field's schema for desired position.
     *
     * @param position    field position in ISO-8583 message
     * @param fieldSchema field schema
     */
    public ISOMessageSchemaBuilder field(int position, ISOFieldSchema fieldSchema) {
      ValidationUtils.validateFieldPosition(position);
      this.fields[position] = fieldSchema;
      return this;
    }

    /**
     * Builds the message schema.
     *
     * @return {@link ISOMessageSchema} instance
     */
    public ISOMessageSchema build() {
      return new ISOMessageSchema(this.fields);
    }
  }
}
