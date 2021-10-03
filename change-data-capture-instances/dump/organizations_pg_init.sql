CREATE TABLE organization (
    organization_id     INTEGER PRIMARY KEY,
    code    varchar(5) UNIQUE NOT NULL,
    name    varchar(40) UNIQUE NOT NULL
);

CREATE TABLE department (
    department_id     INTEGER PRIMARY KEY,
    organization_id     INTEGER NOT NULL REFERENCES organization (organization_id) ON DELETE CASCADE,
    code    varchar(5) UNIQUE NOT NULL,
    name    varchar(40) UNIQUE NOT NULL
);