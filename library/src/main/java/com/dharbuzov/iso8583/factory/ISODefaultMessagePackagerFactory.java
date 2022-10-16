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
package com.dharbuzov.iso8583.factory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.dharbuzov.iso8583.config.ISOBaseProperties;
import com.dharbuzov.iso8583.model.ISOMessage;
import com.dharbuzov.iso8583.model.schema.ISOMessageSchema;
import com.dharbuzov.iso8583.model.schema.ISOSchema;
import com.dharbuzov.iso8583.packager.ISOMessagePackager;
import com.dharbuzov.iso8583.packager.model.ISOMessagePackContext;
import com.dharbuzov.iso8583.packager.model.ISOMessageUnpackContext;
import com.dharbuzov.iso8583.util.ValidationUtils;

/**
 * Default implementation of packager factory.
 *
 * @author Dmytro Harbuzov (dmytro.harbuzov@gmail.com).
 */
public class ISODefaultMessagePackagerFactory implements ISOMessagePackagerFactory {

  protected final ISOSchema schema;
  protected final Map<Class<? extends ISOMessagePackager>, ISOMessagePackager> packagers =
      new ConcurrentHashMap<>();
  protected final ISOFieldPackagerFactory fieldPackagerFactory;

  /**
   * Constructor based on properties.
   *
   * @param properties           base properties
   * @param fieldPackagerFactory field packager factory
   */
  public ISODefaultMessagePackagerFactory(ISOBaseProperties properties,
      ISOFieldPackagerFactory fieldPackagerFactory) {
    ValidationUtils.validateNotNull(properties, "ISOBaseProperties are missing!");
    ValidationUtils.validateNotNull(fieldPackagerFactory, "ISOFieldPackagerFactory are missing!");
    final ISOSchema schema = properties.getSchema();
    validateSchema(schema);
    this.schema = schema;
    this.fieldPackagerFactory = fieldPackagerFactory;
  }

  /**
   * Validates the provided iso schema.
   *
   * @param schema schema to validate
   */
  protected void validateSchema(ISOSchema schema) {
    ValidationUtils.validateNotNull(schema,
        "ISOSchema is missing! Please set the ISOSchema in the properties!");
    ValidationUtils.validateSchema(schema);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public byte[] pack(ISOMessage message) {
    final ISOMessagePackContext messagePackContext = createMessagePackContext(message);
    final ISOMessagePackager packager = getMessagePackager(schema.getPackager());
    return packager.pack(messagePackContext);
  }

  /**
   * Creates the context for packing the message into the byte representation.
   *
   * @param message message to create context for
   * @return created message pack context
   */
  protected ISOMessagePackContext createMessagePackContext(ISOMessage message) {
    final ISOMessageSchema messageSchema = schema.getApplicableSchema(message.getType());
    ValidationUtils.validateNotNull(messageSchema,
        String.format("Can't find the applicable message schema for type '%s'", message.getType()));
    return ISOMessagePackContext.builder().message(message).schema(messageSchema)
        .encoding(schema.getEncoding()).fieldPackagerFactory(fieldPackagerFactory)
        .messageLengthDigits(schema.getLengthDigits()).build();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ISOMessage unpack(byte[] msgBytes) {
    final ISOMessageUnpackContext messageUnpackContext = createMessageUnpackContext(msgBytes);
    final ISOMessagePackager packager = getMessagePackager(schema.getPackager());
    return packager.unpack(messageUnpackContext);
  }

  /**
   * Creates the context for unpacking the message from bytes representation.
   *
   * @param msgBytes message bytes to create context for
   * @return created message unpack context
   */
  protected ISOMessageUnpackContext createMessageUnpackContext(byte[] msgBytes) {
    final ISOMessageSchema messageSchema = schema.getSchema("****");
    return ISOMessageUnpackContext.builder().schema(messageSchema).encoding(schema.getEncoding())
        .fieldPackagerFactory(fieldPackagerFactory).messageLengthDigits(schema.getLengthDigits())
        .build();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void addMessagePackager(ISOMessagePackager messagePackager) {
    ValidationUtils.validateNotNull(messagePackager, "ISOMessagePackager is missing!");
    this.packagers.put(messagePackager.getClass(), messagePackager);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ISOMessagePackager getMessagePackager(
      Class<? extends ISOMessagePackager> messagePackager) {
    ValidationUtils.validateNotNull(messagePackager, "ISOMessagePackager class is missing!");
    return this.packagers.get(messagePackager);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void removeMessagePackager(ISOMessagePackager messagePackager) {
    ValidationUtils.validateNotNull(messagePackager, "ISOMessagePackager is missing!");
    this.packagers.remove(messagePackager.getClass());
  }
}
