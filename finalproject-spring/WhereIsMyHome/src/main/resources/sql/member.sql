alter table `members` add column `token` varchar(1000) default null;
alter table `members` add `type` VARCHAR(20) null;
alter table members modify user_id varchar(40);
alter table members modify user_password varchar(100);

  alter table members add age int;