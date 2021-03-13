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
);