DROP TABLE IF EXISTS student;
DROP TABLE IF EXISTS studentGroup;

CREATE TABLE studentGroup
(
  group_id    INT          NOT NULL AUTO_INCREMENT,
  group_name  VARCHAR(255) NOT NULL UNIQUE,
  create_date DATE         NOT NULL,
  finish_date DATE         NOT NULL,
  PRIMARY KEY (group_id)
);


CREATE TABLE student
(
  student_id   INT          NOT NULL AUTO_INCREMENT,
  student_name VARCHAR(255) NOT NULL,
  surname      VARCHAR(255) NOT NULL,
  birth_date   DATE         NOT NULL,
  group_id     INT          NOT NULL,
  PRIMARY KEY (student_id),
  FOREIGN KEY (group_id) REFERENCES studentGroup (group_id) ON DELETE CASCADE
);