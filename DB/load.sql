/* SAVEV DAVID, ID 14057, dsavev@unibz.it */

INSERT INTO employee(name, surname) values ('John', 'Brown');
INSERT INTO employee(name, surname) values ('Ivan', 'McConnor');
INSERT INTO employee(name, surname) values ('Marco', 'Rossi');
INSERT INTO account values (1010, 'Savev', 'David', false, 35);
INSERT INTO account values (5093, 'Piero', 'Pierucci', true, 12);
INSERT INTO account values (100240, 'Leonardo', 'Antonucci', true, 29);
INSERT INTO government_fund(balance) values (105000);
INSERT INTO meal_type values ('Full menu', 8.64);
INSERT INTO meal_type values ('Light menu', 6.90);
INSERT INTO meal_type values ('Extra light menu', 5.20);
INSERT INTO external_person(name, surname) values('Pietro', 'Pietrini');
INSERT INTO external_person(name, surname) values('Leo', 'Guappi');
INSERT INTO admin values(1);
INSERT INTO meal(id_employee, id_account, mealtypename) values(2, 5093, 'Extra light menu');
UPDATE account set balance = balance - (select price from meal_type where name = 'Extra light menu') WHERE id = 5093;
INSERT INTO meal(id_employee, id_externalperson, mealtypename) values(1, (Select id from external_person WHERE surname = 'Guappi' AND name = 'Leo'), 'Light menu');
INSERT INTO meal(id_employee, id_externalperson, mealtypename) values(2, (Select id from external_person WHERE surname = 'Pietrini' AND name = 'Pietro'), 'Light menu');
INSERT INTO charge_card(id_account, id_employee, amount) values(1010, 3, 40);
UPDATE account set balance = balance + 40 WHERE id = 1010;



