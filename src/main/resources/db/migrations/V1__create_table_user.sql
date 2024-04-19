CREATE TABLE IF NOT EXISTS user
(
    id         BIGINT AUTO_INCREMENT NOT NULL,
    name       VARCHAR(255)          NOT NULL,
    user_name  VARCHAR(20)           NOT NULL,
    password   VARCHAR(255)          NOT NULL,
    email      VARCHAR(255)          NOT NULL,
    `role`     VARCHAR(255)          NOT NULL,
    created_at datetime              NOT NULL,
    CONSTRAINT pk_user PRIMARY KEY (id)
);

ALTER TABLE user
    ADD CONSTRAINT uc_user_email UNIQUE (email);

ALTER TABLE user
    ADD CONSTRAINT uc_user_user_name UNIQUE (user_name);

ALTER TABLE user
    ADD INDEX idx_user_user_name (user_name);