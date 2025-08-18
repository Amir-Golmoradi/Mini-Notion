-- Junction Table between Categories and Tasks

CREATE TABLE task_categories
(
    task_id     SMALLINT REFERENCES tasks (id),
    category_id SMALLINT REFERENCES categories (id),
    PRIMARY KEY (task_id, category_id)
);
