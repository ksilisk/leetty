create table if not exists chats
(
    chat_id     bigint PRIMARY KEY,
    title       text,
    description text,
    daily_send_time text
)