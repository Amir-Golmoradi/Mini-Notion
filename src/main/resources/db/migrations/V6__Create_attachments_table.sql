CREATE TABLE attachments
(
    id        SMALLSERIAL PRIMARY KEY,
    file_name VARCHAR(100) NOT NULL,
    file_path VARCHAR(255) NOT NULL,
    task_id   SMALLINT REFERENCES tasks (id)
);