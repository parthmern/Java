-- Add columns as nullable
ALTER TABLE passenger
    ADD email VARCHAR(255) NULL,
    ADD password VARCHAR(255) NULL,
    ADD phone_number VARCHAR(255) NULL;

-- Update existing records to have some default values (empty string or placeholder)
UPDATE passenger SET email = '' WHERE email IS NULL;
UPDATE passenger SET password = '' WHERE password IS NULL;
UPDATE passenger SET phone_number = '' WHERE phone_number IS NULL;

-- Then modify to NOT NULL
ALTER TABLE passenger MODIFY email VARCHAR(255) NOT NULL;
ALTER TABLE passenger MODIFY password VARCHAR(255) NOT NULL;
ALTER TABLE passenger MODIFY phone_number VARCHAR(255) NOT NULL;

-- Also modify name to NOT NULL if needed
ALTER TABLE passenger MODIFY name VARCHAR(255) NOT NULL;
