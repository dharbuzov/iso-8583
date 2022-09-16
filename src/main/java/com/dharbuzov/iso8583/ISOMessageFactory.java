/*
 * Copyright (c) 2022 Paydock, Inc. All rights reserved. Paydock Confidential
 */

package com.dharbuzov.iso8583;

import com.dharbuzov.iso8583.model.ISOMessage;

/**
 * @author Dmytro Harbuzov (dmytro.harbuzov@gmail.com).
 */
public interface ISOMessageFactory {

  byte[] encode(ISOMessage message);

  ISOMessage decode(byte[] message);
}
