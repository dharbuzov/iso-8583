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

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dharbuzov.iso8583.model.MessageType;

import lombok.Builder;
import lombok.Data;

/**
 * @author Dmytro Harbuzov (dmytro.harbuzov@gmail.com).
 */
@Data
@Builder
public class ISOMessageProperties {

  private Map<MessageType, List<Integer>> fieldKeys;

  public static class ISOMessagePropertiesBuilder {

    private Map<MessageType, List<Integer>> fieldKeys = new HashMap<>();

    public ISOMessagePropertiesBuilder messageTypeFieldKeys(MessageType messageType, Integer... fields) {
      fieldKeys.put(messageType, Arrays.asList(fields));
      return this;
    }

  }
}
