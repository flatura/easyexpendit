DELETE
FROM transactions;
DELETE
FROM consumable;
DELETE
FROM categories;
DELETE
FROM users_roles;
DELETE
FROM roles;
DELETE
FROM users;

INSERT INTO users (id, email, password, name, comment)
VALUES ('0d9b7adf-7879-4c4d-915f-d7cb0419028a', 'admin@domain.com',
        '$2a$12$tZtd7BHTDRjqSUsvB4uSSuvOiQUnPNkSRWxLx6o0xwEBfsrjs4ruW', 'Администратор1', ''),
       ('974fd922-c6e7-4950-ad5b-68be50a2b5ce', 'admin2@domain.com',
        '$2a$12$tZtd7BHTDRjqSUsvB4uSSuvOiQUnPNkSRWxLx6o0xwEBfsrjs4ruW', 'Александр Петров', ''),
       ('63ee60d7-b779-45bd-89a6-f3607ccab181', 'user1@domain.com',
        '$2a$12$tZtd7BHTDRjqSUsvB4uSSuvOiQUnPNkSRWxLx6o0xwEBfsrjs4ruW', 'Пользователь1', ''),
       ('ccd9ef15-e9ac-4a7a-8f36-33d621ef3b91', 'user2@domain.com',
        '$2a$12$tZtd7BHTDRjqSUsvB4uSSuvOiQUnPNkSRWxLx6o0xwEBfsrjs4ruW', 'Лидия Славина', '');


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

INSERT INTO categories (id, name)
VALUES (100001, 'Тонер-картриджи (прочее)'),
       (100002, 'Барабаны'),
       (100003, 'Кабель силовой'),
       (100004, 'Кабель слаботочный для видео'),
       (100005, 'Кабель слаботочный UTP'),
       (100006, 'Манипуляторы мышь'),
       (100007, 'Клавиатуры'),
       (100008, 'Комплектующие - HDD'),
       (100009, 'Комплектующие - SSD'),
       (100010, 'Мониторы'),
       (100011, 'Тонер-картриджи: 285/435/436/712/725'),
       (100012, 'Тонер-картриджи: 278/728'),
       (100013, 'Тонер-картриджи: EXV18'),
       (100014, 'Тонер-картриджи: EXV40'),
       (100015, 'Тонер-картриджи: FX-10'),
       (100016, 'Тонер-картриджи: 225X'),
       (100017, 'Тонер-картриджи: 505X'),
       (100018, 'Тонер-картриджи: 283A'),
       (100019, 'Тонер-картриджи: 214X'),
       (100020, 'Тонер-картриджи: 226X'),
       (100021, 'Тонер-картриджи: 2612A'),
       (100022, 'Тонер-картриджи: TK340'),
       (100023, 'Тонер-картриджи: TK430');

INSERT INTO consumable (name, contract, price, part_number, category_id, status, comment)
VALUES ('Картридж Galaprint HP 35A, HP 36A, HP 85A', '1507/21/148-М', 300, 'CB435A, CB436A, CE285A', 100000, 2,
        'Поставка тестовая'),
       ('Картридж Galaprint HP 35A, HP 36A, HP 85A', '1507/21/148-М', 300, 'CB435A, CB436A, CE285A', 100000, 2,
        'Поставка тестовая'),
       ('Картридж Galaprint HP 35A, HP 36A, HP 85A', '1507/21/148-М', 300, 'CB435A, CB436A, CE285A', 100000, 2,
        'Поставка тестовая'),
       ('Картридж Galaprint HP 78A, Canon 728', '1507/21/148-М', 400, 'CE278A', 100000, 2, 'Поставка тестовая'),
       ('Картридж NV-Print HP 25X', '1507/21/148-М', 700, 'CE255X', 100000, 2, 'Поставка тестовая'),
       ('Картридж NV-Print HP 25X', '1507/21/148-М', 700, 'CE255X', 100000, 2, 'Поставка тестовая'),
       ('Кабель CableExpert HDMI 15 м', '1507/21/148-М', 1500, 'noname', 100004, 2, 'Поставка тестовая'),
       ('Кабель CableExpert HDMI 15 м', '1507/21/148-М', 1500, 'noname', 100004, 2, 'Поставка тестовая'),
       ('Кабель CableExpert HDMI 15 м', '1507/21/148-М', 1500, 'noname', 100004, 2, 'Поставка тестовая'),
       ('Кабель CableExpert HDMI 15 м', '1507/21/148-М', 1500, 'noname', 100004, 2, 'Поставка тестовая'),
       ('Мышь Logitech M100', '1507/21/148-М', 200, 'M100', 100005, 2, 'Поставка тестовая'),
       ('Мышь Logitech M100', '1507/21/148-М', 200, 'M100', 100005, 2, 'Поставка тестовая'),
       ('Мышь Logitech M100', '1507/21/148-М', 200, 'M100', 100005, 2, 'Поставка тестовая'),
       ('Мышь Logitech M100', '1507/21/148-М', 200, 'M100', 100005, 2, 'Поставка тестовая'),
       ('Мышь Logitech M100', '1507/21/148-М', 200, 'M100', 100005, 2, 'Поставка тестовая'),
       ('Мышь Logitech M100', '1507/21/148-М', 200, 'M100', 100005, 2, 'Поставка тестовая'),
       ('Мышь Logitech M100', '1507/21/148-М', 200, 'M100', 100005, 2, 'Поставка тестовая'),
       ('Kingston 480G SSD', '1507/21/148-М', 3500, 'SA400S37/480G', 100008, 2, 'Поставка тестовая'),
       ('Kingston 480G SSD', '1507/21/148-М', 3500, 'SA400S37/480G', 100008, 2, 'Поставка тестовая'),
       ('Kingston 480G SSD', '1507/21/148-М', 3500, 'SA400S37/480G', 100008, 2, 'Поставка тестовая'),
       ('Kingston 480G SSD', '1507/21/148-М', 3500, 'SA400S37/480G', 100008, 2, 'Поставка тестовая');

