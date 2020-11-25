
use mydb;

CREATE TABLE IF NOT EXISTS user(
user_id  BIGINT UNSIGNED NOT NULL,
user_type INT UNSIGNED NOT NULL DEFAULT 0,
reg_time timestamp NOT NULL,
update_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
PRIMARY KEY(user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

