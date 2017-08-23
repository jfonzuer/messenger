SET SQL_SAFE_UPDATES = 0;
ALTER TABLE user ADD `notify_other` bit(1) NOT NULL;
update user set notify_other = 1 where id > 0;
SET SQL_SAFE_UPDATES = 1;