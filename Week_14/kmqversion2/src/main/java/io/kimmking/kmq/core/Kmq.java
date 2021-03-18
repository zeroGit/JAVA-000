package io.kimmking.kmq.core;

import lombok.SneakyThrows;

import java.util.concurrent.TimeUnit;

public interface Kmq {

    boolean send(KmqMessage message);

    KmqMessage poll();

    default KmqMessage poll(String mark) {
        return poll();
    }

    KmqMessage poll(long timeout);

    default KmqMessage poll(long timeout, String mark) {
        return poll(timeout);
    }
}
