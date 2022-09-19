package com.dharbuzov.iso8583.config;

import java.util.List;

import lombok.Builder;
import lombok.Data;

/**
 * @author Dmytro Harbuzov (dmytro.harbuzov@gmail.com).
 */
@Data
@Builder
public class ISOMessageProperties {

  private List<String> keys;
}
