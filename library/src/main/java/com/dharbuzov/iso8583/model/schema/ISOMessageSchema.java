/*
 * Copyright (c) 2022 Paydock, Inc. All rights reserved. Paydock Confidential
 */

package com.dharbuzov.iso8583.model.schema;

import com.dharbuzov.iso8583.model.ISOMessage;

import lombok.ToString;

/**
 * @author Dmytro Harbuzov (dmytro.harbuzov@gmail.com).
 */
@ToString
public class ISOMessageSchema {
  ISOFieldSchema[] fields = new ISOFieldSchema[ISOMessage.FIELDS_SIZE];
}
