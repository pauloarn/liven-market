package com.liven.market.utils.builder;

import com.liven.market.utils.JsonUtils;
import org.springframework.test.web.servlet.MockMvc;

public class PutRequest extends GenericRequest {
    public PutRequest(MockMvc mockMvc, JsonUtils jsonUtils, String path, String baseUrl, HttpMethod method) {
        super(mockMvc, jsonUtils, path, baseUrl, method);
    }

    public PutRequest body(Object body) {
        super.body(body);
        return this;
    }

    public PutRequest body(String body) {
        super.body(body);
        return this;
    }
}
