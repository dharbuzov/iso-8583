/*
 * Copyright (c) 2022 Paydock, Inc. All rights reserved. Paydock Confidential
 */

package com.dharbuzov.iso8583.channel;

import com.dharbuzov.iso8583.ISOChannel;
import com.dharbuzov.iso8583.ISOMessageFactory;

import lombok.RequiredArgsConstructor;

/**
 * @author Dmytro Harbuzov (dmytro.harbuzov@gmail.com).
 */
@RequiredArgsConstructor
public abstract class ISOBaseChannel implements ISOChannel {

  private final ISOMessageFactory messageFactory;


}
