package com.dharbuzov.iso8583.config;

import com.dharbuzov.iso8583.channel.ISOChannel;
import com.dharbuzov.iso8583.factory.ISODefaultListenerFactory;
import com.dharbuzov.iso8583.factory.ISODefaultPackagerFactory;
import com.dharbuzov.iso8583.factory.ISOListenerFactory;
import com.dharbuzov.iso8583.factory.ISOPackagerFactory;
import com.dharbuzov.iso8583.generator.DefaultMessageKeyGenerator;
import com.dharbuzov.iso8583.generator.MessageKeyGenerator;

/**
 * @author Dmytro Harbuzov (dmytro.harbuzov@gmail.com).
 */
public abstract class ISOBaseConfiguration<T extends ISOBaseProperties, C extends ISOChannel>
    implements ISOConfiguration<T, C> {

  protected final C channel;
  protected final ISOListenerFactory listenerFactory;
  protected final MessageKeyGenerator messageKeyGenerator;
  protected final ISOPackagerFactory packagerFactory;

  public ISOBaseConfiguration(T properties) {
    this.channel = createChannel(properties);
    this.listenerFactory = createListenerFactory(properties);
    this.messageKeyGenerator = createMessageKeyGenerator(properties);
    this.packagerFactory = createPackagerFactory(properties);
  }

  @Override
  public ISOListenerFactory createListenerFactory(T properties) {
    return new ISODefaultListenerFactory();
  }

  @Override
  public MessageKeyGenerator createMessageKeyGenerator(T properties) {
    return new DefaultMessageKeyGenerator(properties.getMessages());
  }

  @Override
  public ISOPackagerFactory createPackagerFactory(T properties) {
    return new ISODefaultPackagerFactory();
  }
}
