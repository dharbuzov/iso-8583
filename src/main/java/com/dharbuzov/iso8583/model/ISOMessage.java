package com.dharbuzov.iso8583.model;

import com.dharbuzov.iso8583.exception.ISOException;

import lombok.Builder;
import lombok.Getter;

/**
 * @author Dmytro Harbuzov (dmytro.harbuzov@gmail.com).
 */
@Builder
public class ISOMessage {

  public static final int FIELDS_SIZE = 129;

  @Getter
  private final MessageSource source = MessageSource.OUT;

  @Getter
  private final MessageTypeIndicator mti;

  @Getter
  private final String header;
  private final ISOField[] fields = new ISOField[FIELDS_SIZE];

  /**
   * Gets field by the position.
   *
   * @param position position of the field in message
   * @return field
   * @throws ISOException if position is less than 0 or more than 129
   */
  public ISOField getField(int position) throws ISOException {
    validatePosition(position);
    return fields[position];
  }

  /**
   * Sets the field to specific position.
   *
   * @param position position of the field in message
   * @param field    field to set
   * @throws ISOException if the position is less than 0 or more than 129
   */
  public void setField(int position, ISOField field) throws ISOException {
    validatePosition(position);
    fields[position] = field;
  }

  /**
   * Validates that field's position is applicable by ISO 8583 specification.
   *
   * @param position position of the field to manipulate
   * @throws ISOException if position is less than 0 or more than 129
   */
  private void validatePosition(int position) throws ISOException {
    if (position <= 0 || position > FIELDS_SIZE) {
      throw new ISOException(
          "Wrong field position, '%s', should be more than '%s' and less than '%s'", position, 0,
          FIELDS_SIZE);
    }
  }
}
