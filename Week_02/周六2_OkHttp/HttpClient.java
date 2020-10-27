import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.CountDownLatch;

public class LoadTest {

    public static void main(String[] args) {
        if (args.length != 3) {
            System.out.print("usage : java LoadTest url 并发数 总数\n");
            return;
        }

        int conNum;
        int total;
        try {
            conNum = Integer.parseInt(args[1]);
            total = Integer.parseInt(args[2]);
            if (conNum > total) {
                conNum = total;
            }
        } catch (NumberFormatException e) {
            System.out.println("parse error");
            e.printStackTrace();
            return;
        }
        @SuppressWarnings("unchecked")
        List<Long>[] diffs = (List<Long>[]) new List[conNum];
        for (int i = 0; i < conNum; i++) {
            diffs[i] = new LinkedList<Long>();
        }

        CountDownLatch countdonw = new CountDownLatch(conNum);

        for (int i = 0; i < conNum; i++) {
            int finalI = i;
            int finalConNum = conNum;
            new Thread(() -> {
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url(args[0])
                        .build();
                for (int j = 0; j < total / finalConNum; j++) {
                    long t0 = System.currentTimeMillis();
                    try {
                        try (Response response = client.newCall(request).execute()) {
                            if (response.code() == 200) {
                                long t1 = System.currentTimeMillis();
                                diffs[finalI].add(t1 - t0);
                            } else {
                                long t1 = System.currentTimeMillis();
                                diffs[finalI].add(1000000 + (t1 - t0));
                            }
                        }
                    } catch (IOException e) {
                        //e.printStackTrace();
                        long t1 = System.currentTimeMillis();
                        diffs[finalI].add(2000000 + (t1 - t0));
                    }
                }
                countdonw.countDown();
            }).start();
        }

        try {
            countdonw.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        TreeMap<Long, Integer> result = new TreeMap<Long, Integer>();

        long timeSum = 0;
        long reqNum = 0;
        for (int i = 0; i < conNum; i++) {
            List<Long> l = diffs[i];
            reqNum += l.size();
            for (Long aLong : l) {
                result.merge(aLong, 1, Integer::sum);
                if (aLong < 1000000) {
                    timeSum += aLong;
                } else {
                    reqNum -= 1;
                }
            }
        }

        NavigableMap<Long, Integer> desc = result.descendingMap();
        int cnt = 0;
        double _95 = total * 0.05;
        long _95time = 0;
        for (Map.Entry<Long, Integer> entry : desc.entrySet()) {
            //System.out.printf("timeusage : %d - %d\n", entry.getKey(), entry.getValue());
            Integer value = entry.getValue();
            cnt += value;
            if (cnt >= _95) {
                _95time = entry.getKey();
                break;
            }
        }
        System.out.printf("min : %d, max : %d\n", result.firstKey(), desc.firstKey());
        System.out.printf("average : %d\n", timeSum / reqNum);
        System.out.printf("%%95 : %d\n", _95time);
    }
}
