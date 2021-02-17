package io.kimmking.rpcfx.api;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonTypeResolver;
import lombok.Data;

@Data
//@JsonIgnoreProperties({"status"})
public class RpcfxResponse {
    //@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS)
    private Object result;
    private Boolean status;
    private String exception;
}
