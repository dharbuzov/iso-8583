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
package com.dharbuzov.iso8583.model.field;

import com.dharbuzov.iso8583.util.ValidationUtils;

/**
 * @author Dmytro Harbuzov (dmytro.harbuzov@gmail.com).
 */
public abstract class ISOAbstractField implements ISOField {

  protected final ISOFieldType fieldType;
  protected ISOValue<?> value;

  public ISOAbstractField(ISOFieldType fieldType) {
    ValidationUtils.validateNotNull(fieldType, "Field type is missing!");
    this.fieldType = fieldType;
  }

  @Override
  public ISOFieldType getFieldType() {
    return this.fieldType;
  }

  @Override
  public <T> void setValue(ISOValue<T> value) {
    this.value = value;
  }

  @Override
  @SuppressWarnings("unchecked")
  public <T> ISOValue<T> getValue() {
    return (ISOValue<T>) value;
  }
}