INSERT INTO consumable (id, name, contract, price, part_number, category_id, status, comment)
VALUES ('cb0cd54a-b6b2-4d2d-829e-7f0128ac7db6', 'Картридж Galaprint HP 35A, HP 36A, HP 85A', '1507/21/148-М', 300,
        'CB435A, CB436A, CE285A', 100000, 3, 'Кабинет 865 / 6'),
       ('64113547-eb2c-4856-a179-885e33a5b7e4', 'Картридж Galaprint HP 35A, HP 36A, HP 85A', '1507/21/148-М', 300,
        'CB435A, CB436A, CE285A', 100000, 3, 'Кабинет 865 / 7'),
       ('9691f3f1-3043-4d04-90eb-f1b88b1356ab', 'Картридж Galaprint HP 35A, HP 36A, HP 85A', '1507/21/148-М', 300,
        'CB435A, CB436A, CE285A', 100000, 3, 'Кабинет 865 / 1'),
       ('81f4277a-68bc-45e5-b275-e035ac63251d', 'Картридж Galaprint HP 78A, Canon 728', '1507/21/148-М', 400, 'CE278A',
        100000, 3, 'Кабинет 930 / 10'),
       ('46ec581e-79e3-46be-938f-d0219cfa9335', 'Картридж NV-Print HP 25X', '1507/21/148-М', 700, 'CE255X', 100000, 4,
        'Серверная архив'),
       ('ea1fbbf0-f13e-4cf8-a71d-3d8eceb843a3', 'Картридж NV-Print HP 25X', '1507/21/148-М', 700, 'CE255X', 100000, 4,
        'Серверная архив');

INSERT INTO transactions (type, consumable_id, author_id, comment)
VALUES (3, 'cb0cd54a-b6b2-4d2d-829e-7f0128ac7db6', '0d9b7adf-7879-4c4d-915f-d7cb0419028a', '1507 / Директор'),
       (3, '64113547-eb2c-4856-a179-885e33a5b7e4', '0d9b7adf-7879-4c4d-915f-d7cb0419028a', '1507 / 215'),
       (3, '9691f3f1-3043-4d04-90eb-f1b88b1356ab', '0d9b7adf-7879-4c4d-915f-d7cb0419028a', '930 / 51'),
       (3, '81f4277a-68bc-45e5-b275-e035ac63251d', '0d9b7adf-7879-4c4d-915f-d7cb0419028a', '1507 / 113'),
       (3, '46ec581e-79e3-46be-938f-d0219cfa9335', '63ee60d7-b779-45bd-89a6-f3607ccab181', '1507 / 114'),
       (3, 'ea1fbbf0-f13e-4cf8-a71d-3d8eceb843a3', '63ee60d7-b779-45bd-89a6-f3607ccab181', '1507 / 114'),
       (4, '46ec581e-79e3-46be-938f-d0219cfa9335', '63ee60d7-b779-45bd-89a6-f3607ccab181', '1507 / 113к'),
       (4, 'ea1fbbf0-f13e-4cf8-a71d-3d8eceb843a3', '63ee60d7-b779-45bd-89a6-f3607ccab181', '1507 / 113к');