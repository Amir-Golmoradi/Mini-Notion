CREATE TYPE task_status AS ENUM ('PENDING', 'COMPLETED', 'DELETED');

CREATE TABLE tasks
(
    id          SMALLSERIAL PRIMARY KEY,
    title       VARCHAR(150) NOT NULL,
    subtitle    VARCHAR(150) NOT NULL,
    description VARCHAR      NOT NULL,
    status      task_status  NOT NULL    DEFAULT 'PENDING',
    is_deleted  BOOLEAN      NOT NULL    DEFAULT FALSE,
    created_at  TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at  TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    user_id     SMALLINT REFERENCES users (id)
);