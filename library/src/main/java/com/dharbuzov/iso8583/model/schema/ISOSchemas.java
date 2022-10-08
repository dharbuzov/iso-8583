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

import com.dharbuzov.iso8583.exception.ISOException;
import com.dharbuzov.iso8583.packager.ASCIIMessagePackager;
import com.dharbuzov.iso8583.packager.HEXMessagePackager;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Class which is aware about all known schemas defined in the {@link ISOKnownSchema}
 *
 * @author Dmytro Harbuzov (dmytro.harbuzov@gmail.com).
 * @see ISOKnownSchema
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ISOSchemas {

  /**
   * Returns known schema based on type.
   *
   * @param knownSchema known schema name
   * @return known schema object by type
   */
  public static ISOSchema knownSchema(ISOKnownSchema knownSchema) {
    switch (knownSchema) {
      case ISO_87_ASCII:
        return ascii87();
      case ISO_87_HEX:
        return hex87();
      case ISO_93_ASCII:
        return ascii93();
      case ISO_93_HEX:
        return hex93();
      default:
        throw new ISOException("Can't find schema for known name '%s'", knownSchema.name());
    }
  }

  private static ISOSchema ascii87() {
    return ISOSchema.builder()
        .packager(ASCIIMessagePackager.class)
        .schema("0***", ISOMessageSchema.builder()
            .build())
        .build();
  }

  private static ISOSchema hex87() {
    return ISOSchema.builder()
        .packager(HEXMessagePackager.class)
        .schema("0***", ISOMessageSchema.builder()
            .build())
        .build();
  }

  private static ISOSchema ascii93() {
    return ISOSchema.builder()
        .packager(ASCIIMessagePackager.class)
        .schema("1***", ISOMessageSchema.builder()
            .build())
        .build();
  }

  private static ISOSchema hex93() {
    return ISOSchema.builder()
        .packager(HEXMessagePackager.class)
        .schema("1***", ISOMessageSchema.builder()
            .build())
        .build();
  }
}
