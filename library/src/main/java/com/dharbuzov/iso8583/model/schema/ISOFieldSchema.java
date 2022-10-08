/*
 * Copyright (c) 2022 Paydock, Inc. All rights reserved. Paydock Confidential
 */

package com.dharbuzov.iso8583.model.schema;

import java.util.HashMap;
import java.util.Map;

import com.dharbuzov.iso8583.exception.ISOException;
import com.dharbuzov.iso8583.model.ISOFieldType;
import com.dharbuzov.iso8583.model.ISOValueType;
import com.dharbuzov.iso8583.packager.ISOFieldPackager;
import com.dharbuzov.iso8583.util.ValidationUtils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Class represents the declarative definition of ISO-8583 field schema and definition of each
 * subfield in case of constructed or composite field structure.
 *
 * @author Dmytro Harbuzov (dmytro.harbuzov@gmail.com).
 */
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class ISOFieldSchema {

  /**
   * Represents the field type.
   */
  private ISOFieldType fieldType;
  /**
   * Represents the field value type.
   */
  private ISOValueType valueType;
  /**
   * Represents the field packager.
   */
  private Class<? extends ISOFieldPackager> packager;
  /**
   * Represents the subfields of current field.
   */
  private ISOFieldSchema[] fields;
  /**
   * Represents the default value of the field, this value would be sent if is not provided in the
   * message.
   */
  private String defaultValue;
  /**
   * Represents the human-readable name of current field.
   */
  private String name;
  /**
   * Represents additional description of current field.
   */
  private String description;
  /**
   * Represents the field length.
   */
  private int length;

  /**
   * Sets the field type.
   *
   * @param fieldType field type to set
   * @return reference to this object {@link ISOFieldSchema}
   */
  public ISOFieldSchema fieldType(ISOFieldType fieldType) {
    this.fieldType = fieldType;
    return this;
  }

  /**
   * Sets the field value type.
   *
   * @param valueType field value type to set
   * @return reference to this object {@link ISOFieldSchema}
   */
  public ISOFieldSchema valueType(ISOValueType valueType) {
    this.valueType = valueType;
    return this;
  }

  /**
   * Sets the field packager.
   *
   * @param packager packager to set
   * @return reference to this object {@link ISOFieldSchema}
   */
  public ISOFieldSchema packager(Class<? extends ISOFieldPackager> packager) {
    this.packager = packager;
    return this;
  }

  /**
   * Sets the subfield.
   *
   * @param fieldSchemas subfield schemas to set
   * @return reference to this object {@link ISOFieldSchema}
   */
  public ISOFieldSchema fields(ISOFieldSchema[] fieldSchemas) {
    this.fields = fieldSchemas;
    return this;
  }

  /**
   * Gets the subfield schema.
   *
   * @param position subfield position
   * @return found subfield schema
   */
  public ISOFieldSchema getField(int position) throws ISOException {
    ValidationUtils.validateSubFieldPosition(position);
    try {
      return this.fields[position];
    } catch (ArrayIndexOutOfBoundsException ex) {
      throw new ISOException("Subfield with position '%s' not found!", position);
    }
  }

  /**
   * Sets default value.
   *
   * @param defaultValue default value to set
   * @return reference to this object {@link ISOFieldSchema}
   */
  public ISOFieldSchema defaultValue(String defaultValue) {
    this.defaultValue = defaultValue;
    return this;
  }

  /**
   * Sets field name.
   *
   * @param name name to set
   * @return reference to this object {@link ISOFieldSchema}
   */
  public ISOFieldSchema name(String name) {
    this.name = name;
    return this;
  }

  /**
   * Sets field description
   *
   * @param description description to set
   * @return reference to this object {@link ISOFieldSchema}
   */
  public ISOFieldSchema description(String description) {
    this.description = description;
    return this;
  }

  /**
   * Sets the field length.
   *
   * @param length length to set
   * @return reference to this object {@link ISOFieldSchema}
   */
  public ISOFieldSchema length(int length) {
    this.length = length;
    return this;
  }

  /**
   * Returns builder class.
   *
   * @return builder class
   */
  public static ISOFieldSchemaBuilder builder() {
    return new ISOFieldSchemaBuilder();
  }

  /**
   * Builder class.
   */
  public static class ISOFieldSchemaBuilder {
    private ISOFieldType fieldType;
    private ISOValueType valueType;
    private Class<? extends ISOFieldPackager> packager;
    private final Map<Integer, ISOFieldSchema> fields = new HashMap<>();
    private String defaultValue;
    private String name;
    private String description;
    private int length;

    /**
     * Sets the field type.
     *
     * @param fieldType field type to set
     * @return reference to this object {@link ISOFieldSchemaBuilder}
     */
    public ISOFieldSchemaBuilder fieldType(ISOFieldType fieldType) {
      this.fieldType = fieldType;
      return this;
    }

    /**
     * Sets the field value type.
     *
     * @param valueType field value type to set
     * @return reference to this object {@link ISOFieldSchemaBuilder}
     */
    public ISOFieldSchemaBuilder valueType(ISOValueType valueType) {
      this.valueType = valueType;
      return this;
    }

    /**
     * Sets the field packager.
     *
     * @param packager packager to set
     * @return reference to this object {@link ISOFieldSchemaBuilder}
     */
    public ISOFieldSchemaBuilder packager(Class<? extends ISOFieldPackager> packager) {
      this.packager = packager;
      return this;
    }

    /**
     * Sets the subfield.
     *
     * @param fieldSchema subfield to set
     * @return reference to this object {@link ISOFieldSchemaBuilder}
     */
    public ISOFieldSchemaBuilder field(int position, ISOFieldSchema fieldSchema) {
      ValidationUtils.validateSubFieldPosition(position);
      this.fields.put(position, fieldSchema);
      return this;
    }

    /**
     * Sets default value.
     *
     * @param defaultValue default value to set
     * @return reference to this object {@link ISOFieldSchemaBuilder}
     */
    public ISOFieldSchemaBuilder defaultValue(String defaultValue) {
      this.defaultValue = defaultValue;
      return this;
    }

    /**
     * Sets field name.
     *
     * @param name name to set
     * @return reference to this object {@link ISOFieldSchemaBuilder}
     */
    public ISOFieldSchemaBuilder name(String name) {
      this.name = name;
      return this;
    }

    /**
     * Sets field description
     *
     * @param description description to set
     * @return reference to this object {@link ISOFieldSchemaBuilder}
     */
    public ISOFieldSchemaBuilder description(String description) {
      this.description = description;
      return this;
    }

    /**
     * Sets the field length.
     *
     * @param length length to set
     * @return reference to this object {@link ISOFieldSchemaBuilder}
     */
    public ISOFieldSchemaBuilder length(int length) {
      this.length = length;
      return this;
    }

    /**
     * Builds the field schema.
     *
     * @return {@link ISOFieldSchema} instance
     */
    public ISOFieldSchema build() {
      final ISOFieldSchema schema = new ISOFieldSchema();
      schema.setName(this.name);
      schema.setDescription(this.description);
      schema.setFieldType(this.fieldType);
      schema.setValueType(this.valueType);
      schema.setPackager(this.packager);
      schema.setDefaultValue(this.defaultValue);
      schema.setLength(this.length);
      if (this.fields != null && !this.fields.isEmpty()) {
        final int highestPosition = this.fields.keySet().stream().max(Integer::compareTo).get();
        final ISOFieldSchema[] fields = new ISOFieldSchema[highestPosition + 1];
        for (Integer position : this.fields.keySet()) {
          fields[position] = this.fields.get(position);
        }
        schema.setFields(fields);
      }
      return schema;
    }
  }
}
