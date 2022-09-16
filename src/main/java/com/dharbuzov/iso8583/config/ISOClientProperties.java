/*
 * Copyright (c) 2022 Paydock, Inc. All rights reserved. Paydock Confidential
 */

package com.dharbuzov.iso8583.config;

import lombok.Builder;
import lombok.Data;

/**
 * @author Dmytro Harbuzov (dmytro.harbuzov@gmail.com).
 */
@Data
@Builder
public class ISOClientProperties {

  private String host;

  private int port;

  private String packager;

  private String name;

  private boolean keepAlive;
}
