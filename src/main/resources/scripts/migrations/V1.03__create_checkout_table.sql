CREATE TABLE MarketApp.checkouts
(
    id             uuid      NOT NULL primary key,
    basket_fk      uuid      not null,
    payment_method varchar(255),
    createdAt      timestamp not null,
    updatedAt      timestamp not null,

    CONSTRAINT BasketUserFK foreign key (basket_fk) references MarketApp.baskets (id)
);