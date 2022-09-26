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
package com.dharbuzov.iso8583.config;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.dharbuzov.iso8583.model.MessageClass;
import com.dharbuzov.iso8583.model.MessageFunction;
import com.dharbuzov.iso8583.model.MessageOrigin;
import com.dharbuzov.iso8583.model.MessageType;

/**
 * @author Dmytro Harbuzov (dmytro.harbuzov@gmail.com).
 */
public class ISOMessagePropertiesTest {

  @Test
  void testMessageTypeKeyFields() {
    final ISOMessageProperties messageProperties = ISOMessageProperties.builder()
        .messageTypeFieldKeys(MessageType.builder()
            .clazz(MessageClass.NETWORK_MANAGEMENT)
            .function(MessageFunction.REQUEST)
            .origin(MessageOrigin.ACQUIRER)
            .build(), 7, 37).build();
    Map<MessageType, List<Integer>> fieldKeys = messageProperties.getFieldKeys();
    Assertions.assertNotNull(fieldKeys);
    Assertions.assertEquals(fieldKeys.size(), 1);
  }
}
