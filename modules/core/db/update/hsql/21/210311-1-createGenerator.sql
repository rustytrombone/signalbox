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
    NAME varchar(255),
    DESCRIPTION longvarchar,
    QUERY longvarchar,
    STATUS integer,
    --
    primary key (ID)
);