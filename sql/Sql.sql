create database login_system;

create table users (
  user_id serial primary key,          --   Id unique de l'utilisateur
  username varchar(255) NOT NULL,        -- Nom d'utilisateur
  password_hash varchar(255),            -- mot de passe
  registration_date timestamp            -- Date d'inscription
);
