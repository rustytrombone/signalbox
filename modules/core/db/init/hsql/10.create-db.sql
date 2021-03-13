-- begin SIGNALBOX_SIGNAL
create table SIGNALBOX_SIGNAL (
    ID varchar(36) not null,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    GENERATOR_ID varchar(36),
    NAME varchar(255),
    VALUE_ varchar(255),
    --
    primary key (ID)
)^
-- end SIGNALBOX_SIGNAL
-- begin SIGNALBOX_GENERATOR
create table SIGNALBOX_GENERATOR (
    ID varchar(36) not null,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    NAME varchar(255) not null,
    HANDLER_ varchar(50) not null,
    DESCRIPTION longvarchar,
    QUERY longvarchar not null,
    STATUS integer not null,
    --
    primary key (ID)
)^
-- end SIGNALBOX_GENERATOR
-- begin SIGNALBOX_ITEM
create table SIGNALBOX_ITEM (
    ID varchar(36) not null,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    DTYPE varchar(31),
    --
    TYPE_ integer,
    --
    primary key (ID)
)^
-- end SIGNALBOX_ITEM
-- begin SIGNALBOX_FX_PRICE
create table SIGNALBOX_FX_PRICE (
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
    INSTANT timestamp with time zone,
    VAL decimal(19, 6),
    --
    primary key (ID)
)^
-- end SIGNALBOX_FX_PRICE
-- begin SIGNALBOX_MARKOV_LAB
create table SIGNALBOX_MARKOV_LAB (
    ID varchar(36) not null,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    NAME varchar(255),
    --
    primary key (ID)
)^
-- end SIGNALBOX_MARKOV_LAB
-- begin SIGNALBOX_FX_PRICE_DC
create table SIGNALBOX_FX_PRICE_DC (
    ID varchar(36) not null,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    primary key (ID)
)^
-- end SIGNALBOX_FX_PRICE_DC
-- begin SIGNALBOX_FX_PRICE_TS
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
)^
-- end SIGNALBOX_FX_PRICE_TS
