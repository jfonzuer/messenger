SET SQL_SAFE_UPDATES = 0;
delete FROM messenger.token where expiry_date < now();
SET SQL_SAFE_UPDATES = 1;