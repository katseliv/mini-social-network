CREATE SCHEMA socialnetwork;

CREATE TABLE socialnetwork.users
(
    id         SERIAL PRIMARY KEY,
    first_name VARCHAR(50),
    last_name  VARCHAR(50),
    avatar     BYTEA,
    status     VARCHAR(255),
    bio        TEXT,
    username   VARCHAR(100) NOT NULL UNIQUE,
    email      VARCHAR(255) NOT NULL UNIQUE,
    password   VARCHAR(255) NOT NULL
);

CREATE TABLE socialnetwork.messages
(
    id          SERIAL PRIMARY KEY,
    sender_id   INTEGER NOT NULL,
    receiver_id INTEGER NOT NULL,
    content     TEXT,
    sent_at     TIMESTAMPTZ DEFAULT NOW(),
    FOREIGN KEY (sender_id) REFERENCES socialnetwork.users (id),
    FOREIGN KEY (receiver_id) REFERENCES socialnetwork.users (id)
);

CREATE TYPE FRIENDSHIP_STATUS AS ENUM ('PENDING', 'ACCEPTED', 'REJECTED');

CREATE TABLE socialnetwork.friends
(
    id         SERIAL PRIMARY KEY,
    user_id1   INTEGER           NOT NULL,
    user_id2   INTEGER           NOT NULL,
    status     FRIENDSHIP_STATUS NOT NULL,
    created_at TIMESTAMPTZ DEFAULT NOW(),
    FOREIGN KEY (user_id1) REFERENCES socialnetwork.users (id) ON DELETE CASCADE,
    FOREIGN KEY (user_id2) REFERENCES socialnetwork.users (id) ON DELETE CASCADE
);

CREATE TYPE TOKEN_TYPE AS ENUM ('ACCESS', 'REFRESH');

CREATE TABLE socialnetwork.jwt_tokens
(
    id      SERIAL PRIMARY KEY,
    token   VARCHAR(255) NOT NULL,
    type    TOKEN_TYPE   NOT NULL,
    user_id INTEGER      NOT NULL,
    FOREIGN KEY (user_id) REFERENCES socialnetwork.users (id) ON DELETE CASCADE
);
