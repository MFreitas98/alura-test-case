CREATE TABLE course
(
    id             BIGINT AUTO_INCREMENT NOT NULL,
    code           VARCHAR(10)           NOT NULL,
    instructor_id  BIGINT                NULL,
    `description`  VARCHAR(255)          NOT NULL,
    status         BIT(1)                NOT NULL,
    created_at     datetime              NOT NULL,
    inactivated_at datetime              NULL,
    CONSTRAINT pk_course PRIMARY KEY (id)
);

CREATE TABLE user
(
    id         BIGINT AUTO_INCREMENT NOT NULL,
    name       VARCHAR(255)          NOT NULL,
    user_name  VARCHAR(20)           NOT NULL,
    email      VARCHAR(255)          NOT NULL,
    `role`     VARCHAR(255)          NOT NULL,
    created_at datetime              NOT NULL,
    CONSTRAINT pk_user PRIMARY KEY (id)
);

ALTER TABLE course
    ADD CONSTRAINT uc_course_code UNIQUE (code);

ALTER TABLE user
    ADD CONSTRAINT uc_user_email UNIQUE (email);

ALTER TABLE user
    ADD CONSTRAINT uc_user_user_name UNIQUE (user_name);

ALTER TABLE course
    ADD CONSTRAINT FK_COURSE_ON_INSTRUCTOR FOREIGN KEY (instructor_id) REFERENCES user (id);