/*
 * Copyright (c) 2022 Paydock, Inc. All rights reserved. Paydock Confidential
 */

package com.dharbuzov.iso8583;

import com.dharbuzov.iso8583.model.ISOMessage;

/**
 * @author Dmytriy Harbuzov (dmitriy.harbuzov@paydock.com).
 */
public interface ISOMessageFactory {

  byte[] encode(ISOMessage message);

  ISOMessage decode(byte[] message);
}
