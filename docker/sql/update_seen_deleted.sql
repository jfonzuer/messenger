select * from conversation where is_read_by_user_two = 0 and is_deleted_by_user_two = 1;
set SQL_SAFE_UPDATES = 0;
update conversation set is_read_by_user_two = 1 where is_read_by_user_two = 0 and is_deleted_by_user_two = 1;
update conversation set is_read_by_user_one = 1 where is_read_by_user_one = 0 and is_deleted_by_user_one = 1;
set SQL_SAFE_UPDATES = 1;