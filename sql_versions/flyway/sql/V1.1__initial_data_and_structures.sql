DROP TABLE IF EXISTS `users`;

CREATE TABLE users
( user_id int PRIMARY KEY,
  username varchar(25) NOT NULL,
  password varchar(30) NOT NULL
  );