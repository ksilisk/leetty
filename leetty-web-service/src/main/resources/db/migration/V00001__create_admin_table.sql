create table if not exists admins
(
    admin_id   SERIAL PRIMARY KEY,
    user_id    bigint NOT NULL UNIQUE,
    added_date TIMESTAMP DEFAULT LOCALTIMESTAMP(0)
)