INSERT INTO users (id, email, password, name, comment)
VALUES ('0d9b7adf-7879-4c4d-915f-d7cb0419028a','admin@domain.com','12345','Администратор1',''),
       ('974fd922-c6e7-4950-ad5b-68be50a2b5ce','admin2@domain.com','12345','Администратор2',''),
       ('63ee60d7-b779-45bd-89a6-f3607ccab181','user1@domain.com','12345','Пользователь1',''),
       ('ccd9ef15-e9ac-4a7a-8f36-33d621ef3b91','user2@domain.com','12345','Пользователь2','');

INSERT INTO roles (id, name)
VALUES (1000001, 'Администратор системы'),
       (1000002, 'Пользователь системы');

INSERT INTO users_roles (role_id, user_id)
VALUES (1000001, '0d9b7adf-7879-4c4d-915f-d7cb0419028a'),
       (1000002, '0d9b7adf-7879-4c4d-915f-d7cb0419028a'),
       (1000001, '974fd922-c6e7-4950-ad5b-68be50a2b5ce'),
       (1000002, '974fd922-c6e7-4950-ad5b-68be50a2b5ce'),
       (1000002, '63ee60d7-b779-45bd-89a6-f3607ccab181'),
       (1000002, 'ccd9ef15-e9ac-4a7a-8f36-33d621ef3b91');

INSERT INTO consumable_category (name)
VALUES ('Тонер-картриджи'),
       ('Барабаны'),
       ('Кабель силовой'),
       ('Кабель слаботочный для видео'),
       ('Кабель слаботочный UTP'),
       ('Манипуляторы мышь'),
       ('Клавиатуры'),
       ('Комплектующие - HDD'),
       ('Комплектующие - SSD'),
       ('Мониторы');

INSERT INTO consumable (name, contract, price, part_number, consumable_category_id, status)
VALUES ('Картридж Galaprint HP 35A, HP 36A, HP 85A','1507/21/148-М',300,'CB435A, CB436A, CE285A',100001,3),
       ('Картридж Galaprint HP 35A, HP 36A, HP 85A','1507/21/148-М',300,'CB435A, CB436A, CE285A',100001,3),
       ('Картридж Galaprint HP 35A, HP 36A, HP 85A','1507/21/148-М',300,'CB435A, CB436A, CE285A',100001,3),
       ('Картридж Galaprint HP 78A, Canon 728','1507/21/148-М',400,'CE278A',100001,3),
       ('Картридж NV-Print HP 25X','1507/21/148-М',700,'CE255X',100001,3),
       ('Картридж NV-Print HP 25X','1507/21/148-М',700,'CE255X',100001,3),
       ('Кабель CableExpert HDMI 15 м','1507/21/148-М',1500,'noname',100004,3),
       ('Кабель CableExpert HDMI 15 м','1507/21/148-М',1500,'noname',100004,3),
       ('Кабель CableExpert HDMI 15 м','1507/21/148-М',1500,'noname',100004,3),
       ('Кабель CableExpert HDMI 15 м','1507/21/148-М',1500,'noname',100004,3),
       ('Мышь Logitech M100','1507/21/148-М',200,'M100',100006,3),
       ('Мышь Logitech M100','1507/21/148-М',200,'M100',100006,3),
       ('Мышь Logitech M100','1507/21/148-М',200,'M100',100006,3),
       ('Мышь Logitech M100','1507/21/148-М',200,'M100',100006,3),
       ('Мышь Logitech M100','1507/21/148-М',200,'M100',100006,3),
       ('Мышь Logitech M100','1507/21/148-М',200,'M100',100006,3),
       ('Мышь Logitech M100','1507/21/148-М',200,'M100',100006,3),
       ('Kingston 480G SSD','1507/21/148-М',3500,'SA400S37/480G',100009,3),
       ('Kingston 480G SSD','1507/21/148-М',3500,'SA400S37/480G',100009,3),
       ('Kingston 480G SSD','1507/21/148-М',3500,'SA400S37/480G',100009,3),
       ('Kingston 480G SSD','1507/21/148-М',3500,'SA400S37/480G',100009,3);

INSERT INTO consumable (id, name, contract, price, part_number, consumable_category_id, status)
VALUES ('cb0cd54a-b6b2-4d2d-829e-7f0128ac7db6','Картридж Galaprint HP 35A, HP 36A, HP 85A','1507/21/148-М',300,'CB435A, CB436A, CE285A',100001,3),
       ('64113547-eb2c-4856-a179-885e33a5b7e4','Картридж Galaprint HP 35A, HP 36A, HP 85A','1507/21/148-М',300,'CB435A, CB436A, CE285A',100001,3),
       ('9691f3f1-3043-4d04-90eb-f1b88b1356ab','Картридж Galaprint HP 35A, HP 36A, HP 85A','1507/21/148-М',300,'CB435A, CB436A, CE285A',100001,3),
       ('81f4277a-68bc-45e5-b275-e035ac63251d','Картридж Galaprint HP 78A, Canon 728','1507/21/148-М',400,'CE278A',100001,3),
       ('46ec581e-79e3-46be-938f-d0219cfa9335','Картридж NV-Print HP 25X','1507/21/148-М',700,'CE255X',100001,3),
       ('ea1fbbf0-f13e-4cf8-a71d-3d8eceb843a3','Картридж NV-Print HP 25X','1507/21/148-М',700,'CE255X',100001,3);

INSERT INTO transactions (type, consumable_id, author_id, comment)
VALUES (4,'cb0cd54a-b6b2-4d2d-829e-7f0128ac7db6','0d9b7adf-7879-4c4d-915f-d7cb0419028a','1507 / Директор'),
       (4,'64113547-eb2c-4856-a179-885e33a5b7e4','0d9b7adf-7879-4c4d-915f-d7cb0419028a','1507 / 215'),
       (4,'9691f3f1-3043-4d04-90eb-f1b88b1356ab','0d9b7adf-7879-4c4d-915f-d7cb0419028a','930 / 51'),
       (4,'81f4277a-68bc-45e5-b275-e035ac63251d','0d9b7adf-7879-4c4d-915f-d7cb0419028a','1507 / 113'),
       (4,'46ec581e-79e3-46be-938f-d0219cfa9335','63ee60d7-b779-45bd-89a6-f3607ccab181','1507 / 114'),
       (4,'ea1fbbf0-f13e-4cf8-a71d-3d8eceb843a3','63ee60d7-b779-45bd-89a6-f3607ccab181','1507 / 114');