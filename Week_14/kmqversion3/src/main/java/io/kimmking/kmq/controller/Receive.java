package io.kimmking.kmq.controller;

import io.kimmking.kmq.beans.KmqConsumerContainer;
import io.kimmking.kmq.core.KmqConsumer;
import io.kimmking.kmq.core.KmqMessage;
import io.kimmking.kmq.core.KmqSimpleConsumerMsg;
import io.kimmking.kmq.core.KmqSimpleMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Receive {

    @Autowired
    private KmqConsumerContainer container;

    /**
     * curl -XPOST -H "Content-type: application/json" "http://localhost:8080/recv" -d '{"name":"c1","topic":"kk.test"}'
     *
     * @param request :
     * @return :
     */
    @PostMapping("/recv")
    public KmqSimpleMsg recv(@RequestBody KmqSimpleConsumerMsg request) {

        String name = request.getName();
        String topic = request.getTopic();

        if (name == null) {
            KmqSimpleMsg kmqSimpleMsg = new KmqSimpleMsg();
            kmqSimpleMsg.setMsg("错误");
            return kmqSimpleMsg;
        }

        KmqConsumer consumer = container.getConsumer(name);
        if (consumer == null) {
            KmqSimpleMsg kmqSimpleMsg = new KmqSimpleMsg();
            kmqSimpleMsg.setMsg("错误");
            return kmqSimpleMsg;
        }

        consumer.subscribe(topic);

        KmqMessage msg = consumer.poll(0);
        if (msg == null) {
            KmqSimpleMsg kmqSimpleMsg = new KmqSimpleMsg();
            kmqSimpleMsg.setMsg("空空");
            return kmqSimpleMsg;
        }
        KmqSimpleMsg kmqSimpleMsg = new KmqSimpleMsg();
        kmqSimpleMsg.setMsg(msg.toString());
        return kmqSimpleMsg;
    }
}
