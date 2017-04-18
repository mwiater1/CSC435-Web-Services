# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table article (
  id                            bigint not null,
  sort_by                       varchar(255),
  published_at                  timestamp,
  author                        varchar(255),
  title                         varchar(255),
  description                   varchar(255),
  url                           varchar(255),
  url_to_image                  varchar(255),
  constraint pk_article primary key (id)
);
create sequence article_seq;

create table category (
  name                          varchar(255) not null,
  constraint pk_category primary key (name)
);

create table country (
  code                          varchar(255) not null,
  name                          varchar(255),
  constraint pk_country primary key (code)
);

create table language (
  code                          varchar(255) not null,
  name                          varchar(255),
  constraint pk_language primary key (code)
);

create table preference (
  id                            bigint not null,
  article_id                    bigint,
  favorite                      boolean,
  read                          boolean,
  constraint pk_preference primary key (id)
);
create sequence preference_seq;

create table source (
  id                            varchar(255) not null,
  url                           varchar(255),
  name                          varchar(255),
  description                   varchar(255),
  constraint pk_source primary key (id)
);

create table user (
  user_name                     varchar(255) not null,
  api_key                       uuid,
  password                      varchar(255),
  constraint pk_user primary key (user_name)
);


# --- !Downs

drop table if exists article;
drop sequence if exists article_seq;

drop table if exists category;

drop table if exists country;

drop table if exists language;

drop table if exists preference;
drop sequence if exists preference_seq;

drop table if exists source;

drop table if exists user;

