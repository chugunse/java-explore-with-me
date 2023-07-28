DROP TABLE IF EXISTS users, categories, events, requests, compilation, compilation_events, comments;
CREATE TABLE IF NOT EXISTS users
(
    id    BIGINT GENERATED BY DEFAULT AS IDENTITY,
    email VARCHAR(255) NOT NULL,
    name  VARCHAR(255) NOT NULL,
    CONSTRAINT uq_user_email UNIQUE (email),
    CONSTRAINT pk_user PRIMARY KEY (id)


);
CREATE TABLE IF NOT EXISTS categories
(
    id   BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    CONSTRAINT uq_categories_name UNIQUE (name)
);

CREATE TABLE IF NOT EXISTS events
(
    id                 BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    annotation         VARCHAR(2000)               NOT NULL,
    category_id        BIGINT                      NOT NULL,
    created_on         TIMESTAMP WITHOUT TIME ZONE,
    description        VARCHAR(7000)               NOT NULL,
    event_date         TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    initiator_id       BIGINT                      NOT NULL,
    lat                FLOAT,
    lon                FLOAT,
    paid               BOOLEAN                     NOT NULL,
    participant_limit  INT,
    published_on       TIMESTAMP WITHOUT TIME ZONE,
    request_moderation BOOLEAN                     NOT NULL,
    state              VARCHAR                     NOT NULL,
    title              VARCHAR(255)                NOT NULL,
    CONSTRAINT uq_events UNIQUE (title),
    CONSTRAINT fk_category_id FOREIGN KEY (category_id) REFERENCES categories (id) ON DELETE CASCADE,
    CONSTRAINT fk_initiator_id FOREIGN KEY (initiator_id) REFERENCES users (id) ON DELETE CASCADE
);
CREATE TABLE IF NOT EXISTS requests
(
    id           BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    created      TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    event_id     BIGINT                      NOT NULL,
    requester_id BIGINT                      NOT NULL,
    status       VARCHAR,
    CONSTRAINT fk_event_id FOREIGN KEY (event_id) REFERENCES events (id) ON DELETE CASCADE,
    CONSTRAINT fk_requester_id FOREIGN KEY (requester_id) REFERENCES users (id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS compilations
(
    id     BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    pinned BOOLEAN NOT NULL,
    title  VARCHAR(500)
);

CREATE TABLE IF NOT EXISTS compilation_events
(
    compilation_id BIGINT NOT NULL,
    event_id       BIGINT NOT NULL,
    CONSTRAINT pk_comp_event PRIMARY KEY (compilation_id, event_id),
    CONSTRAINT fk_compilation_id FOREIGN KEY (compilation_id) REFERENCES compilations (id) ON DELETE CASCADE,
    CONSTRAINT fk_event_id FOREIGN KEY (event_id) REFERENCES events (id) ON DELETE CASCADE
);
CREATE TABLE IF NOT EXISTS comments
(
    id        BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    event_id  BIGINT                      NOT NULL,
    owner_id  BIGINT                      NOT NULL,
    text      VARCHAR(2000)               NOT NULL,
    created   TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated   TIMESTAMP WITH TIME ZONE,
    changed_by VARCHAR,
    constraint fk_owner_id FOREIGN KEY (owner_id) REFERENCES users (id) ON DELETE CASCADE,
    constraint fk_event_id FOREIGN KEY (event_id) REFERENCES events (id) ON DELETE CASCADE
);
CREATE INDEX categories_name_field ON categories (name);
CREATE INDEX events_state_field ON events (state);
CREATE INDEX events_event_date_field ON events (event_date);
CREATE INDEX events_paid_field ON events (paid);
CREATE INDEX users_email_idx ON users (email);