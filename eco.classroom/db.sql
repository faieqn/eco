CREATE SEQUENCE seq_user START WITH 0 INCREMENT BY 1 MINVALUE 0 NO MAXVALUE NO CYCLE;
CREATE SEQUENCE seq_course START WITH 0 INCREMENT BY 1 MINVALUE 0 NO MAXVALUE NO CYCLE;
CREATE SEQUENCE seq_cycle START WITH 0 INCREMENT BY 1 MINVALUE 0 NO MAXVALUE NO CYCLE;
CREATE SEQUENCE seq_section START WITH 0 INCREMENT BY 1 MINVALUE 0 NO MAXVALUE NO CYCLE;

CREATE TABLE user_profile (
	profile_id INTEGER NOT NULL PRIMARY KEY,
	profile_name VARCHAR(100) NOT NULL
);

CREATE TABLE "user" (
	user_id INTEGER NOT NULL PRIMARY KEY DEFAULT nextval('seq_user'),
	user_name VARCHAR(200) NOT NULL,
	username VARCHAR(9) NOT NULL UNIQUE,
	"password" CHAR(32) NOT NULL,
	salt CHAR(32) NOT NULL,
	profile_id INTEGER NOT NULL REFERENCES user_profile(profile_id),
	email VARCHAR(250) UNIQUE
);

CREATE TABLE "day" (
	day_id INTEGER NOT NULL PRIMARY KEY,
	day_name VARCHAR(20) NOT NULL
);

CREATE TABLE course (
	course_id INTEGER NOT NULL PRIMARY KEY DEFAULT nextval('seq_course'),
	course_name VARCHAR(200) NOT NULL
);


CREATE TABLE "cycle" (
	cycle_id INTEGER NOT NULL PRIMARY KEY DEFAULT nextval('seq_cycle'),
	cycle_name VARCHAR(100) NOT NULL,
	start_date DATE NOT NULL,
	end_date DATE NOT NULL
);

CREATE OR REPLACE FUNCTION check_sequencial_cycles() RETURNS TRIGGER AS $check_sequencial_cycles$
	DECLARE
		countCycles INTEGER;
	BEGIN
		SELECT COUNT(*)
		INTO countCycles
		FROM "cycle"
		WHERE (NEW.start_date BETWEEN start_date AND end_date)
		OR (NEW.end_date BETWEEN start_date AND end_date);

		IF countCycles > 0 THEN
			RAISE EXCEPTION 'Cycles overlap';
		END IF;

		RETURN NEW;
	END;
$check_sequencial_cycles$ LANGUAGE plpgsql;
CREATE TRIGGER check_sequencial_cycles BEFORE INSERT OR UPDATE ON "cycle"
	FOR EACH ROW EXECUTE PROCEDURE check_sequencial_cycles();

CREATE TABLE course_section (
	section_id INTEGER NOT NULL PRIMARY KEY DEFAULT nextval('seq_section'),
	section_name VARCHAR(5) NOT NULL
);

CREATE TABLE course_status (
	status_id INTEGER NOT NULL PRIMARY KEY,
	status_name VARCHAR(50) NOT NULL
);

CREATE TABLE course_open (
	course_id INTEGER NOT NULL REFERENCES course(course_id),
	cycle_id INTEGER NOT NULL REFERENCES "cycle"(cycle_id),
	section_id INTEGER NOT NULL REFERENCES course_section(section_id),
	professor_id INTEGER NOT NULL REFERENCES "user"(user_id),
	status_id INTEGER NOT NULL REFERENCES course_status(status_id),
	uri VARCHAR(250) NOT NULL,
	subscribers INTEGER NOT NULL DEFAULT 0,
	connected INTEGER NOT NULL DEFAULT 0,
	PRIMARY KEY (course_id, cycle_id, section_id)
);

CREATE TABLE course_open_schedule(
	course_id INTEGER NOT NULL,
	cycle_id INTEGER NOT NULL,
	section_id INTEGER NOT NULL,
	day_id INTEGER NOT NULL REFERENCES "day"(day_id),
	start_time TIME NOT NULL,
	end_time TIME NOT NULL,
	FOREIGN KEY (course_id, cycle_id, section_id) REFERENCES course_open(course_id, cycle_id, section_id),
	PRIMARY KEY (course_id, cycle_id, section_id)
);

CREATE TABLE course_subscriber (
	course_id INTEGER NOT NULL,
	cycle_id INTEGER NOT NULL,
	section_id INTEGER NOT NULL,
	user_id INTEGER NOT NULL REFERENCES "user"(user_id),
	FOREIGN KEY (course_id, cycle_id, section_id) REFERENCES course_open (course_id, cycle_id, section_id),
	PRIMARY KEY (course_id, cycle_id, section_id, user_id)
);

INSERT INTO "day" VALUES (1, 'Lunes'),(2, 'Martes'),(3, 'Miercoles'),(4, 'Jueves'), (5, 'Viernes'),
			 (6, 'Sabado'),(7, 'Doming');

INSERT INTO course_status VALUES (1, 'Desconectado'),(2, 'Conectado'),(3,'Deshabilitado');

INSERT INTO user_profile VALUES (1, 'Admin'), (2, 'Profesor'), (3, 'Estudiante');
