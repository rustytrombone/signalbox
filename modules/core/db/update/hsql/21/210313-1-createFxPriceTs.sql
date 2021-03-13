create table SIGNALBOX_FX_PRICE_TS (
    ID varchar(36) not null,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    TICKER varchar(15),
    VAL decimal(19, 6),
    TYPE_ integer,
    --
    primary key (ID)
);