/*
 * Copyright (c) 2022 Paydock, Inc. All rights reserved. Paydock Confidential
 */

package com.dharbuzov.iso8583.channel;

/**
 * @author Dmytro Harbuzov (dmytro.harbuzov@gmail.com).
 */
public enum ChannelState {
  CREATED,
  CONNECTING,
  CONNECTED,
  CLOSING,
  CLOSED
}
