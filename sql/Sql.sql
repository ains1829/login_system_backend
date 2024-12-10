create database login_system;

create table users (
  user_id serial primary key,          --   Id unique de l'utilisateur
  email varchar(255) NOT NULL UNIQUE,        -- Nom d'utilisateur
  password_hash varchar(255),            -- mot de passe
  registration_date::timestamp(6) without time zone          -- Date d'inscription
);
