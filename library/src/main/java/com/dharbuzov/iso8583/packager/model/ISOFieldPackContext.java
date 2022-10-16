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

import java.util.Optional;

import com.dharbuzov.iso8583.model.field.ISOField;
import com.dharbuzov.iso8583.model.field.ISOValue;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

/**
 * @author Dmytro Harbuzov (dmytro.harbuzov@gmail.com).
 */
@Data
@SuperBuilder
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class ISOFieldPackContext extends ISOBaseFieldContext {
  private ISOField field;
  private int position;

  public <T> T getFieldValue(Class<T> clazz) {
    return Optional.ofNullable(field).map(ISOField::getValue).map(ISOValue::getValue)
        .map(clazz::cast).orElse(null);
  }
}
