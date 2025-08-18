CREATE TABLE comments
(
    id         SMALLSERIAL PRIMARY KEY,
    content    TEXT NOT NULL,
    task_id    SMALLINT REFERENCES tasks (id),
    user_id    SMALLINT REFERENCES users (id),
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP

);