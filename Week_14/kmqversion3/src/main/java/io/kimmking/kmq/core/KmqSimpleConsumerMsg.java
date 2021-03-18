package io.kimmking.kmq.core;

import lombok.Data;

@Data
public class KmqSimpleConsumerMsg {
    private String name;
    private String topic;
}
