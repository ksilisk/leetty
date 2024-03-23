create table if not exists users
(
    user_id       bigint PRIMARY KEY,
    first_name    text NOT NULL,
    second_name   text,
    username      text
)