package com.dharbuzov.iso8583.config;

import com.dharbuzov.iso8583.channel.ISOChannel;
import com.dharbuzov.iso8583.coordinator.ISODefaultMessageCoordinator;
import com.dharbuzov.iso8583.coordinator.ISOMessageCoordinator;
import com.dharbuzov.iso8583.factory.ISODefaultListenerFactory;
import com.dharbuzov.iso8583.factory.ISODefaultMessageFactory;
import com.dharbuzov.iso8583.factory.ISODefaultPackagerFactory;
import com.dharbuzov.iso8583.factory.ISOListenerFactory;
import com.dharbuzov.iso8583.factory.ISOMessageFactory;
import com.dharbuzov.iso8583.factory.ISOPackagerFactory;

/**
 * @author Dmytro Harbuzov (dmytro.harbuzov@gmail.com).
 */
public abstract class ISOBaseConfiguration<T extends ISOBaseProperties, C extends ISOChannel>
    implements ISOConfiguration<T, C> {

  protected final C channel;
  protected final ISOListenerFactory listenerFactory;
  protected final ISOMessageFactory messageFactory;

  protected final ISOMessageCoordinator messageCoordinator;
  protected final ISOPackagerFactory packagerFactory;

  public ISOBaseConfiguration(T properties) {
    this.channel = createChannel(properties);
    this.listenerFactory = createListenerFactory(properties);
    this.messageFactory = createMessageFactory(properties);
    this.messageCoordinator = createCoordinator(properties);
    this.packagerFactory = createPackagerFactory(properties);
  }

  @Override
  public ISOListenerFactory createListenerFactory(T properties) {
    return new ISODefaultListenerFactory();
  }

  @Override
  public ISOMessageFactory createMessageFactory(T properties) {
    return new ISODefaultMessageFactory();
  }

  @Override
  public ISOMessageCoordinator createCoordinator(T properties) {
    return new ISODefaultMessageCoordinator();
  }

  @Override
  public ISOPackagerFactory createPackagerFactory(T properties) {
    return new ISODefaultPackagerFactory();
  }
}
