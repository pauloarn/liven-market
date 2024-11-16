package com.liven.market.utils.builder;

import com.liven.market.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

@Component
public class MvcBuilder {

    private final JsonUtils jsonUtils;
    private Optional<MockMvc> mockMvcOp;

    @Autowired
    public MvcBuilder(JsonUtils jsonUtils) {
        this.jsonUtils = jsonUtils;
        this.mockMvcOp = Optional.empty();
    }

    @Autowired(required = false)
    public void setMockMvcOp(MockMvc mockMvc) {
        this.mockMvcOp = Optional.ofNullable(mockMvc);
    }

    public Builder builder() {
        return new Builder(getMockMvc(), jsonUtils);
    }

    public Builder builder(String baseUrl) {
        return new Builder(getMockMvc(), jsonUtils, baseUrl);
    }

    private MockMvc getMockMvc() {
        return this.mockMvcOp.orElseThrow();
    }

    public static class Builder {
        private final MockMvc mockMvc;
        private final JsonUtils jsonUtils;
        private String baseUrl;

        public Builder(MockMvc mockMvc, JsonUtils jsonUtils) {
            this.mockMvc = mockMvc;
            this.jsonUtils = jsonUtils;
        }

        public Builder(MockMvc mockMvc, JsonUtils jsonUtils, String baseUrl) {
            this.mockMvc = mockMvc;
            this.baseUrl = baseUrl;
            this.jsonUtils = jsonUtils;
        }

        public Builder baseUrl(String baseUrl) {
            this.baseUrl = baseUrl;
            return this;
        }

        public GetRequest get() {
            return new GetRequest(mockMvc, jsonUtils, "", baseUrl, GenericRequest.HttpMethod.GET);
        }

        public GetRequest get(String path) {
            return new GetRequest(mockMvc, jsonUtils, path, baseUrl, GenericRequest.HttpMethod.GET);
        }

        public PostRequest post() {
            return new PostRequest(mockMvc, jsonUtils, "", baseUrl, GenericRequest.HttpMethod.POST);
        }

        public PostRequest post(String path) {
            return new PostRequest(mockMvc, jsonUtils, path, baseUrl, GenericRequest.HttpMethod.POST);
        }

        public PutRequest put() {
            return new PutRequest(mockMvc, jsonUtils, "", baseUrl, GenericRequest.HttpMethod.PUT);
        }

        public PutRequest put(String path) {
            return new PutRequest(mockMvc, jsonUtils, path, baseUrl, GenericRequest.HttpMethod.PUT);
        }

        public DeleteRequest del() {
            return new DeleteRequest(mockMvc, jsonUtils, "", baseUrl, GenericRequest.HttpMethod.DELETE);
        }

        public DeleteRequest del(String path) {
            return new DeleteRequest(mockMvc, jsonUtils, path, baseUrl, GenericRequest.HttpMethod.DELETE);
        }
    }
}
