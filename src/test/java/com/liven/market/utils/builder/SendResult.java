package com.liven.market.utils.builder;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.liven.market.service.dto.response.Response;
import lombok.Getter;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MvcResult;

import java.nio.charset.StandardCharsets;
import java.util.List;

public class SendResult {
    @Getter
    private final MvcResult mvcResult;
    private final MockHttpServletResponse response;
    private final ObjectMapper objectMapper;

    public SendResult(ObjectMapper objectMapper, MvcResult mvcResult) {
        this.mvcResult = mvcResult;
        this.response = mvcResult.getResponse();
        this.objectMapper = objectMapper;
    }

    public Response<Object> convertToResponse() {
        return this.convertToResponseOf(Object.class);
    }

    public <C> Response<C> convertToResponseOf(Class<C> cClass) {
        var type = this.objectMapper.getTypeFactory().constructParametricType(Response.class, cClass);
        return convertTo(type);
    }

    public <C> Response<List<C>> convertToResponseListOf(Class<C> cClass) {
        var typeFactory = this.objectMapper.getTypeFactory();
        var arrayType = typeFactory.constructCollectionType(List.class, cClass);
        var type = typeFactory.constructParametricType(Response.class, arrayType);
        return convertTo(type);
    }

    public <C> C convertTo(JavaType javaType) {
        try {
            return this.objectMapper.readValue(response.getContentAsString(StandardCharsets.UTF_8), javaType);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
