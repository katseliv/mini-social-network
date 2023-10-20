ALTER TABLE socialnetwork.friends
    ALTER COLUMN status TYPE VARCHAR(50);

ALTER TABLE socialnetwork.jwt_tokens
    ALTER COLUMN type TYPE VARCHAR(50);

DROP TYPE friendship_status;

DROP TYPE token_type;