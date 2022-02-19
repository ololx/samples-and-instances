-- Create a keyspace
CREATE KEYSPACE IF NOT EXISTS books WITH REPLICATION = { 'class' : 'SimpleStrategy', 'replication_factor' : '1' };

-- Create a table
CREATE TABLE books.book (
   id timeuuid PRIMARY KEY,
   title text,
   description text,
   published boolean
);