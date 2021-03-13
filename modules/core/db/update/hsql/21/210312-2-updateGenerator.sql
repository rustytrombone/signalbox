alter table SIGNALBOX_GENERATOR add column HANDLER_ varchar(50) ^
update SIGNALBOX_GENERATOR set HANDLER_ = 'A' where HANDLER_ is null ;
alter table SIGNALBOX_GENERATOR alter column HANDLER_ set not null ;
update SIGNALBOX_GENERATOR set NAME = '' where NAME is null ;
alter table SIGNALBOX_GENERATOR alter column NAME set not null ;
update SIGNALBOX_GENERATOR set QUERY = '' where QUERY is null ;
alter table SIGNALBOX_GENERATOR alter column QUERY set not null ;
update SIGNALBOX_GENERATOR set STATUS = 10 where STATUS is null ;
alter table SIGNALBOX_GENERATOR alter column STATUS set not null ;
