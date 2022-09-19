package com.dharbuzov.iso8583.generator;

import com.dharbuzov.iso8583.model.ISOMessage;

/**
 * @author Dmytro Harbuzov (dmytro.harbuzov@gmail.com).
 */
public interface MessageKeyGenerator {

  String generate(ISOMessage msg);

}
