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

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author Dmytro Harbuzov (dmytro.harbuzov@gmail.com).
 */
public class ISOMessageSchemaTest {

  @Test
  void schemaCreationTest() {
    ISOMessageSchema schema = new ISOMessageSchema();

    Assertions.assertNotNull(schema);
    for (ISOFieldSchema fieldSchema : schema.getFields()) {
      Assertions.assertNull(fieldSchema);
    }

    schema = ISOMessageSchema.builder().build();

    Assertions.assertNotNull(schema);
    for (ISOFieldSchema fieldSchema : schema.getFields()) {
      Assertions.assertNull(fieldSchema);
    }

  }

  @Test
  void schemaConstructionTest() {
    final ISOMessageSchema schema = new ISOMessageSchema();
    final ISOFieldSchema fieldSchema = ISOFieldSchema.builder().build();
    for (int i = 0; i < ISOMessageSchema.FIELDS_SIZE; i++) {
      schema.field(i, fieldSchema);
      Assertions.assertEquals(fieldSchema, schema.getField(0));
    }
  }

  @Test
  void schemaFieldNotValidTest() {
    ISOMessageSchema schema = ISOMessageSchema.builder().build();

    Assertions.assertNotNull(schema);
    try {
      schema.field(ISOMessageSchema.FIELDS_SIZE + 1, ISOFieldSchema.builder().build());
      Assertions.fail();
    } catch (Exception ex) {
      Assertions.assertNotNull(ex.getMessage());
    }
    try {
      schema.field(-1, ISOFieldSchema.builder().build());
      Assertions.fail();
    } catch (Exception ex) {
      Assertions.assertNotNull(ex.getMessage());
    }
  }
}
