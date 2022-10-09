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
package com.dharbuzov.iso8583.packager.model;

import java.io.ByteArrayOutputStream;
import java.nio.charset.Charset;

import com.dharbuzov.iso8583.factory.ISOFieldPackagerFactory;
import com.dharbuzov.iso8583.model.ISOField;
import com.dharbuzov.iso8583.model.ISOMessage;
import com.dharbuzov.iso8583.model.schema.ISOFieldSchema;
import com.dharbuzov.iso8583.model.schema.ISOMessageSchema;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author Dmytro Harbuzov (dmytro.harbuzov@gmail.com).
 */
@Data
@Builder
@ToString
@EqualsAndHashCode
public class ISOPackagerContext {
  private Charset encoding;
  private int messageLength;
  private ISOMessageSchema messageSchema;
  private ISOMessage message;
  private ISOFieldSchema fieldSchema;
  private ISOField field;
  private int fieldPosition;
  private ISOFieldPackagerFactory fieldPackagerFactory;
  private ByteArrayOutputStream bout;
}
