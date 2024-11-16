package com.liven.market.utils.builder;

import com.liven.market.utils.JsonUtils;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class GenericRequest {
    private final MockMvc mockMvc;
    private final JsonUtils jsonUtils;
    private final String path;
    private final String baseUrl;
    private final HttpMethod method;
    private final Map<String, String> queryParams = new HashMap<>();
    private final Map<String, String> params = new HashMap<>();
    private final Map<String, String> headers = new HashMap<>();
    private MediaType mediaType = MediaType.APPLICATION_JSON;
    private String body;

    public GenericRequest(MockMvc mockMvc, JsonUtils jsonUtils, String path, String baseUrl, HttpMethod method) {
        this.mockMvc = mockMvc;
        this.jsonUtils = jsonUtils;
        this.path = path;
        this.baseUrl = baseUrl;
        this.method = method;
    }

    public GenericRequest header(String header, String value) {
        this.headers.put(header, value);
        return this;
    }

    public GenericRequest query(String query, String value) {
        this.queryParams.put(query, value);
        return this;
    }

    public GenericRequest query(String query, List<String> list) {
        this.queryParams.put(query, String.join(",", list));
        return this;
    }

    public GenericRequest setParam(String param, String value) {
        var paramB = String.format("{%s}", param);
        this.params.put(paramB, value);
        return this;
    }

    public GenericRequest contentType(MediaType mediaType) {
        this.mediaType = mediaType;
        return this;
    }

    public SendResult send() {
        return new SendResult(jsonUtils.getObjectMapper(), this.sendRequest());
    }

    protected GenericRequest body(String body) {
        this.body = body;
        return this;
    }

    protected GenericRequest body(Object body) {
        try {
            this.body = jsonUtils.objectToJson(body);
            return this;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    protected MvcResult sendRequest() {
        MockHttpServletRequestBuilder requestBuilders;
        switch (this.method) {
            case GET:
                requestBuilders = MockMvcRequestBuilders.get(this.buildUrl());
                break;
            case POST:
                requestBuilders = MockMvcRequestBuilders.post(this.buildUrl());
                break;
            case PUT:
                requestBuilders = MockMvcRequestBuilders.put(this.buildUrl());
                break;
            case DELETE:
                requestBuilders = MockMvcRequestBuilders.delete(this.buildUrl());
                break;
            default:
                throw new RuntimeException("Método não especificado");
        }

        this.buildHeaders(requestBuilders);

        if (this.body != null) requestBuilders.content(this.body);
        requestBuilders.contentType(mediaType);

        try {
            return this.mockMvc.perform(requestBuilders).andReturn();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void buildHeaders(MockHttpServletRequestBuilder requestBuilders) {
        this.headers.forEach(requestBuilders::header);
    }

    private String buildUrl() {
        var url = this.baseUrl + this.path;

        for (var el : this.params.entrySet()) {
            url = url.replace(el.getKey(), el.getValue());
        }

        return buildQueryParams(url);
    }

    private String buildQueryParams(String url) {
        if (this.queryParams.isEmpty()) return url;

        List<String> queryList = new ArrayList<>();
        this.queryParams.forEach((query, value) -> {
            queryList.add(String.format("%s=%s", query, value));
        });

        var query = String.join("&", queryList);

        return url + "?" + query;
    }

    public enum HttpMethod {
        GET,
        POST,
        PUT,
        DELETE
    }
}
