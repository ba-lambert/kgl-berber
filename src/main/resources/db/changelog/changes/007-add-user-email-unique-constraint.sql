--liquibase formatted sql
--changeset ba:8
ALTER TABLE users ADD CONSTRAINT users_email_key UNIQUE (email);
