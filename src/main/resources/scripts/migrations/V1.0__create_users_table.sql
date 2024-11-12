create schema MarketApp;
CREATE SEQUENCE MarketApp.iduser_id_seq;

create table MarketApp.users(
    id        bigint    not null primary key default nextval('MarketApp.iduser_id_seq'),
    name varchar(255),
    email varchar(255) NOT NULL UNIQUE,
    password varchar(255) not null,
    createdAt timestamp not null,
    updatedAt timestamp not null
);
