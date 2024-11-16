package com.liven.market.utils.builder;


import com.liven.market.utils.JsonUtils;
import org.springframework.test.web.servlet.MockMvc;

public class GetRequest extends GenericRequest {
    public GetRequest(MockMvc mockMvc, JsonUtils jsonUtils, String path, String baseUrl, HttpMethod method) {
        super(mockMvc, jsonUtils, path, baseUrl, method);
    }
}
