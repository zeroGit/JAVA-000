package io.kimmking.kmq.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;

public class Kmq2 implements Kmq {

    private String topic;

    private int capacity;

    private ConcurrentHashMap<Integer, KmqMessage[]> queues;
    private KmqMessage[] curQue;
    private int curQuePos;

    private final int MaxQueEleNum = 10000;

    public Kmq2(String topic, int capacity) {
        this.topic = topic;
        if (capacity <= 0) {
            capacity = MaxQueEleNum;
        }
        this.capacity = capacity;
        this.queues = new ConcurrentHashMap<>();
        this.queues.put(0, new KmqMessage[capacity]);
        this.curQue = this.queues.get(0);
        this.curQuePos = 0;
    }

    @Override
    synchronized public boolean send(KmqMessage message) {
        if (curQuePos >= capacity) {
            this.curQue = new KmqMessage[capacity];
            this.queues.put(this.queues.size(), this.curQue);
            this.curQuePos = 0;
        }
        this.curQue[this.curQuePos++] = message;
        this.notifyAll();
        return true;
    }

    @Override
    public KmqMessage poll() {
        return null;
    }

    @Override
    public KmqMessage poll(long timeout) {
        return null;
    }

    /**
     * @param mark: format : 121345 这个是上次消费的消息的pos，是所有消息queue合并起来的pos
     * @return :
     */
    @Override
    public KmqMessage poll(String mark) {
        int pos = Integer.parseInt(mark);
        // 当前消息位置
        pos++;

        // 所在que索引
        int queIndex = (pos + 1) / MaxQueEleNum;

        KmqMessage[] kmqMessages = queues.get(queIndex);
        if (kmqMessages == null) {
            return null;
        }

        int maxpos = MaxQueEleNum;
        synchronized (this) {
            if (kmqMessages == curQue) {
                maxpos = curQuePos;
            }
        }
        if (queIndex >= maxpos) {
            return null;
        }

        return kmqMessages[pos];
    }

    @Override
    public KmqMessage poll(long timeout, String mark) {

        KmqMessage poll = poll(mark);
        if (poll == null && timeout > 0) {
            synchronized (this) {
                try {
                    this.wait(timeout);
                    return poll(mark);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    return null;
                }
            }
        }
        return poll;
    }
}
