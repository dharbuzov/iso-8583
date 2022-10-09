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

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.dharbuzov.iso8583.packager.ISOFieldPackager;
import com.dharbuzov.iso8583.util.ValidationUtils;

/**
 * @author Dmytro Harbuzov (dmytro.harbuzov@gmail.com).
 */
public class ISODefaultFieldPackagerFactory implements ISOFieldPackagerFactory {

  protected final Map<Class<? extends ISOFieldPackager>, ISOFieldPackager> packagers =
      new ConcurrentHashMap<>();

  @Override
  public ISOFieldPackager getFieldPackager(Class<? extends ISOFieldPackager> fieldPackager) {
    ValidationUtils.validateNotNull(fieldPackager, "ISOFieldPackager is missing!");
    return packagers.get(fieldPackager);
  }

  @Override
  public void addFieldPackager(ISOFieldPackager fieldPackager) {
    ValidationUtils.validateNotNull(fieldPackager, "ISOFieldPackager is missing!");
    packagers.put(fieldPackager.getClass(), fieldPackager);
  }

  @Override
  public void removeFieldPackager(ISOFieldPackager fieldPackager) {
    ValidationUtils.validateNotNull(fieldPackager, "ISOFieldPackager is missing!");
    packagers.remove(fieldPackager.getClass());
  }
}
