CREATE  TABLE MarketApp.users(
   id      uuid NOT NULL primary key,
   name varchar(255),
   email varchar(255) NOT NULL UNIQUE,
   password varchar(255) not null,
   createdAt timestamp not null,
   updatedAt timestamp not null
);
