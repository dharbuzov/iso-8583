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

import java.util.ArrayList;
import java.util.List;

import com.dharbuzov.iso8583.exception.ISOException;
import com.dharbuzov.iso8583.exception.ISOValidationException;
import com.dharbuzov.iso8583.util.ValidationUtils;

import lombok.Getter;

/**
 * @author Dmytro Harbuzov (dmytro.harbuzov@gmail.com).
 */
public abstract class ISOAbstractCompoundField extends ISOAbstractField {

  @Getter
  protected final int fieldsSize;

  @Getter
  protected final List<ISOField> fields;

  /**
   * Construct for compound type field.
   *
   * @param fieldType  field type
   * @param fieldsSize fields size
   * @throws ISOValidationException if field type is not applicable for compound type
   */
  public ISOAbstractCompoundField(ISOFieldType fieldType, int fieldsSize)
      throws ISOValidationException {
    super(fieldType);
    if (fieldType == ISOFieldType.PRIMITIVE) {
      throw new ISOValidationException("Primitive field type can't be compound!");
    }
    this.fieldsSize = fieldsSize;
    this.fields = new ArrayList<>(fieldsSize);
  }

  /**
   * Gets field by the position.
   *
   * @param position position of the subfield in field
   * @return field
   * @throws ISOException if position is not valid
   */
  public ISOField getField(int position) throws ISOException {
    ValidationUtils.validateSubFieldPosition(position, fieldsSize);
    return fields.get(position);
  }

  /**
   * Sets the field value to the specific position.
   *
   * @param position position of the subfield in field
   * @param field    field to set
   * @throws ISOException if position is not valid
   */
  public void setField(int position, ISOField field) throws ISOException {
    ValidationUtils.validateSubFieldPosition(position, fieldsSize);
    fields.set(position, field);
  }

  /**
   * Sets the field value to the specific position.
   *
   * @param position position of the subfield in field
   * @param value    string value to set
   * @throws ISOException if position is not valid
   */
  public void setField(int position, String value) throws ISOException {
    ValidationUtils.validateSubFieldPosition(position, fieldsSize);
    final ISOStringValue stringValue = new ISOStringValue();
    stringValue.setValue(value);
    final ISOField field = new ISOPrimitiveField();
    field.setValue(stringValue);
    fields.set(position, field);
  }

  /**
   * Sets the field value to the specific position.
   *
   * @param position position of the field in message
   * @param value    byte array value to set
   * @throws ISOException if position is not valid
   */
  public void setField(int position, byte[] value) throws ISOException {
    ValidationUtils.validateSubFieldPosition(position, fieldsSize);
    final ISOByteValue byteValue = new ISOByteValue();
    byteValue.setValue(value);
    final ISOField field = new ISOPrimitiveField();
    field.setValue(byteValue);
    fields.set(position, field);
  }

  /**
   * Removes subfield from the field.
   *
   * @param position position of the field
   * @throws ISOException if position is not valid
   */
  public void removeField(int position) throws ISOException {
    ValidationUtils.validateSubFieldPosition(position, fieldsSize);
    fields.remove(position);
  }

  /**
   * Removes specified subfields from the field.
   *
   * @param positions array of positions
   * @throws ISOException if any position is not valid
   */
  public void removeFields(int... positions) throws ISOException {
    for (int position : positions) {
      // Validate all positions, so that we will not have a situation where some positions are
      // not valid method throws an exception and previous positions have been removed
      ValidationUtils.validateSubFieldPosition(position, fieldsSize);
    }
    for (int position : positions) {
      removeField(position);
    }
  }

  /**
   * Returns flag which indicates that message has field at the specified position.
   *
   * @param position position of the field
   * @return {@code true} if subfield exists in the field, otherwise {@code false}
   * @throws ISOException if position is not valid
   */
  public boolean hasField(int position) throws ISOException {
    ValidationUtils.validateSubFieldPosition(position, fieldsSize);
    return fields.get(position) != null;
  }

  /**
   * Returns flag which indicates that message has every field at the specified positions.
   *
   * @param positions array of positions
   * @return {@code true} if field contains any subfield specified at the positions, otherwise
   * {@code false}
   * @throws ISOException if any position is not valid
   */
  public boolean hasEveryField(int... positions) throws ISOException {
    for (int position : positions) {
      if (!hasField(position)) {
        return false;
      }
    }
    return true;
  }

  /**
   * Returns flag which indicates that field has any of subfield at the specified positions.
   *
   * @param positions array of positions
   * @return {@code true} if field contains any subfield specified at the positions, otherwise
   * {@code false}
   * @throws ISOException if any position is not valid
   */
  public boolean hasAnyField(int... positions) throws ISOException {
    for (int position : positions) {
      if (hasField(position)) {
        return true;
      }
    }
    return false;
  }
}
