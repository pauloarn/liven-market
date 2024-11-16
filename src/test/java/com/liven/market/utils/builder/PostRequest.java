package com.liven.market.utils.builder;

import com.liven.market.utils.JsonUtils;
import org.springframework.test.web.servlet.MockMvc;

public class PostRequest extends GenericRequest {
    public PostRequest(MockMvc mockMvc, JsonUtils jsonUtils, String path, String baseUrl, HttpMethod method) {
        super(mockMvc, jsonUtils, path, baseUrl, method);
    }

    public PostRequest body(Object body) {
        super.body(body);
        return this;
    }

    public PostRequest body(String body) {
        super.body(body);
        return this;
    }
}
