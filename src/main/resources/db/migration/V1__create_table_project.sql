CREATE TABLE tb_projects(
    id SERIAL PRIMARY KEY,
    name varchar(100) NOT NULL,
    description varchar(255),
    start_date DATE,
    end_date DATE
);