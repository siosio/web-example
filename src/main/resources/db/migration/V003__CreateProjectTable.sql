-- プロジェクト
create table project (
  project_id bigserial    not null,
  name       varchar(256) not null,
  type       varchar(32)  not null,
  client_id  bigint       not null
);

alter table project
  add primary key (project_id);

alter table project
  add foreign key (client_id) references client (client_id);

-- プロジェクト期間
create table project_period (
  id         bigserial not null,
  project_id bigint    not null,
  start_date date      not null,
  end_date   date      not null
);

alter table project_period
  add foreign key (project_id) references project (project_id);

