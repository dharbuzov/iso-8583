package com.dharbuzov.iso8583.listener;

import com.dharbuzov.iso8583.model.event.Event;

/**
 * @author Dmytro Harbuzov (dmytro.harbuzov@gmail.com).
 */
public interface ISOEventListener extends ISOPrecedenceListener {

  void onEvent(Event event);
}
