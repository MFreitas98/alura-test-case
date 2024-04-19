CREATE TABLE course_evaluation
(
    id           BIGINT AUTO_INCREMENT NOT NULL,
    course_id    BIGINT                NOT NULL,
    user_id      BIGINT                NOT NULL,
    score        INT                   NOT NULL,
    score_reason VARCHAR(255)          NULL,
    CONSTRAINT pk_course_evaluation PRIMARY KEY (id)
);

ALTER TABLE course_evaluation
    ADD CONSTRAINT FK_COURSE_EVALUATION_ON_COURSE FOREIGN KEY (course_id) REFERENCES course (id);

ALTER TABLE course_evaluation
    ADD CONSTRAINT FK_COURSE_EVALUATION_ON_USER FOREIGN KEY (user_id) REFERENCES user (id);

ALTER TABLE course_evaluation
    ADD CONSTRAINT uc_course_user_evaluation UNIQUE (user_id, course_id);