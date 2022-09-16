/*
 * Copyright (c) 2022 Paydock, Inc. All rights reserved. Paydock Confidential
 */

package com.dharbuzov.iso8583.listener;

import com.dharbuzov.iso8583.model.ISOMessage;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Dmytro Harbuzov (dmytro.harbuzov@gmail.com).
 */
@Slf4j
public class DefaultISOMessageListener implements ISOMessageListener {

  @Override
  public void onMessage(ISOMessage message) {
    //TODO: log message received
  }
}
