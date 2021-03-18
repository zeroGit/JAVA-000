package io.kimmking.kmq.demo;

import io.kimmking.kmq.core.KmqBroker;
import io.kimmking.kmq.core.KmqConsumer;
import io.kimmking.kmq.core.KmqMessage;
import io.kimmking.kmq.core.KmqProducer;

import lombok.SneakyThrows;

import java.util.Scanner;

public class KmqDemo {

    @SneakyThrows
    public static void main(String[] args) {

        String topic = "kk.test";
        KmqBroker broker = new KmqBroker();
        broker.createTopic(topic);

        final boolean[] flag = new boolean[1];
        flag[0] = true;

        String[] consumerName = {"c1"};
        int[] sleepC = {10};

        Runnable r = () -> {
            try {
                Thread.sleep(sleepC[0]);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            String cn = consumerName[0];
            KmqConsumer consumer = broker.createConsumer();
            consumer.subscribe(topic);
            while (flag[0]) {
                KmqMessage<Order> message = consumer.poll(10000);
                if (null != message) {
                    System.out.println(cn + " : " + message.getBody());
                } else {
                    System.out.println("poll time out");
                }
            }
            System.out.println("程序退出。");
        };

        // consumer 1 启动
        new Thread(r).start();

        Thread.sleep(1000);

        // consumer 2 启动
        consumerName[0] = "c2";
        sleepC[0] = 10000;
        new Thread(r).start();

        System.out.println("okkkkkkkkkkkkkk");

        KmqProducer producer = broker.createProducer();
        //for (int i = 0; i < 1000; i++) {
        //    Order order = new Order(1000L + i, System.currentTimeMillis(), "USD2CNY", 6.51d);
        //    producer.send(topic, new KmqMessage(null, order));
        //}
        Thread.sleep(500);
        System.out.println("点击任何键，发送一条消息；点击q或e，退出程序。");
        while (true) {
            Scanner scanner = new Scanner(System.in);
            String s = scanner.nextLine();
            producer.send(topic, new KmqMessage(null, new Order(100000L, System.currentTimeMillis(), s, 6.52d)));
        }
    }
}
