CREATE TABLE categories
(
    id      SMALLSERIAL PRIMARY KEY,
    name    VARCHAR(50) NOT NULL,
    user_id SMALLINT REFERENCES users (id)
);
