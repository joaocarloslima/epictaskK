ALTER TABLE task
ADD COLUMN user_id UUID;

ALTER TABLE task
ADD CONSTRAINT fk_user
FOREIGN KEY (user_id) REFERENCES epicuser(id);