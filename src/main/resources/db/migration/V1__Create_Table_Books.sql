CREATE TABLE book(
    id          SERIAL PRIMARY KEY,
    author      varchar(255),
    launch_date date NOT NULL,
    price       decimal(65, 2) NOT NULL,
    title       varchar(255)
);