create table users (
  user_id  varchar(256) not null,
  password varchar(256) not null
);

alter table users
  add primary key (user_id);

create table user_roles (
  id      bigserial    not null,
  user_id varchar(256) not null,
  role    varchar(256) not null
);

alter table user_roles add primary key (id);
alter table user_roles add foreign key (user_id) references users (user_id);
create index user_roles_user_id on user_roles (user_id);

