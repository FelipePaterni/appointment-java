insert into
    TBL_PERSON (NAME, PHONE)
VALUES
    ('John Doe', '555-1234'),
    ('Jane Smith', '555-5678'),
    ('Alice Johnson', '555-8765'),
    ('Bob Brown', '555-4321'),
    ('Charlie Davis', '555-0000'),
    ('Felipe', '9999-9999'),
    ('Maria', '8888-8888'),
    ('Lucas', '7777-7777'),
    ('Ana', '6666-6666');

insert into
    TBL_CLIENT (BIRTH_DATE, PERSON_ID)
VALUES
    ('1990-01-01', 1),
    ('1985-05-15', 2),
    ('1992-07-20', 3);

insert into
    TBL_PROFESSIONAL (ACTIVE, PERSON_ID)
VALUES
    (true, 4),
    (true, 5),
    (false, 6);

insert into
    TBL_USER (EMAIL, PASSWORD, PERSON_ID)
VALUES
    ('john.doe@example.com', 'password123', 7),
    ('jane.smith@example.com', 'password456', 8),
    ('alice.johnson@example.com', 'password789', 9);

insert into
    TBL_ROLE (role)
values
    ('ROLE_ADMIN'),
    ('ROLE_OPERETOR');

insert into
    TBL_USER_ROLE (role_id, user_id)
values
    (1, 7),
    (2, 7),
    (2, 8),
    (2, 9);