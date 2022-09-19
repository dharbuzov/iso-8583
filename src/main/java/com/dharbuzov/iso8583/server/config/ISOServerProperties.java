package com.dharbuzov.iso8583.server.config;

import com.dharbuzov.iso8583.config.ISOBaseProperties;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author Dmytro Harbuzov (dmytro.harbuzov@gmail.com).
 */
@Data
@Builder
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class ISOServerProperties extends ISOBaseProperties {

}
