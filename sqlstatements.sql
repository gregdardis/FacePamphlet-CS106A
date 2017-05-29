-- DROP TABLE IF EXISTS Profiles;
-- DROP TABLE IF EXISTS Friends;

CREATE TABLE IF NOT EXISTS Profiles(
    name TEXT PRIMARY KEY,
    status TEXT,
    image BLOB
);

-- Test
INSERT INTO Profiles(name, status)
VALUES("Greg", "coding like a fiend");

CREATE TABLE IF NOT EXISTS Friends(
    friend1 TEXT,
    friend2 TEXT,
    PRIMARY KEY (friend1, friend2),
    FOREIGN KEY (friend1) REFERENCES Profiles(name),
    FOREIGN KEY (friend2) REFERENCES Profiles(name)
    -- TODO: enable enforcing of foreign keys in java.
    -- They are not by default enforced
);
