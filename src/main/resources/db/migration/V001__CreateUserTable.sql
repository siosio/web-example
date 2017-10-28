create table user (
  user_id  varchar2(256) not null,
  password varchar2(256) not null
);

alter table user
  add primary key (user_id);

create table user_roles (
  id      bigint auto_increment not null,
  user_id varchar2(256)         not null,
  role    varchar2(256)         not null,
);

alter table user_roles
  add primary key (id);
alter table user_roles
  add foreign key (user_id) references user (user_id);
create index user_roles_user_id
  on user_roles (user_id);

