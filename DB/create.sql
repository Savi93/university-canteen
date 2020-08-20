/* SAVEV DAVID, ID 14057, dsavev@unibz.it */

CREATE TABLE Government_fund (id serial PRIMARY KEY, balance float DEFAULT 0 CHECK (balance >= 0.0));
CREATE TABLE Account (id int PRIMARY KEY CHECK (id >= 100), surname varchar(255) NOT NULL, name varchar(255) NOT NULL, isprofessor boolean NOT NULL, balance float DEFAULT 0 CHECK (balance >= 0.0));
CREATE TABLE Employee (id serial PRIMARY KEY, name varchar(255) NOT NULL, surname varchar(255) NOT NULL);
CREATE TABLE Charge_card (id serial PRIMARY KEY, id_account int, id_employee int, FOREIGN KEY(id_account) REFERENCES Account(id) on delete set null on update cascade, FOREIGN KEY(id_employee) REFERENCES Employee(id) on delete set null on update cascade, timedate timestamp DEFAULT CURRENT_TIMESTAMP, amount float NOT NULL CHECK (amount > 0.0));
CREATE TABLE Admin (id int PRIMARY KEY CHECK(id < 100));
CREATE TABLE Meal_type (name varchar(255) PRIMARY KEY, price float DEFAULT 0 CHECK (price >= 0.0));
CREATE TABLE External_person (id serial PRIMARY KEY, surname varchar(255) NOT NULL, name varchar(255) NOT NULL);
CREATE TABLE Meal (id serial PRIMARY KEY, id_employee int, id_admin int, id_account int, id_externalperson int, mealtypename varchar(255) NOT NULL, FOREIGN KEY (id_employee) REFERENCES Employee(id) on delete set null on update cascade, FOREIGN KEY (id_admin) REFERENCES Admin(id) on delete set null on update cascade, FOREIGN KEY (id_account) REFERENCES Account(id) on delete set null on update cascade, FOREIGN KEY (id_externalperson) REFERENCES External_person(id) on delete set null on update cascade, FOREIGN KEY (mealtypename) REFERENCES Meal_type(name) on delete no action on update cascade, timedate timestamp DEFAULT CURRENT_TIMESTAMP);
