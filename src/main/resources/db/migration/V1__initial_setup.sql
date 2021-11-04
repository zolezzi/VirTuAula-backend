drop table if exists teams_students;
drop table if exists accounts_classrooms;
drop table if exists teachers_students;
drop table if exists account_types_privileges;
drop table if exists student_task;
drop table if exists team;
drop table if exists level;
drop table if exists account;
drop table if exists user;
drop table if exists privilege;
drop table if exists account_type;
drop table if exists task_type;
drop table if exists option_task;
drop table if exists task;
drop table if exists lesson;
drop table if exists classroom;
create table classroom(id bigint primary key AUTO_INCREMENT, name varchar(255) not null, description varchar(255));
create table lesson(id bigint primary key AUTO_INCREMENT, name varchar(255) not null, max_note double(10,2), classroom_id bigint, constraint fk_lesson_classroom FOREIGN KEY (classroom_id) REFERENCES classroom(id));
create table task_type(id bigint primary key AUTO_INCREMENT, name varchar(255) not null);
create table task(id bigint primary key AUTO_INCREMENT, statement varchar(255) not null, answer bigint, correct_answer bigint, score double(10,2), lesson_id bigint, task_type_id bigint, constraint fk_task_lesson FOREIGN KEY (lesson_id) REFERENCES lesson(id), constraint fk_task_type FOREIGN KEY (task_type_id) REFERENCES task_type(id));
create table option_task(id bigint primary key AUTO_INCREMENT, response_value varchar(255) not null, task_id bigint, is_correct boolean not null default 0, constraint fk_option_task FOREIGN KEY (task_id) REFERENCES task(id));
create table account_type(id bigint primary key AUTO_INCREMENT, name varchar(255) not null);
create table privilege(id bigint primary key AUTO_INCREMENT, name varchar(255) not null);
create table user(id bigint primary key AUTO_INCREMENT, username varchar(255), password varchar(255), email varchar(255));
create table level(id bigint primary key AUTO_INCREMENT, name varchar(255), description varchar(255), image_path varchar(255), min_value double(20,2), max_value double(20,2), number_level int(10), name_next_level varchar(255));
create table account(id bigint primary key AUTO_INCREMENT, username varchar(255), first_name varchar(255), last_name varchar(255), email varchar(255), account_type_id bigint, account_type_role varchar(255), experience double(20,2), user_id bigint, dni int(8), level_id bigint, constraint fk_account_type FOREIGN KEY (account_type_id) REFERENCES account_type(id), constraint fk_account_user FOREIGN KEY (user_id) REFERENCES user(id), constraint fk_account_level FOREIGN KEY (level_id) REFERENCES level(id));
create table account_types_privileges(id bigint primary key AUTO_INCREMENT, account_type_id bigint, privilege_id bigint, constraint fk_account_type_privilege FOREIGN KEY (account_type_id) REFERENCES account_type(id), constraint fk_privilege_account_type FOREIGN KEY (privilege_id) REFERENCES privilege(id));
create table teachers_students(id bigint primary key AUTO_INCREMENT, teacher_id bigint, student_id bigint, constraint fk_teacher FOREIGN KEY (teacher_id) REFERENCES account(id), constraint fk_student FOREIGN KEY (student_id) REFERENCES account(id));
create table accounts_classrooms(id bigint primary key AUTO_INCREMENT, account_id bigint, classroom_id bigint, constraint fk_account_classroom FOREIGN KEY (account_id) REFERENCES account(id), constraint fk_classroom_account FOREIGN KEY (classroom_id) REFERENCES classroom(id));
create table student_task(id bigint primary key AUTO_INCREMENT, answer bigint, state varchar(15) default 'UNCOMPLETED', lesson_id bigint, account_id bigint, task_id bigint, constraint fk_student_task_lesson FOREIGN KEY (lesson_id) REFERENCES lesson(id), constraint fk_student_task_account FOREIGN KEY (account_id) REFERENCES account(id), constraint fk_student_task FOREIGN KEY (task_id) REFERENCES task(id));
create table team(id bigint primary key AUTO_INCREMENT, name varchar(255), classroom_id bigint, teacher_id bigint, constraint fk_classroom_team FOREIGN KEY (classroom_id) REFERENCES classroom(id), constraint fk_teacher_team FOREIGN KEY (teacher_id) REFERENCES account(id));
create table teams_students(id bigint primary key AUTO_INCREMENT, team_id bigint, student_id bigint, constraint fk_team FOREIGN KEY (team_id) REFERENCES team(id), constraint fk_student_team FOREIGN KEY (student_id) REFERENCES account(id));