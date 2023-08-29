
CREATE TABLE Cats
(
    id      SERIAL PRIMARY KEY,
    name    VARCHAR,
    ownerId INT,
    dateOfBirth DATE,
    breed VARCHAR,
    color VARCHAR
);

CREATE TABLE Owners
(
  id   SERIAL PRIMARY KEY,
  name    VARCHAR,
  dateOfBirth DATE
);