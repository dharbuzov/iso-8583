package com.dharbuzov.iso8583.listener;

/**
 * @author Dmytro Harbuzov (dmytro.harbuzov@gmail.com).
 */
public interface ISOPrecedenceListener {

  int MAX_PRECEDENCE = Integer.MAX_VALUE;
  int LOWEST_PRECEDENCE = Integer.MIN_VALUE;

  default int getPrecedence() {
    return LOWEST_PRECEDENCE;
  }
}
