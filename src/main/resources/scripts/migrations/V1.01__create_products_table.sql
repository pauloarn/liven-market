CREATE  TABLE MarketApp.products(
         id      uuid NOT NULL primary key,
         sku varchar(255) NOT NULL UNIQUE,
         name varchar(255) NOT NULL,
         price double not null,
         amount bigint not null,
         createdAt timestamp not null,
         updatedAt timestamp not null
);
