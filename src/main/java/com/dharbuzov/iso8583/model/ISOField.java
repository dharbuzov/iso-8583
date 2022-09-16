/*
 * Copyright (c) 2022 Paydock, Inc. All rights reserved. Paydock Confidential
 */

package com.dharbuzov.iso8583.model;

import lombok.Builder;

/**
 * @author Dmytriy Harbuzov (dmitriy.harbuzov@paydock.com).
 */
@Builder
public class ISOField {

  private final int position;
  private final Object value;
}
