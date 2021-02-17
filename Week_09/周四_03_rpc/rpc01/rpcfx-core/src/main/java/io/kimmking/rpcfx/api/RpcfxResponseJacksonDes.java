package io.kimmking.rpcfx.api;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TreeTraversingParser;

import java.io.IOException;

public class RpcfxResponseJacksonDes {

    public static RpcfxResponse deserialize(ObjectMapper mapper, String jsonstr, Class<?> c, Class<?> resultC) throws IOException, JsonProcessingException {

        if (mapper == null) {
            mapper = new ObjectMapper();
        }

        JsonNode root = mapper.readTree(jsonstr);
        JsonParser p = new TreeTraversingParser(root);
        RpcfxResponse res = (RpcfxResponse) mapper.readValue(p, c);

        // result
        JsonNode result = root.get("result");
        JsonParser p2 = new TreeTraversingParser(result);
        Object o = mapper.readValue(p2, resultC);
        res.setResult(o);

        return res;
    }
}
