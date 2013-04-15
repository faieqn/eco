
INSERT INTO course_section(section_name) VALUES ('A');

INSERT INTO course(course_name) VALUES ('Sistemas Operativos 1');
INSERT INTO course(course_name) VALUES ('Sistemas Operativos 2');
INSERT INTO course(course_name) VALUES ('Analisis y Disenio 1');
INSERT INTO course(course_name) VALUES ('Inteligencia Artificial 1');

INSERT INTO "cycle"(cycle_name, start_date, end_date) VALUES ('Primer Semestre 2013', '2013-01-20', '2013-05-10');
INSERT INTO "cycle"(cycle_name, start_date, end_date) VALUES ('Vacaciones Junio 2013', '2013-06-01', '2013-06-30');
INSERT INTO "cycle"(cycle_name, start_date, end_date) VALUES ('Segundo Semestre 2013', '2013-07-15', '2013-11-28');
INSERT INTO "cycle"(cycle_name, start_date, end_date) VALUES ('Vacaciones Diciembre 2013', '2013-12-01', '2014-01-10');

INSERT INTO "user"(user_name, username, "password", salt, profile_id, email) VALUES
					('Ing. Rene Ornelyz', '123456789', '', '', 2, null),
					('Ing. Luis Espino', '123456780', '', '', 2, null),
					('Rony Arredondo', '200815456', '', '', 3, null),
					('Brian Estrada', '200819048', '', '', 3, null);

INSERT INTO course_open VALUES (0, 0, 0, 0, 1, 'rtmp://192.168.1.3:1935/live/sopes1a12013', 0, 0);

INSERT INTO course_open_schedule VALUES (0, 0, 0, 1, '07:00:00', '09:00:00');

INSERT INTO course_subscriber VALUES (0,0,0,2),(0,0,0,3);