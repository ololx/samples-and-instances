CREATE TABLE organizations (
    organization_id     INTEGER PRIMARY KEY,
    code    varchar(5) UNIQUE NOT NULL,
    name    varchar(40) UNIQUE NOT NULL
);

CREATE TABLE departments (
    department_id     INTEGER PRIMARY KEY,
    organization_id     INTEGER NOT NULL,
    code    varchar(5) UNIQUE NOT NULL,
    name    varchar(40) UNIQUE NOT NULL,
    CONSTRAINT fk_organization
        FOREIGN KEY(organization_id) 
            REFERENCES organizations(organization_id)
);