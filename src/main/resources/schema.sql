CREATE TABLE department (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE employee (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    skills TEXT,
    department_id BIGINT,
    CONSTRAINT fk_department FOREIGN KEY(department_id) REFERENCES department(id)
);