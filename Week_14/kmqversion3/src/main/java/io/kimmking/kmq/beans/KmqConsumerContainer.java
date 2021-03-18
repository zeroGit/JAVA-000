package io.kimmking.kmq.beans;

import io.kimmking.kmq.core.KmqBroker;
import io.kimmking.kmq.core.KmqConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;

@Component
public class KmqConsumerContainer {
    @Autowired
    private KmqBroker kmqBroker;

    private ConcurrentHashMap<String, KmqConsumer> consuemrs;

    public KmqConsumerContainer() {
        consuemrs = new ConcurrentHashMap<>();
    }

    public KmqConsumer getConsumer(String name) {

        KmqConsumer kmqConsumer = consuemrs.get(name);
        if (kmqConsumer == null) {
            synchronized (this) {
                KmqConsumer kmqConsumer2 = consuemrs.get(name);
                if (kmqConsumer2 == null) {
                    KmqConsumer consumer = kmqBroker.createConsumer();
                    consuemrs.put(name, consumer);
                    kmqConsumer2 = consumer;
                }
                return kmqConsumer2;
            }
        }

        return kmqConsumer;
    }
}
