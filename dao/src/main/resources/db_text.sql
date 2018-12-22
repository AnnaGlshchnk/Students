INSERT INTO studentGroup (group_id, name, create_date, finish_date)
VALUES (1, 'A', '2016-08-27', '2020-06-30'),
       (2, 'B', '2015-07-27', '2019-06-29'),
       (3, 'C', '2011-08-04', '2015-06-28');

INSERT INTO student (student_id, name, surname, birth_date, group_id)
VALUES (1, 'Ann',  'Glush',  '1999-02-04', 1),
       (2, 'Zen',  'Ro',     '1998-07-15', 1),
       (3, 'Alex', 'Tsim',   '1997-11-15', 2),
       (4, 'Sem',  'Petr',   '1998-01-05', 2),
       (5, 'Dari', 'Rovin',  '1995-12-05', 3),
       (6, 'Ann',  'Shevl',  '1994-03-29', 3);