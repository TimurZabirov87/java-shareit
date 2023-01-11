DROP TABLE IF EXISTS requests CASCADE;
DROP TABLE IF EXISTS comments CASCADE;
DROP TABLE IF EXISTS bookings CASCADE;
DROP TABLE IF EXISTS users CASCADE;
DROP TABLE IF EXISTS items CASCADE;

CREATE TABLE IF NOT EXISTS users (
                                     id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
                                     name VARCHAR(255) NOT NULL,
                                     email VARCHAR(512) NOT NULL,
                                     CONSTRAINT UQ_USER_EMAIL UNIQUE (email)
);

CREATE TABLE IF NOT EXISTS requests (
                                     id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
                                     description VARCHAR(512) NOT NULL,
                                     created TIMESTAMP WITHOUT TIME ZONE NOT NULL,
                                     requestor_id INTEGER REFERENCES users(id));

CREATE TABLE IF NOT EXISTS items (
                                     id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
                                     name VARCHAR(255) NOT NULL,
                                     description VARCHAR(512) NOT NULL,
                                     is_available BOOLEAN NOT NULL,
                                     owner_id INTEGER REFERENCES users(id) ON DELETE CASCADE,
                                     request_id INTEGER REFERENCES requests(id)
);

CREATE TABLE IF NOT EXISTS bookings (
                                     id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
                                     start_date TIMESTAMP WITHOUT TIME ZONE NOT NULL,
                                     end_date TIMESTAMP WITHOUT TIME ZONE NOT NULL,
                                     item_id INTEGER REFERENCES items(id) ON DELETE CASCADE,
                                     booker_id INTEGER REFERENCES users(id) ON DELETE CASCADE,
                                     status VARCHAR(8) NOT NULL
);

CREATE TABLE IF NOT EXISTS comments (
                                     id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
                                     text VARCHAR(512) NOT NULL,
                                     item_id INTEGER REFERENCES items(id) ON DELETE CASCADE,
                                     author_id INTEGER REFERENCES users(id) ON DELETE CASCADE,
                                     created TIMESTAMP WITHOUT TIME ZONE NOT NULL
);

