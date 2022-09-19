package com.dharbuzov.iso8583.generator;

import com.dharbuzov.iso8583.config.ISOMessageProperties;
import com.dharbuzov.iso8583.model.ISOMessage;

import lombok.RequiredArgsConstructor;

/**
 * @author Dmytro Harbuzov (dmytro.harbuzov@gmail.com).
 */
@RequiredArgsConstructor
public class DefaultMessageKeyGenerator implements MessageKeyGenerator {

  private final ISOMessageProperties messageProperties;

  @Override
  public String generate(ISOMessage msg) {
    return msg.getHeader();
  }
}
