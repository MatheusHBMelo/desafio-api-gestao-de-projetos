CREATE TABLE tb_tasks (
    id SERIAL PRIMARY KEY,
    title VARCHAR(100) NOT NULL,
    description VARCHAR(255),
    status VARCHAR(50),
    priority VARCHAR(50),
    due_date DATE,
    project_id BIGINT NOT NULL,
    CONSTRAINT fk_project
        FOREIGN KEY(project_id)
        REFERENCES tb_projects(id)
        ON DELETE CASCADE
);