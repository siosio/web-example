create table client (
  client_id bigserial not null,
  name      varchar(256)          not null
);

alter table client
  add primary key (client_id);
