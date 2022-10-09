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
package com.dharbuzov.iso8583.factory;

import com.dharbuzov.iso8583.model.ISOField;
import com.dharbuzov.iso8583.model.schema.ISOFieldSchema;
import com.dharbuzov.iso8583.packager.ISOFieldPackager;

/**
 * A Packager factory responsible for orchestrating the {@link ISOField} message packaging into the
 * protocol level representation and vice versa.
 *
 * @author Dmytro Harbuzov (dmytro.harbuzov@gmail.com).
 * @see ISOFieldPackager
 * @see ISOField
 * @see ISOFieldSchema
 */
public interface ISOFieldPackagerFactory {
  /**
   * Gets the field packager by class.
   *
   * @param fieldPackager field packager class to get by
   * @return found field packager in the factory
   */
  ISOFieldPackager getFieldPackager(Class<? extends ISOFieldPackager> fieldPackager);

  /**
   * Adds field packager to factory.
   *
   * @param fieldPackager field packager to add
   */
  void addFieldPackager(ISOFieldPackager fieldPackager);

  /**
   * Removes field packager from factory.
   *
   * @param fieldPackager field packager to remove
   */
  void removeFieldPackager(ISOFieldPackager fieldPackager);
}
