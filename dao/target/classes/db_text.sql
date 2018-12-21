INSERT INTO studentGroup (group_id, name)
VALUES (1, 'A'),
       (2, 'B'),
       (3, 'C');

INSERT INTO student (student_id, name, surname, age, group_id)
VALUES (1, 'Ann',  'Glush',  18, 1),
       (2, 'Zen',  'Ro',     19, 1),
       (3, 'Alex', 'Tsim',   17, 2),
       (4, 'Sem',  'Petr',   17, 2),
       (5, 'Dari', 'Rovin',  19, 3),
       (6, 'Ann',  'Shevl',  20, 3);