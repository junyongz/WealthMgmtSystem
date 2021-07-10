create table if not exists wms_stocks (
    id identity primary key,
    code varchar(50) not null,
    name varchar(200) not null,
    market_place varchar(100) not null,
    added_date timestamp not null,
    currency varchar(3) not null,
    market_value decimal(20,3) not null,
    invested_amount decimal(20,3) not null,
    unit_number int,
    market_unit_price decimal(8,3),
    invested_unit_price decimal(8,3)
);

create table if not exists wms_stocks_history (
    id identity primary key,
    code varchar(50) not null,
    name varchar(200) not null,
    market_place varchar(100) not null,
    added_date timestamp not null,
    currency varchar(3) not null,
    market_value decimal(20,3) not null,
    invested_amount decimal(20,3) not null,
    unit_number int,
    market_unit_price decimal(8,3),
    invested_unit_price decimal(8,3),
    added_by varchar(30)
);

create table if not exists wms_dividend (
    id identity primary key,
    stock_id bigint not null,
    currency varchar(3) not null,
    amount decimal(20,3) not null,
    added_date timestamp not null
);