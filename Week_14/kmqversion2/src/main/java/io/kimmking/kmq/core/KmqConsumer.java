package io.kimmking.kmq.core;

public class KmqConsumer<T> {

    private final KmqBroker broker;

    private long markPos = -1;

    private Kmq kmq;

    public KmqConsumer(KmqBroker broker) {
        this.broker = broker;
    }

    public void subscribe(String topic) {
        this.kmq = this.broker.findKmq(topic);
        if (null == kmq) throw new RuntimeException("Topic[" + topic + "] doesn't exist.");
    }

    public KmqMessage<T> poll(long timeout) {
        KmqMessage<T> pollMsg = kmq.poll(timeout, Long.toString(markPos));
        if (pollMsg != null) {
            markPos++;
        }
        return pollMsg;
    }
}
