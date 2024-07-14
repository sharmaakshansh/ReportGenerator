package com.report.generator.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InputEntity {
    private String field1;
    private String field2;
    private String field3;
    private String field4;
    private BigDecimal field5;
    private String refkey1;
    private String refkey2;
}
