/*
 * Copyright 2022.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.dharbuzov.iso8583;

import org.awaitility.Awaitility;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import com.dharbuzov.iso8583.client.ISOClient;
import com.dharbuzov.iso8583.client.ISOSyncClient;
import com.dharbuzov.iso8583.client.config.ISOClientConfiguration;
import com.dharbuzov.iso8583.client.config.ISOClientProperties;
import com.dharbuzov.iso8583.server.ISOServer;
import com.dharbuzov.iso8583.server.config.ISOServerConfiguration;
import com.dharbuzov.iso8583.server.config.ISOServerProperties;

/**
 * Abstract integration test which is needed for testing different integrations between client and
 * server.
 *
 * @author Dmytro Harbuzov (dmytro.harbuzov@gmail.com).
 */
public abstract class AbstractIntegrationTest {

  protected final ISOSyncClient client;
  protected final ISOServer server;

  /**
   * Abstract integration test constructor.
   *
   * @param clientProperties client properties
   * @param serverProperties server properties
   */
  public AbstractIntegrationTest(ISOClientProperties clientProperties,
      ISOServerProperties serverProperties) {
    this.client = createClient(clientProperties);
    this.server = createServer(serverProperties);
  }

  @BeforeEach
  protected void beforeEach() {
    configureServer(server);
    configureClient(client);

    if (!server.isRunning()) {
      server.start();
    }
    if (!client.isConnected()) {
      client.connect();
    }

    Awaitility.await("Server is ready!").until(server::isRunning);
    Awaitility.await("Client is ready!").until(client::isConnected);
  }

  @AfterEach
  protected void afterEach() {
    server.removeMessageListeners();
    server.removeEventListeners();
    client.removeMessageListeners();
    client.removeEventListeners();
    if (client.isConnected()) {
      client.disconnect();
    }
    if (server.isRunning()) {
      server.shutdown();
    }
  }

  @AfterAll
  protected static void afterAll() {

  }

  /**
   * Creates the iso client.
   *
   * @param clientProperties client properties
   * @return created client instance
   */
  protected ISOSyncClient createClient(ISOClientProperties clientProperties) {
    return new ISOClientConfiguration(clientProperties).getClient();
  }

  /**
   * Creates the iso server.
   *
   * @param serverProperties server properties
   * @return created server instance
   */
  protected ISOServer createServer(ISOServerProperties serverProperties) {
    return new ISOServerConfiguration(serverProperties).getServer();
  }

  /**
   * Method for configure additionally the client for example adding message or event listeners.
   *
   * @param client iso client
   */
  protected void configureClient(ISOClient client) {
    //NOOP
  }

  /**
   * Method to configure additionally the server for example adding message or event listeners.
   *
   * @param server iso server
   */
  protected void configureServer(ISOServer server) {
    //NOOP
  }
}
