CREATE TABLE IF NOT EXISTS course
(
    id             BIGINT AUTO_INCREMENT NOT NULL,
    name           VARCHAR(255)          NOT NULL,
    code           VARCHAR(10)           NOT NULL,
    instructor_id  BIGINT                NULL,
    `description`  VARCHAR(255)          NOT NULL,
    status         BIT(1)                NOT NULL,
    created_at     datetime              NOT NULL,
    inactivated_at datetime              NULL,
    CONSTRAINT pk_course PRIMARY KEY (id)
);

ALTER TABLE course
    ADD CONSTRAINT uc_course_code UNIQUE (code);

ALTER TABLE course
    ADD INDEX idx_uc_course_status (status);

ALTER TABLE course
    ADD CONSTRAINT FK_COURSE_ON_INSTRUCTOR FOREIGN KEY (instructor_id) REFERENCES user (id);