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
package com.dharbuzov.iso8583.model.schema;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import com.dharbuzov.iso8583.packager.ISOMessagePackager;
import com.dharbuzov.iso8583.util.ValidationUtils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Class represents the declarative definition of ISO-8583 all possible message schemas.
 *
 * @author Dmytro Harbuzov (dmytro.harbuzov@gmail.com).
 */
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class ISOSchema {
  /* Default encoding of messages */
  public static final Charset DEFAULT_ENCODING = StandardCharsets.ISO_8859_1;

  /* Message encoding */
  private Charset encoding = DEFAULT_ENCODING;

  /* The general message packager, which is used mainly to pack and unpack length, type, header, trailer */
  private Class<? extends ISOMessagePackager> packager;

  /* The map which holds all possible schemas for different message types. */
  private Map<String, ISOMessageSchema> schemas = new HashMap<>();

  /**
   * Sets encoding of messages.
   *
   * @param encoding encoding name to set
   * @return reference to this object {@link  ISOSchema}
   */
  public ISOSchema encoding(String encoding) {
    this.encoding = Charset.forName(encoding);
    return this;
  }

  /**
   * Sets encoding of messages.
   *
   * @param encoding encoding to set
   * @return reference to this object {@link  ISOSchema}
   */
  public ISOSchema encoding(Charset encoding) {
    this.encoding = encoding;
    return this;
  }

  /**
   * Sets the general message packager.
   *
   * @param packager packager to set
   * @return reference to this object {@link  ISOSchema}
   */
  public ISOSchema packager(Class<? extends ISOMessagePackager> packager) {
    this.packager = packager;
    return this;
  }

  /**
   * Adds the message schema by message type.
   *
   * @param type   message type string
   * @param schema message schema
   * @return reference to this object {@link  ISOSchema}
   */
  public ISOSchema schema(String type, ISOMessageSchema schema) {
    ValidationUtils.validateGenericMessageTypeString(type);
    this.schemas.put(type, schema);
    return this;
  }

  /**
   * Gets the message schema by generic type.
   *
   * @param type generic type
   * @return found message schema
   */
  public ISOMessageSchema getSchema(String type) {
    return this.schemas.get(type);
  }

  /**
   * Returns a builder class.
   *
   * @return builder class
   */
  public static ISOSchemaBuilder builder() {
    return new ISOSchemaBuilder();
  }

  /**
   * Builder class.
   */
  public static class ISOSchemaBuilder {

    private Charset encoding = DEFAULT_ENCODING;
    private Class<? extends ISOMessagePackager> packager;
    private final Map<String, ISOMessageSchema> schemas = new HashMap<>();

    /**
     * Sets encoding of messages.
     *
     * @param encoding encoding name to set
     * @return reference to this object {@link  ISOSchemaBuilder}
     */
    public ISOSchemaBuilder encoding(String encoding) {
      this.encoding = Charset.forName(encoding);
      return this;
    }

    /**
     * Sets encoding of messages.
     *
     * @param encoding encoding to set
     * @return reference to this object {@link  ISOSchemaBuilder}
     */
    public ISOSchemaBuilder encoding(Charset encoding) {
      this.encoding = encoding;
      return this;
    }

    /**
     * Sets the general message packager.
     *
     * @param packager packager to set
     * @return reference to this object {@link  ISOSchemaBuilder}
     */
    public ISOSchemaBuilder packager(Class<? extends ISOMessagePackager> packager) {
      this.packager = packager;
      return this;
    }

    /**
     * Adds the message schema by message type.
     *
     * @param type   message type string
     * @param schema message schema
     * @return reference to this object {@link  ISOSchemaBuilder}
     */
    public ISOSchemaBuilder schema(String type, ISOMessageSchema schema) {
      ValidationUtils.validateGenericMessageTypeString(type);
      this.schemas.put(type, schema);
      return this;
    }

    /**
     * Builds the iso schema.
     *
     * @return {@link ISOSchema} instance
     */
    public ISOSchema build() {
      return new ISOSchema(this.encoding, this.packager, this.schemas);
    }
  }
}
