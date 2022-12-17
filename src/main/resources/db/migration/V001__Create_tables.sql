create table IF NOT EXISTS  schedule
(
    id   uuid           NOT NULL PRIMARY KEY,
    date date,
    coef jsonb NOT NULL
);

create table IF NOT EXISTS  category
(
    id          uuid           NOT NULL PRIMARY KEY,
    price       numeric(19, 4) NOT NULL,
    name        varchar(50),
    description varchar(1000),
    schedule_id uuid
        CONSTRAINT fk_schedule_id REFERENCES schedule
);

create table IF NOT EXISTS  room
(
    id          uuid NOT NULL PRIMARY KEY,
    places      int,
    image       varchar(255),
    number      int,
    category_id uuid
        CONSTRAINT fk_category_id REFERENCES category
);

create table IF NOT EXISTS  client
(
    id                uuid        NOT NULL PRIMARY KEY,
    first_name        varchar(50) not null,
    last_name         varchar(50) not null,
    email             varchar(50) not null,
    phone             varchar(50),
    registration_date date        not null
);

create table IF NOT EXISTS  reservation_room
(
    id               uuid not null primary key,
    reservation_date date not null,
    check_in_date    date not null,
    check_out_date   date not null,
    price            numeric(19,4) not null,
    paid             bool not null default false,

    first_name        varchar(50) not null,
    last_name         varchar(50) not null,
    email             varchar(50) not null,
    phone             varchar(50),

    room_id          uuid
        CONSTRAINT fk_room_id REFERENCES room,
    client_id        uuid
        CONSTRAINT fk_client_id REFERENCES client
)