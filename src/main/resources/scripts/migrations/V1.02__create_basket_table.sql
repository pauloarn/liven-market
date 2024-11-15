CREATE TABLE MarketApp.baskets
(
    id          uuid           NOT NULL primary key,
    user_fk     uuid           not null,
    total_value numeric(38, 2) not null,
    createdAt   timestamp      not null,
    updatedAt   timestamp      not null,

    CONSTRAINT BasketUserFK foreign key (user_fk) references MarketApp.users (id)
);

create table MarketApp.basket_product
(
    basket_fk      uuid   not null,
    product_fk     uuid   not null,
    product_amount bigint not null,

    primary key (basket_fk, product_fk),
    CONSTRAINT BasketProductBasketFk foreign key (basket_fk) references MarketApp.baskets (id),
    CONSTRAINT BasketProductProductFk foreign key (product_fk) references MarketApp.products (id)
);