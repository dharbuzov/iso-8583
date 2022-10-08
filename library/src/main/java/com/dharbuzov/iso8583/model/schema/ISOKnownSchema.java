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
package com.dharbuzov.iso8583.model.schema;

/**
 * Enum which defines the known ISO-8583 schemas available as a part of library.
 *
 * @author Dmytro Harbuzov (dmytro.harbuzov@gmail.com).
 */
public enum ISOKnownSchema {
  ISO_87_ASCII, ISO_87_HEX, ISO_93_ASCII, ISO_93_HEX;
}
