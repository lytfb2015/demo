package com.yogcn.iot.aggregation.response;

import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
public class HelloResponse {

    private String name ;

    private String email ;
}
