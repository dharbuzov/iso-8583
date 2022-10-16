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

import com.dharbuzov.iso8583.model.field.ISOFieldType;
import com.dharbuzov.iso8583.model.field.ISOValueType;
import com.dharbuzov.iso8583.packager.ISOFieldPackager;

/**
 * @author Dmytro Harbuzov (dmytro.harbuzov@gmail.com).
 */
public class ISOFieldSchemaTest {

  @Test
  void schemaCreationTest() {
    ISOFieldSchema schema = new ISOFieldSchema();

    Assertions.assertNotNull(schema);

    schema = ISOFieldSchema.builder().build();

    Assertions.assertNotNull(schema);
  }

  @Test
  void schemaConstructionTest() {
    ISOFieldType fieldType = ISOFieldType.PRIMITIVE;
    ISOValueType valueType = ISOValueType.ALPHA;
    Class<? extends ISOFieldPackager> packager = ISOFieldPackager.class;
    ISOFieldSchema[] fieldSchemas = new ISOFieldSchema[]{};
    String defaultValue = "default";
    String name = "name";
    String description = "description";
    int length = 1;
    ISOFieldSchema schema = new ISOFieldSchema()
        .fieldType(fieldType)
        .valueType(valueType)
        .packager(packager)
        .fields(fieldSchemas)
        .defaultValue(defaultValue)
        .name(name)
        .description(description)
        .length(1);

    Assertions.assertNotNull(schema);
    Assertions.assertEquals(schema.getFieldType(), fieldType);
    Assertions.assertEquals(schema.getValueType(), valueType);
    Assertions.assertEquals(schema.getPackager(), packager);
    Assertions.assertEquals(schema.getFields(), fieldSchemas);
    Assertions.assertEquals(schema.getDefaultValue(), defaultValue);
    Assertions.assertEquals(schema.getName(), name);
    Assertions.assertEquals(schema.getDescription(), description);
    Assertions.assertEquals(schema.getLength(), length);

    schema = ISOFieldSchema.builder()
        .fieldType(fieldType)
        .valueType(valueType)
        .packager(packager)
        .defaultValue(defaultValue)
        .name(name)
        .description(description)
        .length(1)
        .build();

    Assertions.assertNotNull(schema);
    Assertions.assertEquals(schema.getFieldType(), fieldType);
    Assertions.assertEquals(schema.getValueType(), valueType);
    Assertions.assertEquals(schema.getPackager(), packager);
    Assertions.assertEquals(schema.getDefaultValue(), defaultValue);
    Assertions.assertEquals(schema.getName(), name);
    Assertions.assertEquals(schema.getDescription(), description);
    Assertions.assertEquals(schema.getLength(), length);
  }

  @Test
  void schemaFieldGetSetTest() {
    ISOFieldSchema subFieldSchema1 = ISOFieldSchema.builder().build();
    ISOFieldSchema subFieldSchema2 = ISOFieldSchema.builder().build();
    ISOFieldSchema fieldSchema = ISOFieldSchema.builder()
        .field(1, subFieldSchema1)
        .field(2, subFieldSchema2)
        .build();

    Assertions.assertNotNull(fieldSchema);
    Assertions.assertEquals(fieldSchema.getField(1), subFieldSchema1);
    Assertions.assertEquals(fieldSchema.getField(2), subFieldSchema2);
  }
}
