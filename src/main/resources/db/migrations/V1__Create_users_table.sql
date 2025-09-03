-- Todo: Because User-Task relations is One To Many, we don't need to specify the task reference in users table.
CREATE TABLE users
(
    id            SMALLSERIAL PRIMARY KEY,
    name          VARCHAR(50)  NOT NULL,
    email         VARCHAR(100) NOT NULL,
    password_hash VARCHAR(100) NOT NULL,
    created_at    TIMESTAMP WITH TIME ZONE DEFAULT now(),
    updated_at    TIMESTAMP WITH TIME ZONE DEFAULT now(),
    CONSTRAINT unique_email UNIQUE (email)

);