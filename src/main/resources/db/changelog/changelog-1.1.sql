--liquibase formatted sql
--changeset varya:2

alter table if exists store.users add column role varchar(10) default('ROLE_USER') not null;
