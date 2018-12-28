INSERT INTO studentGroup (group_id, group_name, create_date, finish_date)
VALUES (1, 'A', '2016-08-27', '2020-06-30'),
       (2, 'B', '2015-07-27', '2019-06-29'),
       (3, 'C', '2011-08-04', '2015-06-28');

INSERT INTO student (student_id, student_name, surname, birth_date, group_id, group_name)
VALUES (1, 'Ann',  'Glush',  '1999-02-04', 1, 'A'),
       (2, 'Zen',  'Ro',     '1998-07-15', 1, 'A'),
       (3, 'Alex', 'Tsim',   '1997-11-15', 2, 'B'),
       (4, 'Sem',  'Petr',   '1998-01-05', 2, 'B'),
       (5, 'Dari', 'Rovin',  '1995-12-05', 3, 'C'),
       (6, 'Ann',  'Shevl',  '1994-03-29', 3, 'C');