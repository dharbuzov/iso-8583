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

import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.dharbuzov.iso8583.packager.ISOMessagePackager;

/**
 * @author Dmytro Harbuzov (dmytro.harbuzov@gmail.com).
 */
public class ISOSchemaTest {

  @Test
  void schemaCreationTest() {
    ISOSchema schema = new ISOSchema();

    Assertions.assertEquals(schema.getEncoding(), ISOSchema.DEFAULT_ENCODING);
    Assertions.assertNotNull(schema);

    schema = ISOSchema.builder().build();

    Assertions.assertEquals(schema.getEncoding(), ISOSchema.DEFAULT_ENCODING);
    Assertions.assertNotNull(schema);

    schema.encoding(StandardCharsets.UTF_8);

    Assertions.assertEquals(schema.getEncoding(), StandardCharsets.UTF_8);

    schema.packager(ISOMessagePackager.class);

    Assertions.assertEquals(schema.getPackager(), ISOMessagePackager.class);
  }

  @Test
  void schemaConstructionTest() {
    ISOSchema schema = new ISOSchema();

    Assertions.assertNotNull(schema);

    ISOMessageSchema messageSchema = new ISOMessageSchema();
    schema.schema("****", messageSchema);

    Assertions.assertEquals(schema.getSchema("****"), messageSchema);
  }
}
