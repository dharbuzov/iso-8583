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
package com.dharbuzov.iso8583.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import com.dharbuzov.iso8583.exception.ISOPackageException;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * @author Dmytro Harbuzov (dmytro.harbuzov@gmail.com).
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ByteStreamUtils {

  /**
   * Writes the bytes into output byte stream
   *
   * @param bout  output byte stream
   * @param bytes bytes to write
   */
  public static void writeBytes(ByteArrayOutputStream bout, byte[] bytes) {
    try {
      bout.write(bytes);
    } catch (IOException e) {
      throw new ISOPackageException(e);
    }
  }

  /**
   * Reads the bytes from byte array input stream.
   *
   * @param bin    byte array output stream
   * @param bytes  bytes to read into
   * @param offset offset to start read
   * @param length max length
   */
  public static void readBytes(ByteArrayInputStream bin, byte[] bytes, int offset, int length) {
    bin.read(bytes, offset, length);
  }

  /**
   * Writes the output byte stream into another output byte stream.
   *
   * @param from from byte stream
   * @param to   to byte stream
   */
  public static void writeBout(ByteArrayOutputStream from, ByteArrayOutputStream to) {
    try {
      from.writeTo(to);
    } catch (IOException e) {
      throw new ISOPackageException(e);
    }
  }
}
