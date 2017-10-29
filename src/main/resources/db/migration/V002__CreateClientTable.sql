create table client (
  client_id bigint auto_increment not null,
  naem      varchar(256)          not null
);

alter table client
  add primary key (client_id);
