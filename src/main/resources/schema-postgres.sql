DROP TABLE IF EXISTS transactions;
DROP TABLE IF EXISTS consumable;
DROP TABLE IF EXISTS categories;
DROP TABLE IF EXISTS users_roles;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS roles;

DROP SEQUENCE IF EXISTS roles_seq;
DROP SEQUENCE IF EXISTS categories_seq;
CREATE SEQUENCE roles_seq START WITH 100000;
CREATE SEQUENCE categories_seq START WITH 100000;

CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE users
(
    id         UUID      DEFAULT uuid_generate_v4() PRIMARY KEY,
    email      VARCHAR(255)            NOT NULL,
    password   VARCHAR(255)            NOT NULL,
    name       VARCHAR(45)             NOT NULL,
    registered TIMESTAMP DEFAULT now() NOT NULL,
    last_logon TIMESTAMP,
    enabled    BOOLEAN   DEFAULT TRUE  NOT NULL,
    comment    VARCHAR(255),
    CONSTRAINT unique_user_idx UNIQUE (email)
);

CREATE TABLE roles
(
  id   INTEGER DEFAULT nextval('roles_seq') PRIMARY KEY,
  name VARCHAR(45) NOT NULL

);

CREATE TABLE users_roles
(
  role_id INTEGER NOT NULL,
  user_id UUID NOT NULL,
  FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
  FOREIGN KEY (role_id) REFERENCES roles (id) ON DELETE CASCADE,
  CONSTRAINT users_roles_idx UNIQUE (user_id, role_id)
);

CREATE TABLE categories
(
    id   INTEGER DEFAULT nextval('categories_seq') PRIMARY KEY,
    name VARCHAR(50) NOT NULL
);


CREATE TABLE consumable
(
    id          UUID DEFAULT uuid_generate_v4() PRIMARY KEY,
    name        VARCHAR(255) NOT NULL,
    contract    VARCHAR(255),
    price       INTEGER,
    part_number VARCHAR(60),
    category_id INTEGER,
    status      INTEGER      NOT NULL,
    comment     VARCHAR(255),
    FOREIGN KEY (category_id) REFERENCES categories (id) ON DELETE SET NULL
);

CREATE TABLE transactions
(
  id            UUID      DEFAULT uuid_generate_v4() PRIMARY KEY,
  type          VARCHAR(1) NOT NULL,
  consumable_id UUID       NOT NULL,
  author_id     UUID       NOT NULL,
  date_time     TIMESTAMP DEFAULT now(),
  comment       varchar(255),
  FOREIGN KEY (consumable_id) REFERENCES consumable (id) ON DELETE CASCADE,
  FOREIGN KEY (author_id) REFERENCES users (id) ON DELETE SET NULL
);