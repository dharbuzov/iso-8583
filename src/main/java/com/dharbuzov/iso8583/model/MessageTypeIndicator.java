package com.dharbuzov.iso8583.model;

import lombok.Builder;

/**
 * @author Dmytro Harbuzov (dmytro.harbuzov@gmail.com).
 */
@Builder
public class MessageTypeIndicator {

  private MessageVersion version;
  private MessageClass messageClass;
  private MessageFunction function;
  private MessageOrigin origin;
}
