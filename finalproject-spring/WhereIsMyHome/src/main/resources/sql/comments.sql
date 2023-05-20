CREATE TABLE `ssafyhome`.`comments` (
  `comments_idx` INT NOT NULL AUTO_INCREMENT,
  `score` INT NOT NULL DEFAULT 0,
  `content` VARCHAR(1000) NULL,
  `user_id` VARCHAR(40) NULL,
  PRIMARY KEY (`comments_idx`));
  
  alter table comments add foreign key(user_id) references members(user_id);
  
  alter table comments add regist_time timestamp default now();
  
alter table comments add apt_code int not null;
alter table comments add foreign key(apt_code) references houseinfo(aptCode);