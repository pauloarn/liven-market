package com.liven.market.enums;

import java.text.MessageFormat;
import java.util.ResourceBundle;

public enum MessageEnum {
    REQUISICAO_CONCLUIDA("message.api.requisicao.concluida"),
    ACESSO_NEGADO("message.api.acesso.negado"),
    ROUTE_NOT_FOUND("message.api.rout.not.found"),
    ENDPOINT_ERROR("message.api.endpoint.error"),
    VALIDATION_ERROR("message.api.validacao.error"),
    METHOD_NOT_SUPPORTED("message.api.method.not.supported"),
    MISSING_REQUEST_PARAMETER("message.api.missing.request.parameter"),
    USER_ALREADY_EXISTS("message.api.use.already.exists"),
    USER_NOT_FOUND("message.api.user.not.found"),
    SKU_ALREADY_REGISTERED("message.api.sku.already.registered"),
    AMOUNT_MUST_NOT_BE_NEGATIVE("message.api.amount.must.not.be.negative"),
    PRICE_MUST_BE_GREATER_THAN_0("message.api.price.must.be.greater.than.0"),
    PRODUCT_ON_STOCK_LESS_THAN_REQUIRED("message.api.product.on.stock.less.than.required"),
    PRODUCT_NOT_REGISTERED("message.api.product.not.registered"),
    BASKET_NOT_FOUND("message.api.basket.not.found"),
    UNKNOWN_ERROR("message.api.error.desconhecido"),

    INVALID_TOKEN("message.api.invalid.token"),
    FAIL_TO_CREATE_TOKEN("message.api.fail.to.create.token"),
    ;

    private static final ResourceBundle resourceBundle = ResourceBundle.getBundle("messages");
    private String description;

    MessageEnum(String description) {
        this.description = description;
    }

    public String getDescription() {
        return this.description;
    }

    public String getMessage(String... args) {
        String msg = resourceBundle.getString(this.description);
        return args == null ? msg : MessageFormat.format(msg, args);
    }
}
