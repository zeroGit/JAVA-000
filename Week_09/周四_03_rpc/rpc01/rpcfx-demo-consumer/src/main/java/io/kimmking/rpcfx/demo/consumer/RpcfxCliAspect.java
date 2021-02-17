package io.kimmking.rpcfx.demo.consumer;

import com.fasterxml.jackson.databind.*;
import io.kimmking.rpcfx.api.RpcfxRequest;
import io.kimmking.rpcfx.api.RpcfxResponse;
import io.kimmking.rpcfx.api.RpcfxResponseJacksonDes;
import io.kimmking.rpcfx.demo.api.ServiceDesc;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Aspect
public class RpcfxCliAspect {

    @Around("execution(* io.kimmking.rpcfx.demo.consumer.UserServiceIml.find*(..)) || " +
            "execution(* io.kimmking.rpcfx.demo.consumer.OrderServiceIml.find*(..))")
    public Object find(ProceedingJoinPoint jp) {

        try {
            Object ret = jp.proceed();

            Object ori = jp.getTarget();

            ServiceDesc sd = null;

            if (ori instanceof ServiceDesc) {
                sd = (ServiceDesc) ori;
            }
            if (sd == null) {
                return null;
            }

            return invoke(sd.desc(), jp.getSignature().getName(), jp.getArgs(), ret.getClass());

        } catch (Throwable throwable) {
            System.out.println("throwable - " + throwable.toString());
            throwable.printStackTrace();
            return null;
        }
    }

    public Object invoke(String service, String method, Object[] params, Class<?> retClass) throws Throwable {

        // 加filter地方之二
        // mock == true, new Student("hubao");

        RpcfxRequest request = new RpcfxRequest();
        request.setServiceClass(service);
        request.setMethod(method);
        request.setParams(params);

        //if (null != filters) {
        //    for (Filter filter : filters) {
        //        if (!filter.filter(request)) {
        //            return null;
        //        }
        //    }
        //}

        RpcfxResponse response = post(request, "http://localhost:8080/", retClass);
        //RpcfxResponse response = post(request, "http://47.93.207.16:9098/", retClass);

        // 加filter地方之三
        // Student.setTeacher("cuijing");

        // 这里判断response.status，处理异常
        // 考虑封装一个全局的RpcfxException

        //return objectMapper.readValue(response.getResult().toString(), retClass);
        System.out.println(String.format("resp get : %s, %b, %s\n", response.getResult().getClass(), response.getStatus(), response.getException()));
        return response.getResult();
    }

    private RpcfxResponse post(RpcfxRequest req, String url, Class<?> retClass) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        String reqJson = objectMapper.writeValueAsString(req);
        System.out.println("req json: " + reqJson);

        // 1.可以复用client
        // 2.尝试使用httpclient或者netty client

        /*
        OkHttpClient client = new OkHttpClient();
        final Request request = new Request.Builder()
                .url(url)
                .post(RequestBody.create(MediaType.get("application/json; charset=utf-8"), reqJson))
                .build();
        String respJson = client.newCall(request).execute().body().string();
        */
        NettyHttpClient client = null;
        String respJson = "";
        try {
            client = NettyHttpClient.newCli(url, reqJson);
            respJson = client.getResBody();
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("resp json: " + respJson);

        return RpcfxResponseJacksonDes.deserialize(objectMapper, respJson, RpcfxResponse.class, retClass);
    }
}
