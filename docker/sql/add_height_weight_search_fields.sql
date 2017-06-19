SET SQL_SAFE_UPDATES = 0;
ALTER TABLE user ADD `height` int(11) NOT NULL;
ALTER TABLE user ADD `weight` int(11) NOT NULL;
ALTER TABLE user ADD `search` text NOT NULL;
update user set height = 175 where id > 0;
update user set weight = 75 where id > 0;
SET SQL_SAFE_UPDATES = 1;