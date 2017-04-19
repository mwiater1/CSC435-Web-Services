# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table articles (
  id                            bigserial not null,
  url                           varchar(1000),
  title                         varchar(1000),
  sort_by                       varchar(1000),
  author                        varchar(1000),
  source_id                     varchar(1000),
  url_to_image                  varchar(1000),
  published_at                  varchar(1000),
  description                   varchar(1000),
  constraint pk_articles primary key (id)
);

create table preferences (
  id                            bigserial not null,
  api_key                       uuid,
  article_id                    bigint,
  favorite                      boolean,
  read                          boolean,
  constraint pk_preferences primary key (id)
);

create table sources (
  id                            varchar(255) not null,
  sort_bys                      json,
  url                           varchar(1000),
  name                          varchar(1000),
  category                      varchar(1000),
  language                      varchar(1000),
  country                       varchar(1000),
  description                   varchar(1000),
  constraint pk_sources primary key (id)
);

create table users (
  user_name                     varchar(255) not null,
  password                      varchar(255),
  api_key                       uuid,
  constraint pk_users primary key (user_name)
);


# --- !Downs

drop table if exists articles cascade;

drop table if exists preferences cascade;

drop table if exists sources cascade;

drop table if exists users cascade;

