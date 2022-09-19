package com.dharbuzov.iso8583.config;

import com.dharbuzov.iso8583.channel.ISOChannel;
import com.dharbuzov.iso8583.factory.ISOListenerFactory;
import com.dharbuzov.iso8583.factory.ISOPackagerFactory;
import com.dharbuzov.iso8583.generator.MessageKeyGenerator;

/**
 * @author Dmytro Harbuzov (dmytro.harbuzov@gmail.com).
 */
public interface ISOConfiguration<T extends ISOBaseProperties, C extends ISOChannel> {

  C createChannel(T properties);

  ISOListenerFactory createListenerFactory(T properties);

  MessageKeyGenerator createMessageKeyGenerator(T properties);

  ISOPackagerFactory createPackagerFactory(T properties);
}
