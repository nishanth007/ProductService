CREATE TABLE category
(
    id                BIGINT AUTO_INCREMENT NOT NULL,
    created_date      datetime NULL,
    last_updated_date datetime NULL,
    `description`     VARCHAR(255) NULL,
    category          VARCHAR(255) NULL,
    CONSTRAINT pk_category PRIMARY KEY (id)
);

CREATE TABLE jt_mentor
(
    user_id BIGINT NOT NULL,
    subject VARCHAR(255) NULL,
    CONSTRAINT pk_jt_mentor PRIMARY KEY (user_id)
);

CREATE TABLE jt_student
(
    user_id BIGINT NOT NULL,
    stu_id  BIGINT NULL,
    batch   VARCHAR(255) NULL,
    CONSTRAINT pk_jt_student PRIMARY KEY (user_id)
);

CREATE TABLE jt_user
(
    id   BIGINT NOT NULL,
    name VARCHAR(255) NULL,
    CONSTRAINT pk_jt_user PRIMARY KEY (id)
);

CREATE TABLE product
(
    id                BIGINT AUTO_INCREMENT NOT NULL,
    created_date      datetime NULL,
    last_updated_date datetime NULL,
    price DOUBLE NULL,
    title             VARCHAR(255) NULL,
    category_id       BIGINT NULL,
    CONSTRAINT pk_product PRIMARY KEY (id)
);

ALTER TABLE category
    ADD CONSTRAINT uc_category_category UNIQUE (category);

ALTER TABLE jt_mentor
    ADD CONSTRAINT FK_JT_MENTOR_ON_USER FOREIGN KEY (user_id) REFERENCES jt_user (id);

ALTER TABLE jt_student
    ADD CONSTRAINT FK_JT_STUDENT_ON_USER FOREIGN KEY (user_id) REFERENCES jt_user (id);

ALTER TABLE product
    ADD CONSTRAINT FK_PRODUCT_ON_CATEGORY FOREIGN KEY (category_id) REFERENCES category (id);