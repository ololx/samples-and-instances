CREATE TABLE departments (
    department_id     INTEGER PRIMARY KEY,
    code    varchar(5) UNIQUE NOT NULL
);

CREATE TABLE employees (
    employee_id     INTEGER PRIMARY KEY,
    department_id     INTEGER UNIQUE NOT NULL,
    code    varchar(5) UNIQUE NOT NULL,
    fillname    varchar UNIQUE NOT NULL,
    CONSTRAINT fk_departments
        FOREIGN KEY(department_id) 
            REFERENCES departments(department_id)
);