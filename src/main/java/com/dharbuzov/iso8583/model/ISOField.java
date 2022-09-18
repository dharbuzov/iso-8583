package com.dharbuzov.iso8583.model;

import lombok.Builder;

/**
 * @author Dmytro Harbuzov (dmytro.harbuzov@gmail.com).
 */
@Builder
public class ISOField {

  private final int position;
  private final Object value;
}
