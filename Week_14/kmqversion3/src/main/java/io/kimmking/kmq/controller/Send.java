package io.kimmking.kmq.controller;

import io.kimmking.kmq.core.KmqBroker;
import io.kimmking.kmq.core.KmqMessage;
import io.kimmking.kmq.core.KmqProducer;
import io.kimmking.kmq.core.KmqSimpleMsg;
import io.kimmking.kmq.demo.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Send {

    @Autowired
    private KmqBroker kmqBroker;
    private final String topic = "kk.test";

    /**
     * curl -XPOST -H "Content-type: application/json" "http://localhost:8080/send" -d '{"msg":"12345"}'
     */
    @RequestMapping("/send")
    public String send(@RequestBody KmqSimpleMsg msg) {

        System.out.println("xxxxxxxxxxxx " + msg);

        KmqProducer producer = kmqBroker.createProducer();
        producer.send(topic, new KmqMessage(null, new Order(100000L, System.currentTimeMillis(), msg.getMsg(), 6.52d)));

        return "ok";
    }
}
