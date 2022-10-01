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
package com.dharbuzov.iso8583.binder;

import com.dharbuzov.iso8583.client.ISOSyncClient;
import com.dharbuzov.iso8583.config.ISOMessageProperties;
import com.dharbuzov.iso8583.model.ISOMessage;

/**
 * Interface which represents message key generator, the key is used to bind the request and
 * response messages from the server in synchronous communication model.
 *
 * @author Dmytro Harbuzov (dmytro.harbuzov@gmail.com).
 * @see ISOMessageProperties
 * @see ISOSyncClient
 */
public interface MessageKeyGenerator {

  /**
   * Generates the unique key for a message which could be used to bind the request and response
   * message.
   *
   * @param msg msg to generate the key
   * @return generated key
   */
  String generate(ISOMessage msg);

}
