CREATE TABLE customers
(
 "id"             serial primary key  NOT NULL,
 first_name   varchar(50) NOT NULL,
 last_name    varchar(50) NOT NULL,
 organization varchar(50) NULL
);

CREATE TABLE card_types
(
 "id"   serial primary key NOT NULL,
 type varchar(50) NOT NULL
);

CREATE TABLE payment_cards
(
 id_customer  integer primary key NOT NULL,
 card_number  integer NOT NULL,
 id_card_type integer NOT NULL,
 CONSTRAINT FK_CUSTOMER FOREIGN KEY ( id_customer ) REFERENCES customers ( "id" ),
 CONSTRAINT FK_CARD_TYPE FOREIGN KEY ( id_card_type ) REFERENCES card_types ( "id" )
);

CREATE TABLE orders
(
 "id"          serial primary key NOT NULL,
 purchase_date timestamp NOT NULL,
 delivery_date timestamp NULL,
 total_price   integer NOT NULL,
 id_customer   integer NOT NULL,
 CONSTRAINT FK_CUSTOMER FOREIGN KEY ( id_customer ) REFERENCES customers ( "id" )
);

CREATE TABLE articles
(
 "id"          serial primary key NOT NULL,
 description varchar(50) NOT NULL,
 price       integer NOT NULL
);

CREATE TABLE orders_articles
(
 "id"          serial primary key NOT NULL,
 id_order   integer NOT NULL,
 id_article integer NOT NULL,
 CONSTRAINT FK_ORDER FOREIGN KEY ( id_order ) REFERENCES "public".orders ( "id" ),
 CONSTRAINT FK_ARTICLE FOREIGN KEY ( id_article ) REFERENCES "public".articles ( "id" )
);