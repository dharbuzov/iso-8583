

package com.dharbuzov.iso8583.server;

import com.dharbuzov.iso8583.channel.ISOServerChannel;
import com.dharbuzov.iso8583.server.config.ISOServerProperties;

/**
 * @author Dmytro Harbuzov (dmytro.harbuzov@gmail.com).
 */
public class ISODefaultServer implements ISOServer {

  public ISODefaultServer(ISOServerProperties properties, ISOServerChannel channel) {

  }

  @Override
  public void start() {

  }

  @Override
  public boolean isRunning() {
    return false;
  }

  @Override
  public void shutdown() {

  }
}
