-- This file allow to write SQL commands that will be emitted in test and dev.
-- The commands are commented as their support depends of the database
-- insert into myentity (id, field) values(1, 'field-1');
-- insert into myentity (id, field) values(2, 'field-2');
-- insert into myentity (id, field) values(3, 'field-3');
-- alter sequence myentity_seq restart with 4;

INSERT INTO taskentity (done, priority, created, id, name) 
VALUES (false, 0, '2025-09-17 12:55:36', nextval('taskentity_SEQ'), 'Dispose trash');
INSERT INTO taskentity (done, priority, created, id, name) 
VALUES (true, 1, '2025-09-18 17:30:00', nextval('taskentity_SEQ'), 'Accomplish important stuff');
INSERT INTO taskentity (done, priority, created, id, name) 
VALUES (false, 2, '2025-09-19 15:02:26', nextval('taskentity_SEQ'), 'Recognize futileness');
